package org.bugflux.pacman.event;

import org.bugflux.lock.Signal;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.WalkerMover;
import org.bugflux.pacman.entities.Mover;
import org.bugflux.pacman.entities.Walkable.Direction;

public class EventWalkerMover extends Thread implements Mover {
	protected final WalkerMover m;
	protected volatile boolean hasMove;
	protected volatile Direction d;
	protected final Signal s;
	
	public EventWalkerMover(WalkerMover m) {
		this.m = m;
		s = new Signal();
	}
	
	public void run() {
		while(true) {
			s.await();
			m.tryMove(d);
		}
	}

	/**
	 * TODO returned Coord doesn't make sense!
	 */
	@Override
	public Coord tryMove(Direction d) {
		this.d = d;
		s.send();
		return null; // always does!!
	}
}
