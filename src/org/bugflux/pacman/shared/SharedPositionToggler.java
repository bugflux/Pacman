package org.bugflux.pacman.shared;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.Walkable.PositionType;

public abstract class SharedPositionToggler implements Toggler {

	@Override
	public abstract PositionType toggle(Coord c);

	@Override
	public abstract boolean canToggle(Coord c);
}
