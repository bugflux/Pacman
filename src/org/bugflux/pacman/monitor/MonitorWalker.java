package org.bugflux.pacman.monitor;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Walkable.Direction;

import pt.ua.gboard.Gelem;

public class MonitorWalker implements Controllable {
	protected final Controllable w;
	
	public MonitorWalker(Controllable w) {
		this.w = w;
	}

	@Override
	public synchronized Coord getCoord() {
		return w.getCoord();
	}

	@Override
	public synchronized Coord tryMove(Direction d) {
		return w.tryMove(d);
	}

	@Override
	public synchronized boolean canMove(Direction d) {
		return w.canMove(d);
	}

	@Override
	public Gelem gelem() {
		return w.gelem();
	}

	@Override
	public void kill() {
		w.kill();
	}
}
