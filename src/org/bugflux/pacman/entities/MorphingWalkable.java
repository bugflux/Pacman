package org.bugflux.pacman.entities;

import org.bugflux.pacman.Coord;

public interface MorphingWalkable extends Walkable {
	
	/**
	 * Registers a new position toggler in the map.
	 * 
	 * @param t the toggler to register.
	 */
	public abstract void addPositionToggler(Toggler t);

	/**
	 * Toggle the PositionType of a position in a Walkable object.
	 * 
	 * @param c The position's coordinates.
	 * @return The new PositionType for that position.
	 */
	public abstract PositionType togglePositionType(Toggler t, Coord c);
	
	/**
	 * Check if it is possible to toggle a given position.
	 * 
	 * @param c The coordinates of the position.
	 * @return true if it can currently be toggled, false otherwise.
	 */
	public abstract boolean canToggle(Toggler t, Coord c);
}
