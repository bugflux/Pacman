package org.bugflux.pacman.concurrent;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.Pacman;
import org.bugflux.pacman.Walkable;
import org.bugflux.pacman.Walkable.Direction;
import org.bugflux.pacman.Walker;

import pt.ua.gboard.Gelem;

public class SharedPacman extends Pacman implements Walker {
	public SharedPacman(Walkable w, Coord c) {
		super(w, c);
	}

	@Override
	public synchronized Coord getCoord() {
		return c;
	}

	@Override
	public synchronized void tryMove(Direction d) {
		synchronized(w) {
			if(canMove(d)) {
				c = w.move(this, d);
			}
		}
	}

	@Override
	public synchronized boolean canMove(Direction d) {
		synchronized(w) {
			Coord c = w.newCoord(this.c, d);
			return w.validPosition(c) && w.isHall(c);
		}
	}

	@Override
	public Gelem gelem() {
		return gelem;
	}
}
