package org.bugflux.pacman;

import org.bugflux.pacman.entities.Mover;
import org.bugflux.pacman.entities.Walkable.Direction;
import org.bugflux.pacman.entities.Controllable;


public class WalkerMover implements Mover {
	protected final Controllable c;

	public WalkerMover(Controllable c) {
		this.c = c;
	}
	
	public Coord tryMove(Direction d) {
		if(c.canMove(d)) {
			return c.move(d);
		}

		return null;
	}
}
