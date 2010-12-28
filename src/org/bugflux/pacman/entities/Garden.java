package org.bugflux.pacman.entities;

import org.bugflux.pacman.Coord;

public interface Garden extends Walkable {

	/**
	 * Try to collect something from current position!
	 * 
	 * @param c 
	 */
	public abstract void collect(Collector c);
	
	/**
	 * Check if there is something to collect in a position.
	 * 
	 * @param c The position's coordinates.
	 * @return true if there is something to collect, false otherwise.
	 */
	public abstract boolean hasCollectable(Coord c);
	
	/**
	 * Special addWalker, adds a collector that interacts with the map.
	 * 
	 * @param w The Collector to add.
	 * @param c The position to place it initially.
	 */
	public abstract void addWalker(Collector w, Coord c);
	
	/**
	 * Place a Bonus in this map.
	 * 
	 * @param b The Bonus to place.
	 * @param c The position to place the Bonus in. 
	 */
	public abstract void addBonus(Bonus b, Coord c);
	
	/**
	 * Remove a bonus from the map. The bonus can't disappear by himself because
	 * that would require a synch block on Bonuses for all methods on the Garden.
	 * 
	 * @param b The Bonus to remove.
	 */
	public abstract void removeBonus(Bonus b);
	
	/**
	 * Check if there's a Bonus in a given position.
	 * 
	 * @param c The coordinates of the position to check for Bonus.
	 * @return true if there's a Bonus in that position, false otherwise.
	 */
	public abstract boolean hasBonus(Coord c);
	
	/**
	 * Check if there's a Bean in a given position.
	 * 
	 * @param c The coordinates of the position to check for Bean.
	 * @return true if there's a Bean in that position, false otherwise.
	 */
	public abstract boolean hasBean(Coord c);
	
	/**
	 * Get the Scorekeeper for this garden.
	 * 
	 * @return the Scorekeeper instance.
	 */
	public abstract Scorekeeper getScorekeeper();
}
