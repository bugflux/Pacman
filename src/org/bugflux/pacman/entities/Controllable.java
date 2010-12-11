package org.bugflux.pacman.entities;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Walkable.Direction;

import pt.ua.gboard.Gelem;

public interface Controllable {
	public static enum Team { GOOD, VILLAIN };

	/**
	 * 
	 * @return
	 */
	public abstract Team team();
	
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
	public abstract Coord tryMove(Direction d);
	
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
	
	/**
	 * 
	 */
	public abstract void kill();
}
