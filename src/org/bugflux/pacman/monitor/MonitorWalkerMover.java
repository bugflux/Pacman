package org.bugflux.pacman.monitor;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Mover;
import org.bugflux.pacman.entities.Walkable.Direction;

public class MonitorWalkerMover implements Mover {
	protected Mover m;

	public MonitorWalkerMover(Mover m) {
		this.m = m;
	}

	public synchronized Coord tryMove(Direction d) {
		return m.tryMove(d);
	}
}
