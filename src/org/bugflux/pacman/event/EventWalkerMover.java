package org.bugflux.pacman.event;

import org.bugflux.lock.UncheckedInterruptedException;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Mover;
import org.bugflux.pacman.entities.Walkable.Direction;

public class EventWalkerMover extends Thread implements Mover {
	protected Direction nextD;
	protected volatile boolean hasMove;
	
	public EventWalkerMover() {
		hasMove = false;
	}
	
	public synchronized void put(Direction d) {
		nextD = d;
		hasMove = true;

		notify();
	}
	
	protected synchronized Direction get() {
		while(!hasMove) {
			try {
				wait();
			}
			catch(InterruptedException e) {
				throw new UncheckedInterruptedException(e);
			}
		}

		hasMove = false;
		return nextD;
	}

	@Override
	public Coord tryMove(Direction d) {
		// TODO Auto-generated method stub
		return null;
	}
}
