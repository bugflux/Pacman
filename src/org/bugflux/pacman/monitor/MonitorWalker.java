package org.bugflux.pacman.monitor;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Walkable.Direction;

import pt.ua.gboard.Gelem;

public class MonitorWalker implements Controllable {
	protected final Controllable w;
	
	/**
	 * The controller must be using a SharedMap!!
	 * @param w
	 */
	public MonitorWalker(Controllable w) {
		assert w != null;
		
		this.w = w;
	}

	@Override
	public synchronized Coord getCoord() {
		return w.getCoord();
	}

	@Override
	public synchronized Coord move(Direction d) {
		return w.move(d);
	}

	@Override
	public synchronized boolean canMove(Direction d) {
		return w.canMove(d);
	}

	@Override
	public synchronized Gelem gelem() {
		return w.gelem();
	}


	@Override
	public synchronized Team team() {
		return w.team();
	}

	@Override
	public synchronized void die() {
		w.die();
	}

	@Override
	public synchronized boolean isDead() {
		return w.isDead();
	}
}
