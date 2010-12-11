package org.bugflux.pacman.monitor;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.MorphingWalkable;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.Walkable.PositionType;

public class MonitorPositionToggler implements Toggler {
	protected final MorphingWalkable w; 
	
	public MonitorPositionToggler(MorphingWalkable w) {
		this.w = w;
	}

	@Override
	public synchronized PositionType tryToggle(Coord c) {
		return w.tryTogglePositionType(c);
	}
}
