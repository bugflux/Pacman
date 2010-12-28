package org.bugflux.pacman.entities;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Walkable.Direction;

public interface Controllable extends Player {
	/**
	 * 
	 * @return
	 */
	public abstract Coord getCoord();
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public abstract Coord move(Direction d);
	
	/**
	 * 
	 * @return
	 */
	public abstract boolean canMove(Direction d);
}
