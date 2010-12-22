package org.bugflux.pacman.event;

import org.bugflux.lock.Signal;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.WalkerMover;
import org.bugflux.pacman.entities.Mover;
import org.bugflux.pacman.entities.Walkable.Direction;

public class EventWalkerMover implements Mover {
	protected final WalkerMover m;
	protected volatile boolean hasMove;
	protected volatile Direction d;
	protected final Signal s;
	
	public EventWalkerMover(WalkerMover m) {
		this.m = m;
		s = new Signal();
	}

	@Override
	public Coord tryMove(Direction d) {
		return m.tryMove(d);
	}
}
