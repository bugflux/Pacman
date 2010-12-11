package org.bugflux.pacman.entities;

import org.bugflux.pacman.Coord;

public interface Garden extends Walkable {

	/**
	 * Try to collect something from current position!
	 * 
	 * @param c 
	 */
	public int tryCollect(Collector c);
	
	/**
	 * Check if there is something to collect in a position.
	 * 
	 * @param c The position's coordinates.
	 * @return true if there is something to collect, false otherwise.
	 */
	public boolean hasCollectable(Coord c);
}
