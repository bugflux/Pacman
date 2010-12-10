package org.bugflux.pacman.monitor;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.PositionToggler;
import org.bugflux.pacman.entities.MorphingWalkable;
import org.bugflux.pacman.entities.Walkable.PositionType;

public class MonitorPositionToggler extends PositionToggler {

	public MonitorPositionToggler(MorphingWalkable w) {
		super(w);
	}

	@Override
	public synchronized PositionType tryToggle(Coord c) {
		synchronized(w) {
			if(w.canToggle(c)) {
				return w.tryTogglePositionType(c);
			}

			return w.positionType(c);
		}
	}
}
