package org.bugflux.pacman.event;

import org.bugflux.lock.Signal;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.Walkable.PositionType;

public class EventPositionToggler extends Thread implements Toggler {
	protected final Toggler t;
	protected final Signal s;
	protected volatile Coord c;
	
	public EventPositionToggler(Toggler t) {
		this.t = t;
		s = new Signal();
	}
	
	public void run() {
		while(true) {
			s.await();
			t.tryToggle(c);
		}
	}

	@Override
	public PositionType tryToggle(Coord c) {
		this.c = c;
		s.send();
		return null; // always does!!
	}
}
