package org.bugflux.pacman;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import org.bugflux.pacman.entities.Collector;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Controllable.Team;
import org.bugflux.pacman.entities.Garden;
import org.bugflux.pacman.entities.MorphingWalkable;

import pt.ua.gboard.CharGelem;
import pt.ua.gboard.FilledGelem;
import pt.ua.gboard.GBoard;

public class Map implements Garden, MorphingWalkable {
	protected final PositionType map[][];

	// most of this maps are used for faster testing
	protected final int beanMap[][]; // 0 == inexistent, else gelemId
	protected final int gelemIdMap[][]; // used for walls
	protected final int walkersMap[][];
	protected int remainingBeans;

	protected final ArrayList<Controllable> goodWalkers;
	protected final ArrayList<Controllable> villainWalkers;

	protected final GBoard screen;
	protected final int mapLayer = 0;
	protected final int graveYardLayer = mapLayer + 1;
	protected final int beanLayer = graveYardLayer + 1;
	protected final int walkerLayer = beanLayer + 1;

	protected final int numLayers = walkerLayer + 1;

	protected final int wallGelemId;
	protected final int backgroundGelemId;
	/**
	 * .
	 * A char map is a 2D char array. Each position contains:
	 *  - '.' for bean position
	 *  - 0 for walkable area
	 *  - anything else for wall
	 */
	public Map(char map[][]) {
		assert map != null;
		assert map.length > 0 && map[0].length > 0;

		// declare the maps
		this.map = new PositionType[map.length][map[0].length];
		gelemIdMap = new int[height()][width()];
		walkersMap = new int[height()][width()];
		beanMap = new int[height()][width()];

		goodWalkers = new ArrayList<Controllable>();
		villainWalkers = new ArrayList<Controllable>();

		// declare and initialize the screen
		// the first layer is for the map (walls, halls, doors),r
		// the third layer is for correct path marking
		screen = GBoard.init("Pac-Man", height(), width(), 30, 30, numLayers);

		backgroundGelemId = screen.registerGelem(new FilledGelem(Color.black, 100.0));
		wallGelemId = screen.registerGelem(new WallGelem());
		int beanGelemId = screen.registerGelem(new BeanGelem());
		for(int r = 0; r < height(); r++) {
			for(int c = 0; c < width(); c++) {
				switch(map[r][c]) {
					case '.':
						//this.map[r][c] = POSITION_TYPE.HALL;

						beanMap[r][c] = beanGelemId;
						screen.draw(beanMap[r][c], r, c, beanLayer);
						remainingBeans++;
						//break;

					case 0:
						this.map[r][c] = PositionType.HALL;
						gelemIdMap[r][c] = backgroundGelemId;
						screen.draw(gelemIdMap[r][c], r, c, mapLayer);
						break;

					default:
						this.map[r][c] = PositionType.WALL;

						gelemIdMap[r][c] = wallGelemId;
						screen.draw(gelemIdMap[r][c], r, c, mapLayer);

						break;
				}
			}
		}
	}

	@Override
	public void addWalker(Controllable w, Coord c) {
		assert isHall(c) && isFree(c);

		if(hasBean(c)) {
			removeBean(c);
		}

		walkersMap[c.r()][c.c()] = screen.registerGelem(w.gelem());
		screen.draw(walkersMap[c.r()][c.c()], c.r(), c.c(), walkerLayer);
		
		if(w.team() == Team.GOOD) {
			goodWalkers.add(w);
		}
		else {
			villainWalkers.add(w);
		}
	}

	@Override
	public Coord tryMove(Controllable w, Direction d) {
		if(w.team() == Team.GOOD) {
			assert goodWalkers.contains(w);
		}
		else {
			assert villainWalkers.contains(w);
		}
		
		Coord oldC = w.getCoord();
		if(w.isDead()) {
			return oldC;
		}

		Coord c = newCoord(oldC, d);

		if(!validPosition(c) || !isHall(c)) {
			return oldC;
		}
		
		if(isFree(c)) {
			try {
				Collector collector = (Collector)w;
				if(collector.looseEnergy(1) <= 0) {
					System.err.println("died");
					killWalker(collector);

					return oldC;
				}

				if(hasBean(c)) {
					removeBean(c);
					collector.gainEnergy(10);
					
					if(remainingBeans() == 0) {
						// TODO finish
					}
				}
			}
			catch(ClassCastException e) {
				//System.err.println("unable to cast!");
				// do nothing, actually!
			}

			screen.move(walkersMap[oldC.r()][oldC.c()], oldC.r(), oldC.c(), walkerLayer, c.r(), c.c(), walkerLayer);
			walkersMap[c.r()][c.c()] = walkersMap[oldC.r()][oldC.c()];
			walkersMap[oldC.r()][oldC.c()] = 0;

			return c;
		}
		else { // collision!
			// TODO kill must actually kill
			Controllable contr = getControllable(c);
			if(contr.team() != w.team()) {
				// the good one dies.
				System.err.println("collision");
				if(w.team() == Team.GOOD) {
					killWalker(w);
				}
				else {
					killWalker(contr);
				}
			}

			return oldC;
		}
	}
	
