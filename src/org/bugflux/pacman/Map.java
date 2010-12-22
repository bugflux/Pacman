package org.bugflux.pacman;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bugflux.pacman.entities.Collector;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Controllable.Team;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.World;

import pt.ua.gboard.CharGelem;
import pt.ua.gboard.FilledGelem;
import pt.ua.gboard.GBoard;

public class Map implements World {
	protected final PositionType map[][];

	// most of this maps are used for faster testing
	protected final int beanMap[][]; // 0 == inexistent, else gelemId
	protected final int gelemIdMap[][]; // used for walls
	protected final int walkersMap[][];
	protected int remainingBeans;

	protected final HashMap<Controllable, Coord> walkers;

	protected final GBoard screen;
	protected final int mapLayer = 0;
	protected final int graveYardLayer = mapLayer + 1;
	protected final int beanLayer = graveYardLayer + 1;
	protected final int walkerLayer = beanLayer + 1;

	protected final int numLayers = walkerLayer + 1;

	protected final int wallGelemId;
	protected final int backgroundGelemId;
	
	protected final Scoreboard scoreboard;
	protected final HashMap<Collector, Integer> scoreWalkersId;
	protected int scoreBeanId;
	
	protected final List<Toggler> togglers;

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

		walkers = new HashMap<Controllable, Coord>();
		scoreWalkersId = new HashMap<Collector, Integer>();
		togglers = new ArrayList<Toggler>();

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

		scoreboard = new Scoreboard();
		scoreBeanId = scoreboard.addCounter(new BeanGelem(), remainingBeans());
	}

	@Override
	public void addPositionToggler(Toggler t) {
		assert t != null;
		assert !togglers.contains(t);

		togglers.add(t);
	}

 	@Override
	public void addWalker(Collector w, Coord c) {
		internalAddWalker(w, c);
		
		scoreWalkersId.put(w, scoreboard.addCounter(w.gelem(), w.energy()));
	}

	@Override
	public void addWalker(Controllable w, Coord c) {
		internalAddWalker(w, c);
	}
	
	private void internalAddWalker(Controllable w, Coord c) {
		assert !walkers.containsKey(w);
		assert isHall(c) && isFree(c);

		if(hasBean(c)) {
			removeBean(c);
		}

		walkersMap[c.r()][c.c()] = screen.registerGelem(w.gelem());
		screen.draw(walkersMap[c.r()][c.c()], c.r(), c.c(), walkerLayer);
		
		walkers.put(w, c);
	}
	
	@Override
	public Coord position(Controllable w) {
		assert walkers.containsKey(w);
		return walkers.get(w);
	}

	@Override
	public Coord tryMove(Collector w, Direction d) {
		return internalTryMove(w, d, true);
	}

	@Override
	public Coord tryMove(Controllable w, Direction d) {
		return internalTryMove(w, d, false);
	}

	private Coord internalTryMove(Controllable w, Direction d, boolean collect) {
		assert walkers.containsKey(w);
		assert !w.isDead();
		
		Coord oldC = walkers.get(w); //w.getCoord();
		Coord c = newCoord(oldC, d);

		assert validPosition(c) && isHall(c);
		
		if(isFree(c)) {
			// TODO this is temporary inconsistency!
			walkers.put(w, c); // replace the walkers coordinates before collecting!
			

			if(collect) {
				Collector guy = (Collector)w;
				tryCollect(guy);
				guy.looseEnergy(1);
				scoreboard.setValue(scoreWalkersId.get(w), guy.energy());
			}

			walkersMap[c.r()][c.c()] = walkersMap[oldC.r()][oldC.c()];
			screen.move(walkersMap[oldC.r()][oldC.c()], oldC.r(), oldC.c(), walkerLayer, c.r(), c.c(), walkerLayer);
			walkersMap[oldC.r()][oldC.c()] = 0;
			
			if(collect) {
				Collector guy = (Collector)w;
				if(guy.isDead()) {
					killWalker(guy);
				}
			}

			return c;
		}
		else { // collision!
			// TODO kill must actually kill
			Controllable contr = getControllable(c);
			if(contr.team() != w.team()) {
				// the good one dies.
				if(w.team() == Team.GOOD) {
					w.die();
					killWalker(w);
				}
				else {
					contr.die();
					killWalker(contr);
				}
			}

			return oldC;
		}
	}

	private void killWalker(Controllable w) {
		assert walkers.containsKey(w);
		assert w.isDead();

		Coord c = walkers.get(w);
		
		screen.erase(walkersMap[c.r()][c.c()], c.r(), c.c(), walkerLayer);
		walkersMap[c.r()][c.c()] = 0;
		screen.draw(screen.registerGelem(w.gelem()), c.r(), c.c(), graveYardLayer);
		
		Team t = w.team();
		walkers.remove(w);
		
		// check if it's the last for this team.
		for(Controllable x : walkers.keySet()) {
			if(x.team() == t) {
				return;
			}
		}
		
		// it was, so game over!
		cleanup();
	}

	private Controllable getControllable(Coord c) {
		assert walkers.containsValue(c);
		assert !isFree(c);

		for(Entry<Controllable, Coord> x : walkers.entrySet()) {
			if(x.getValue().equals(c)) {
				return x.getKey();
			}
		}
		
		assert false;
		return null;
	}

	@Override
	public void tryCollect(Collector w) {
		assert walkers.containsKey(w);
		assert !w.isDead();

		Coord c = walkers.get(w);
		if(hasBean(c)) {
			w.gainEnergy(3);
			removeBean(c);
		}
	}

	@Override
	public boolean hasCollectable(Coord c) {
		return hasBean(c);
	}

	@Override
	public PositionType tryTogglePositionType(Toggler t, Coord c) {
		assert validPosition(c);
		assert togglers.contains(t);

		if(!canToggle(t, c)) {
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
	public boolean canToggle(Toggler t, Coord c) {
		assert togglers.contains(t);

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

	protected void removeBean(Coord c) {
		assert hasBean(c);
		
		screen.erase(beanMap[c.r()][c.c()], c.r(), c.c(), beanLayer);
		beanMap[c.r()][c.c()] = 0;
		remainingBeans--;
		scoreboard.setValue(scoreBeanId, remainingBeans());
		
		if(remainingBeans() == 0) {
			cleanup();
		}
	}
	
	protected void cleanup() {
		for(Controllable x : walkers.keySet()) {
			x.gameOver();
		}
		
		for(Toggler t : togglers) {
			t.gameOver();
		}
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

