package org.bugflux.pacman;

import java.awt.Color;

import org.bugflux.pacman.Walkable.Direction;

import pt.ua.games.PacmanGelem;
import pt.ua.gboard.Gelem;

public class Pacman implements Walker {
	protected static final Gelem gelem = new PacmanGelem(Color.yellow, 75.0);
	protected final Walkable w;
	protected Coord c;

	public Pacman(Walkable w, Coord c) {
		assert w != null && c != null;

		this.w = w;
		this.c = c;
		this.w.addWalker(this, this.c);
	}

	@Override
	public Coord getCoord() {
		return c;
	}

	@Override
	public void tryMove(Direction d) {
		if(canMove(d)) {
			c = w.move(this, d);
		}
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
}
