package org.bugflux.pacman.shared.monitor;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.Walkable.PositionType;
import org.bugflux.pacman.shared.SharedPositionToggler;

public class MonitorPositionToggler extends SharedPositionToggler implements Toggler {
	protected final Toggler t; 
	
	public MonitorPositionToggler(Toggler t) {
		this.t = t;
	}

	@Override
	public synchronized PositionType toggle(Coord c) {
		return t.toggle(c);
	}

	@Override
	public synchronized boolean canToggle(Coord c) {
		return t.canToggle(c);
	}
}
