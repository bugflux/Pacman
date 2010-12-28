package org.bugflux.pacman;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bugflux.pacman.entities.Bonus;
import org.bugflux.pacman.entities.Bonus.Property;
import org.bugflux.pacman.entities.Collector;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Player.Team;
import org.bugflux.pacman.entities.Scorekeeper;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.World;

import pt.ua.gboard.FilledGelem;
import pt.ua.gboard.GBoard;

public class Map implements World, WindowListener {
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
	protected final int bonusLayer = beanLayer + 1;
	protected final int walkerLayer = bonusLayer + 1;

	protected final int numLayers = walkerLayer + 1;

	protected final int wallGelemId;
	protected final int backgroundGelemId;

	protected final Scorekeeper scoreboard;
	protected final HashMap<Collector, Integer> scoreWalkersId;
	protected int scoreBeanId;

	protected final HashMap<Bonus, Collector> activeBonuses;
	protected final HashMap<Bonus, Coord> bonuses;
	protected final int bonusMap[][];

	protected final List<Toggler> togglers;
	protected boolean isOver;

	/**
	 * . A char map is a 2D char array. Each position contains: - '.' for bean
	 * position - 0 for walkable area - anything else for wall
	 */
	public Map(char map[][], Scorekeeper scoreboard) {
		assert map != null;
		assert map.length > 0 && map[0].length > 0;

		// declare the maps
		this.map = new PositionType[map.length][map[0].length];
		gelemIdMap = new int[height()][width()];
		walkersMap = new int[height()][width()];
		bonusMap = new int[height()][width()];
		beanMap = new int[height()][width()];

		walkers = new HashMap<Controllable, Coord>();
		scoreWalkersId = new HashMap<Collector, Integer>();
		bonuses = new HashMap<Bonus, Coord>();
		activeBonuses = new HashMap<Bonus, Collector>();
		togglers = new ArrayList<Toggler>();
		isOver = false;

		// declare and initialize the screen
		// the first layer is for the map (walls, halls, doors),r
		// the third layer is for correct path marking
		screen = GBoard.init("Pac-Man", height(), width(), 30, 30, numLayers);

		backgroundGelemId = screen.registerGelem(new FilledGelem(Color.black,
				100.0));
		wallGelemId = screen.registerGelem(new WallGelem());
		int beanGelemId = screen.registerGelem(new BeanGelem());
		for (int r = 0; r < height(); r++) {
			for (int c = 0; c < width(); c++) {
				switch (map[r][c]) {
				case '.':
					// this.map[r][c] = POSITION_TYPE.HALL;

					beanMap[r][c] = beanGelemId;
					screen.draw(beanMap[r][c], r, c, beanLayer);
					remainingBeans++;
					// break;

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

		this.scoreboard = scoreboard;
		scoreBeanId = scoreboard.addCounter(new BeanGelem(), remainingBeans);

		screen.frame().addWindowListener(this);
		
		// can't call "remainingBeans()" method because of circular dependency
		// for "isOver()"
	}
	@Override public void windowOpened(WindowEvent e) {}
	@Override public void windowClosing(WindowEvent e) { isOver = true; }
	@Override public void windowClosed(WindowEvent e) {}
	@Override public void windowIconified(WindowEvent e) {}
	@Override public void windowDeiconified(WindowEvent e) {}
	@Override public void windowActivated(WindowEvent e) {}
	@Override public void windowDeactivated(WindowEvent e) {}

	@Override
	public void addBonus(Bonus b, Coord c) {
		assert !isOver();
		assert isFree(c);
		assert !hasBonus(c);
		assert !bonuses.containsKey(b);
		assert !activeBonuses.containsKey(b);
		assert isHall(c);

		bonusMap[c.r()][c.c()] = screen.registerGelem(b.gelem());
		screen.draw(bonusMap[c.r()][c.c()], c.r(), c.c(), bonusLayer);

		bonuses.put(b, c);
	}

	// erases from the screen and removes from "bonuses". doesn't promote to "activeBonuses".
	private void eraseBonus(Bonus b) {
		assert bonuses.containsKey(b) || activeBonuses.containsKey(b);

		Coord c = bonuses.get(b);
		screen.erase(bonusMap[c.r()][c.c()], c.r(), c.c(), bonusLayer);
		bonusMap[c.r()][c.c()] = 0;

		bonuses.remove(b);
	}

	@Override
	public void removeBonus(Bonus b) {
		if(bonuses.containsKey(b)) {
			eraseBonus(b);
			return;
		}
		else if(activeBonuses.containsKey(b)){
			activeBonuses.remove(b);
			return;
		}
		assert false; // precondition!
	}

	@Override
	public void addPositionToggler(Toggler t) {
		assert !isOver();
		assert t != null;
		assert !togglers.contains(t);

		togglers.add(t);
	}

	@Override
	public void addWalker(Collector w, Coord c) {
		assert !isOver();
		internalAddWalker(w, c);

		scoreWalkersId.put(w, scoreboard.addCounter(w.gelem(), w.energy()));
	}

	@Override
	public void addWalker(Controllable w, Coord c) {
		assert !isOver();
		internalAddWalker(w, c);
	}

	private void internalAddWalker(Controllable w, Coord c) {
		assert !walkers.containsKey(w);
		assert isHall(c) && isFree(c);

		if (hasBean(c)) {
			removeBean(c);
		}

		walkersMap[c.r()][c.c()] = screen.registerGelem(w.gelem());
		screen.draw(walkersMap[c.r()][c.c()], c.r(), c.c(), walkerLayer);

		walkers.put(w, c);
	}

	@Override
	public Coord position(Controllable w) {
		assert !isOver();
		assert walkers.containsKey(w);
		return walkers.get(w);
	}
	
	@Override
	public boolean canMove(Controllable w, Direction d) {
		assert walkers.containsKey(w);
		assert !w.isDead();
		
		Coord oldC = walkers.get(w);
		Coord c = newCoord(oldC, d);
		
		return isHall(c);
	}

	public Coord move(Controllable w, Direction d) {
		assert walkers.containsKey(w);
		assert !w.isDead();

		Coord oldC = walkers.get(w);
		
		// if a "FreezeOpposing" is active for a team opposite
		// of this Controllable, don't move it!
		for(Bonus b : activeBonuses.keySet()) {
			if(b.property() == Property.FreezeOpposing &&
					activeBonuses.get(b).team() != w.team()) {
				return oldC;
			}
		}

		Coord c = newCoord(oldC, d);
		assert isHall(c);

		if(isFree(c)) {
			// TODO this is temporary inconsistency!
			walkers.put(w, c); // replace the walkers coordinates before collecting!

			walkersMap[c.r()][c.c()] = walkersMap[oldC.r()][oldC.c()];
			screen.move(walkersMap[oldC.r()][oldC.c()], oldC.r(), oldC.c(),
					walkerLayer, c.r(), c.c(), walkerLayer);
			walkersMap[oldC.r()][oldC.c()] = 0;

			return c;
		}
		else { // collision!
			Controllable contr = getControllable(c);
			
			if(contr.team() != w.team()) {
				// the good one dies.
				// if either has "Invincibility, don't lose!
				for(Bonus b : activeBonuses.keySet()) {
					if(b.property() == Property.Invincibility
							&& (activeBonuses.get(b).equals(contr) || activeBonuses.get(b).equals(w))) {
						return oldC;
					}
				}

				if (w.team() == Team.GOOD) {
					w.die();
					killWalker(w);
				} else {
					contr.die();
					killWalker(contr);
				}
			}

			return oldC;
		}
	}

	private void killWalker(Controllable w) {
		assert !isOver();
		assert walkers.containsKey(w);
		assert w.isDead();

		Coord c = walkers.get(w);

		screen.erase(walkersMap[c.r()][c.c()], c.r(), c.c(), walkerLayer);
		walkersMap[c.r()][c.c()] = 0;
		screen.draw(screen.registerGelem(w.gelem()), c.r(), c.c(),
				graveYardLayer);

		Team t = w.team();
		walkers.remove(w);

		// check if it's the last for this team.
		for (Controllable x : walkers.keySet()) {
			if (x.team() == t) {
				return;
			}
		}

		// it was, so game over!
		cleanup(false);
	}

	private Controllable getControllable(Coord c) {
		assert !isOver();
		assert walkers.containsValue(c);
		assert !isFree(c);

		for (Entry<Controllable, Coord> x : walkers.entrySet()) {
			if (x.getValue().equals(c)) {
				return x.getKey();
			}
		}

		assert false;
		return null;
	}

	@Override
	public void collect(Collector w) {
		assert !isOver();
		assert walkers.containsKey(w);
		assert !w.isDead();

		Coord c = walkers.get(w);
		
		if(hasBonus(c)) {
			Bonus b = null;
			// could prevent this reverse-lookup with yet another map,
			// or an Object type on the existing map.
			for(Bonus x : bonuses.keySet()) {
				if(bonuses.get(x).equals(c)) {
					b = x;
					break;
				}
			}
			eraseBonus(b);
			activeBonuses.put(b, w);
		}
		
		if (hasBean(c)) {
			w.gainEnergy(3);
			removeBean(c);
		}
	}

	@Override
	public boolean hasCollectable(Coord c) {
		assert !isOver();
		return hasBean(c) || hasBonus(c);
	}

	@Override
	public PositionType togglePositionType(Toggler t, Coord c) {
		assert !isOver();
		assert validPosition(c);
		assert togglers.contains(t);
		assert canToggle(t, c);

		if (isHall(c)) {
			screen.erase(gelemIdMap[c.r()][c.c()], c.r(), c.c(), mapLayer);
			gelemIdMap[c.r()][c.c()] = wallGelemId;
			screen.draw(gelemIdMap[c.r()][c.c()], c.r(), c.c(), mapLayer);
			return map[c.r()][c.c()] = PositionType.WALL;
		} else { // WALL
			screen.erase(gelemIdMap[c.r()][c.c()], c.r(), c.c(), mapLayer);
			gelemIdMap[c.r()][c.c()] = backgroundGelemId;
			screen.draw(gelemIdMap[c.r()][c.c()], c.r(), c.c(), mapLayer);
			return map[c.r()][c.c()] = PositionType.HALL;
		}
	}

	@Override
	public boolean canToggle(Toggler t, Coord c) {
		assert !isOver();
		assert togglers.contains(t);

		return isFree(c) && !hasCollectable(c);
	}

	@Override
	public Coord newCoord(Coord oldC, Direction d) {
		assert !isOver();
		Coord c = null;
		switch (d) {
		case UP:
			c = new Coord(oldC.r() - 1, oldC.c());
			break;
		case DOWN:
			c = new Coord(oldC.r() + 1, oldC.c());
			break;
		case LEFT:
			c = new Coord(oldC.r(), oldC.c() - 1);
			break;
		case RIGHT:
			c = new Coord(oldC.r(), oldC.c() + 1);
			break;
		default:
			assert false;
		}

		return c;
	}

	@Override
	public boolean isHall(Coord c) {
		assert !isOver();
		assert validPosition(c);
		return positionType(c) == PositionType.HALL;
	}

	@Override
	public boolean isFree(Coord c) {
		assert !isOver();
		assert validPosition(c);

		return walkersMap[c.r()][c.c()] == 0;
	}

	@Override
	public boolean hasBean(Coord c) {
		assert validPosition(c);

		return beanMap[c.r()][c.c()] != 0;
	}
	
	@Override
	public boolean hasBonus(Coord c) {
		assert validPosition(c);
		
		return bonusMap[c.r()][c.c()] != 0;
	}

	public int remainingBeans() {
		assert !isOver();
		return remainingBeans;
	}

	protected void removeBean(Coord c) {
		assert !isOver();
		assert hasBean(c);

		screen.erase(beanMap[c.r()][c.c()], c.r(), c.c(), beanLayer);
		beanMap[c.r()][c.c()] = 0;
		remainingBeans--;
		scoreboard.setValue(scoreBeanId, remainingBeans());

		if (remainingBeans() == 0) {
			cleanup(true);
		}
	}

	protected void cleanup(boolean win) {
		isOver = true;
		if(win) {
			win();
		}
		else {
			lose();
		}
	}

	@Override
	public PositionType positionType(Coord c) {
		assert !isOver();
		assert validPosition(c);

		return map[c.r()][c.c()];
	}

	@Override
	public boolean validPosition(Coord c) {
		assert !isOver();
		return c.r() >= 0 && c.r() < height() && c.c() >= 0 && c.c() < width();
	}

	@Override
	public int height() {
		// assert !isOver();
		return map.length;
	}

	@Override
	public int width() {
		// assert !isOver();
		return map[0].length;
	}

	public GBoard getGBoard() {
		return screen;
	}
	
	@Override
	public Scorekeeper getScorekeeper() {
		assert !isOver();
		return scoreboard;
	}

	@Override
	public boolean isOver() {
		if (scoreboard.isOver()) {
			isOver = true;
		}
		return isOver;
	}
	
	private void win() {
		int gelemId = screen.registerGelem(new FilledGelem(Color.green, 10));
		for(int r = 0; r < height(); r++) {
			for(int c = 0; c < width(); c++) {
				screen.draw(gelemId, r, c, numLayers-1);
			}
		}
	}
	
	private void lose() {
		int gelemId = screen.registerGelem(new FilledGelem(Color.red, 10));
		for(int r = 0; r < height(); r++) {
			for(int c = 0; c < width(); c++) {
				screen.draw(gelemId, r, c, numLayers-1);
			}
		}
	}
}

class WallGelem extends FilledGelem {
	public WallGelem() {
		super(Color.blue, 100.0);
	}
}

class BeanGelem extends FilledGelem {
	public BeanGelem() {
		super(Color.yellow, 10);
	}
}
