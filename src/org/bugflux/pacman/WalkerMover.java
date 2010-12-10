package org.bugflux.pacman;

import org.bugflux.pacman.entities.Mover;
import org.bugflux.pacman.entities.Walkable.Direction;
import org.bugflux.pacman.entities.Controllable;


public class WalkerMover implements Mover {
	protected Controllable w;

	public WalkerMover(Controllable w) {
		this.w = w;
	}
	
	public Coord tryMove(Direction d) {	
		return w.tryMove(d);
	}
}
