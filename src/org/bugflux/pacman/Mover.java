package org.bugflux.pacman;

import org.bugflux.pacman.Walkable.Direction;


public abstract class Mover {
	protected Walker w;

	public Mover(Walker w) {
		this.w = w;
	}
	
	public void move(Direction d) {	
		w.tryMove(d);
	}
}
