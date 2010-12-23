package org.bugflux.pacman.entities;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Walkable.PositionType;

public interface Toggler {
	/**
	 * TODO
	 * @param c
	 * @return
	 */
	public abstract PositionType toggle(Coord c);
	
	/**
	 * TODO
	 * 
	 * @param c
	 * @return
	 */
	public abstract boolean canToggle(Coord c);
}