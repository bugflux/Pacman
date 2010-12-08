package org.bugflux.pacman;

import org.bugflux.pacman.Walkable.Direction;

import pt.ua.gboard.Gelem;

public interface Walker {
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
	public abstract void tryMove(Direction d);
	
	/**
	 * 
	 * @return
	 */
	public abstract Gelem gelem();
	
	/**
	 * 
	 * @return
	 */
	public abstract boolean canMove(Direction d);
}
