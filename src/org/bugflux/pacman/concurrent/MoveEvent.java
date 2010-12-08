package org.bugflux.pacman.concurrent;

import org.bugflux.lock.UncheckedInterruptedException;
import org.bugflux.pacman.Walkable.Direction;

public class MoveEvent implements MoveEventReceiver, MoveEventSender {
	protected Direction nextD;
	protected volatile boolean hasMove;
	
	public MoveEvent() {
		hasMove = false;
	}
	
	public synchronized void put(Direction d) {
		nextD = d;
		hasMove = true;

		notify();
	}
	
	public synchronized Direction get() {
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
}
