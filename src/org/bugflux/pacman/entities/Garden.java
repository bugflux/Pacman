package org.bugflux.pacman.entities;

import org.bugflux.pacman.Coord;

public interface Garden extends Walkable {

	/**
	 * Try to collect something from current position!
	 * 
	 * @param c 
	 */
	public void collect(Collector c);
	
	/**
	 * Check if there is something to collect in a position.
	 * 
	 * @param c The position's coordinates.
	 * @return true if there is something to collect, false otherwise.
	 */
	public boolean hasCollectable(Coord c);
	
	public void addWalker(Collector w, Coord c);
	
	/**
	 * Special move in a garden. Tries to collect whatever
	 * is in the destination position beforehand.
	 * 
	 * @param w The collector to move.
	 * @param d The direction to move in.
	 * @return the final coordinates of the collector in the map.
	 */
	public Coord move(Collector w, Direction d);
}