	private void killWalker(Controllable w) {
		Coord c = w.getCoord();
		w.die();
		
		screen.erase(walkersMap[c.r()][c.c()], c.r(), c.c(), walkerLayer);
		walkersMap[c.r()][c.c()] = 0;
		screen.draw(screen.registerGelem(w.gelem()), c.r(), c.c(), graveYardLayer);
		
		if(w.team() == Team.GOOD) {
			goodWalkers.remove(w);
		}
		else {
			villainWalkers.remove(w);
		}
	}
	
	// called only when there is a controllable in c!!
	private Controllable getControllable(Coord c) {
		assert !isFree(c);

		Iterator<Controllable> iter = goodWalkers.iterator();
		Controllable contr;
		while(iter.hasNext()) {
			contr = iter.next();
			if(contr.getCoord().equals(c)) {
				return contr;
			}
		}
		
		iter = villainWalkers.iterator();
		while(iter.hasNext()) {
			contr = iter.next();
			if(contr.getCoord().equals(c)) {
				return contr;
			}
		}
		
		assert false;
		return null;
	}

	@Override
	public int tryCollect(Collector w) {
		assert goodWalkers.contains(w);
		Coord c = w.getCoord();
		
		if(hasBean(c)) {
			removeBean(c);
			return 10;
		}
		
		return 0;
	}

	@Override
	public boolean hasCollectable(Coord c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PositionType tryTogglePositionType(Coord c) {
		assert validPosition(c);

		if(!canToggle(c)) {
			return map[c.r()][c.c()];
		}

		if(isHall(c)) {
			screen.erase(gelemIdMap[c.r()][c.c()], c.r(), c.c(), mapLayer);
			gelemIdMap[c.r()][c.c()] = wallGelemId;
			screen.draw(gelemIdMap[c.r()][c.c()], c.r(), c.c(), mapLayer);
			return map[c.r()][c.c()] = PositionType.WALL;
		}
		else { // WALL
			screen.erase(gelemIdMap[c.r()][c.c()], c.r(), c.c(), mapLayer);
			gelemIdMap[c.r()][c.c()] = backgroundGelemId;
			screen.draw(gelemIdMap[c.r()][c.c()], c.r(), c.c(), mapLayer);
			return map[c.r()][c.c()] = PositionType.HALL;
		}
	}
	
	@Override
	public boolean canToggle(Coord c) {
		return isFree(c) && !hasBean(c);
	}

	@Override
	public Coord newCoord(Coord oldC, Direction d) {
		Coord c = null;
		switch(d) {
			case UP:    c = new Coord(oldC.r() - 1, oldC.c()); break;
			case DOWN:  c = new Coord(oldC.r() + 1, oldC.c()); break;
			case LEFT:  c = new Coord(oldC.r(), oldC.c() - 1); break;
			case RIGHT: c = new Coord(oldC.r(), oldC.c() + 1); break;
			default: assert false;
		}
		
		return c;
	}

	@Override
	public boolean isHall(Coord c) {
		return positionType(c) == PositionType.HALL;
	}

	@Override
	public boolean isFree(Coord c) {
		assert validPosition(c);

		return walkersMap[c.r()][c.c()] == 0;
	}
	
	public boolean hasBean(Coord c) {
		assert validPosition(c);
		
		return beanMap[c.r()][c.c()] != 0;
	}

	public int remainingBeans() {
		return remainingBeans;
	}

	public void removeBean(Coord c) {
		assert hasBean(c);
		
		screen.erase(beanMap[c.r()][c.c()], c.r(), c.c(), beanLayer);
		beanMap[c.r()][c.c()] = 0;
	}
	
	@Override
	public PositionType positionType(Coord c) {
		assert validPosition(c);

		return map[c.r()][c.c()];
	}

	@Override
	public boolean validPosition(Coord c) {
		return validPosition(c.r(), c.c());
	}
	
	public boolean validPosition(int r, int c) {
		return r >= 0 && r < height()
			&& c >= 0 && c < width();
	}

	@Override
	public int height() {
		return map.length;
	}

	@Override
	public int width() {
		return map[0].length;
	}
	
	public GBoard getGBoard() {
		return screen;
	}
}

class WallGelem extends FilledGelem {
	public WallGelem() {
		super(Color.blue, 100.0);
	}
}

class BeanGelem extends CharGelem {
	public BeanGelem() {
		super('.', Color.yellow);
	}
}

