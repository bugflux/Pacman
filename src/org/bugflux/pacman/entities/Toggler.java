package org.bugflux.pacman.entities;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Walkable.PositionType;

public interface Toggler extends Gamer {
	public PositionType tryToggle(Coord c);
}