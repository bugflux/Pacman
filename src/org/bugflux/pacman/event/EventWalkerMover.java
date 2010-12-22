package org.bugflux.pacman.event;

import org.bugflux.lock.Signal;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.WalkerMover;
import org.bugflux.pacman.entities.Mover;
import org.bugflux.pacman.entities.Walkable;
import org.bugflux.pacman.entities.Walkable.Direction;

public class EventWalkerMover extends Thread implements Mover {
	protected final Walkable w;
	protected final WalkerMover m;
	protected volatile boolean hasMove;
	protected volatile Direction d;
	protected Signal s;
	
	public EventWalkerMover(Walkable w, WalkerMover m) {
		assert w != null;
		assert m != null;
		
		s = new Signal();
		hasMove = false;
		this.w = w;
		this.m = m;
	}
	
	@Override
	public void run() {
		boolean isOver = false;
		while(!isOver) { // TODO stop
			s.await();
			m.tryMove(d);
		}
	}

	@Override
	public Coord tryMove(Direction d) {
		this.d = d;
		hasMove = true;
		s.send();
System.err.println("tryMove consumed");
		return null;
	}
}
