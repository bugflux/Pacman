package org.bugflux.pacman.concurrent;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.MorphingWalkable;
import org.bugflux.pacman.PositionToggler;
import org.bugflux.pacman.Walkable.PositionType;

public class SharedPositionToggler extends PositionToggler {

	public SharedPositionToggler(MorphingWalkable w) {
		super(w);
	}

	public synchronized PositionType tryToggle(Coord c) {
		synchronized(w) {
			if(w.canToggle(c)) {
				return w.togglePositionType(c);
			}

			return w.positionType(c);
		}
	}
}
