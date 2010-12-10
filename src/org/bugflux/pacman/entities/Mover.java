package org.bugflux.pacman.entities;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Walkable.Direction;


public interface Mover {
	public Coord tryMove(Direction d);
}
