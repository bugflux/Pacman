package org.bugflux.pacman;

import org.bugflux.pacman.Walkable.Direction;


public class WalkerMover {
	protected Walker w;

	public WalkerMover(Walker w) {
		this.w = w;
	}
	
	public void move(Direction d) {	
		w.tryMove(d);
	}
}
