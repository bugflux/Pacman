package org.bugflux.pacman.event;

import org.bugflux.lock.Signal;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.MorphingWalkable;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.Walkable.PositionType;

public class EventPositionToggler extends Thread implements Toggler {
	protected final MorphingWalkable m;
	protected final Signal s;
	protected volatile Coord c;
	
	public EventPositionToggler(MorphingWalkable m) {
		this.m = m;
		s = new Signal();
	}
	
	public void run() {
		while(true) {
			s.await();
			m.tryTogglePositionType(c);
		}
	}

	@Override
	public PositionType tryToggle(Coord c) {
		this.c = c;
		s.send();
		return null; // always does!!
	}
}
