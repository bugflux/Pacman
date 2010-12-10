package org.bugflux.pacman.entities;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Walkable.PositionType;

public interface Toggler {
	
	public PositionType tryToggle(Coord c);
	
	public int height();
	
	public int width();
}