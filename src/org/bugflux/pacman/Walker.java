package org.bugflux.pacman;

import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Walkable;
import org.bugflux.pacman.entities.Walkable.Direction;

import pt.ua.gboard.Gelem;

public class Walker implements Controllable {
	protected final Gelem gelem;
	protected final Walkable w;
	protected Coord c;

	public Walker(Walkable w, Coord c, Gelem g) {
		assert w != null && c != null;

		this.w = w;
		this.c = c;
		this.gelem = g;
		this.w.addWalker(this, this.c);
	}

	@Override
	public Coord getCoord() {
		return c;
	}

	@Override
	public Coord tryMove(Direction d) {
		return c = w.tryMove(this, d);
	}
	
	@Override
	public Gelem gelem() {
		return gelem;
	}

	@Override
	public boolean canMove(Direction d) {
		Coord c = w.newCoord(this.c, d);
		return w.validPosition(c) && w.isHall(c);
	}

	@Override
	public void kill() {
		System.err.println("killed!");
	}
}
