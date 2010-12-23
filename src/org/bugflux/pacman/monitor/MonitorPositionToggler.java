package org.bugflux.pacman.monitor;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.Walkable.PositionType;

public class MonitorPositionToggler implements Toggler {
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
