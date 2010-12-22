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
	public synchronized PositionType tryToggle(Coord c) {
		return t.tryToggle(c);
	}

	@Override
	public synchronized void gameOver() {
		t.gameOver();
	}
}
