package org.bugflux.pacman;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import pt.ua.gboard.CharGelem;
import pt.ua.gboard.FilledGelem;
import pt.ua.gboard.GBoard;

public class Map implements Walkable {
	public enum PositionType { WALL, HALL };

	private final PositionType map[][];

	// most of this maps are used for faster testing
	private final int beanMap[][]; // 0 == inexistent, else gelemId
	private final int gelemIdMap[][]; // used for walls
	private final int walkersMap[][];
	private int remainingBeans;

	private final Set<Walker> walkers;

	private final GBoard screen;
	private final int numLayers = 4;
	private final int backgroundLayer = 0;
	private final int mapLayer = backgroundLayer + 1;
	private final int beanLayer = mapLayer + 1;
	private final int walkerLayer = beanLayer + 1;
	
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

		walkers = new HashSet<Walker>();

		// declare and initialize the screen
		// the first layer is for the map (walls, halls, doors),r
		// the third layer is for correct path marking
		screen = GBoard.init("Pac-Man", height(), width(), 30, 30, numLayers);

		int backgroundGelemId = screen.registerGelem(new FilledGelem(Color.black, 100.0));
		int wallGelemId = screen.registerGelem(new WallGelem());
		int beanGelemId = screen.registerGelem(new BeanGelem());
		for(int r = 0; r < height(); r++) {
			for(int c = 0; c < width(); c++) {
				screen.draw(backgroundGelemId, r, c, backgroundLayer);
				switch(map[r][c]) {
					case '.':
						//this.map[r][c] = POSITION_TYPE.HALL;

						beanMap[r][c] = beanGelemId;
						screen.draw(beanMap[r][c], r, c, beanLayer);
						remainingBeans++;
						//break;

					case 0:
						this.map[r][c] = PositionType.HALL;
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
	public void addWalker(Walker w, Coord c) {
		assert isHall(c) && isFree(c);

		if(hasBean(c)) {
			removeBean(c);
		}

		walkersMap[c.r()][c.c()] = screen.registerGelem(w.gelem());
		screen.draw(walkersMap[c.r()][c.c()], c.r(), c.c(), walkerLayer);
		walkers.add(w);
	}
	
	@Override
	public Coord move(Walker w, Direction d) {
		Coord oldC = w.getCoord();
		assert !isFree(oldC);
		assert walkers.contains(w);
		
		Coord c = newCoord(oldC, d);
		
		assert validPosition(c) && isHall(c); // TODO allow?
		
		if(isFree(c)) {
			screen.move(walkersMap[oldC.r()][oldC.c()], oldC.r(), oldC.c(), walkerLayer, c.r(), c.c(), walkerLayer);
			walkersMap[c.r()][c.c()] = walkersMap[oldC.r()][oldC.c()];
			walkersMap[oldC.r()][oldC.c()] = 0;
		}
		else { // collision!
			// TODO
		}
		
		return c;
	}

	public void toggleDoor(Coord c) {
		assert isFree(c) && !hasBean(c);

		if(isHall(c)) {
			gelemIdMap[c.r()][c.c()] = screen.registerGelem(new WallGelem());
			screen.draw(gelemIdMap[c.r()][c.c()], c.r(), c.c(), mapLayer);
			map[c.r()][c.c()] = PositionType.WALL;
		}
		else { // WALL
			screen.erase(gelemIdMap[c.r()][c.c()], c.r(), c.c(), mapLayer);
			gelemIdMap[c.r()][c.c()] = 0;
			map[c.r()][c.c()] = PositionType.HALL;
		}
	}

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
	
	public boolean isHall(Coord c) {
		return positionType(c) == PositionType.HALL;
	}
	
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
	
	public PositionType positionType(Coord c) {
		assert validPosition(c);

		return map[c.r()][c.c()];
	}

	public boolean validPosition(Coord c) {
		return validPosition(c.r(), c.c());
	}
	
	public boolean validPosition(int r, int c) {
		return r >= 0 && r < height()
			&& c >= 0 && c < width();
	}

	public int height() {
		return map.length;
	}

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
		super('.', Color.white);
	}
}

