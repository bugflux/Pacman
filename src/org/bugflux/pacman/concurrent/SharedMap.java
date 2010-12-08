package org.bugflux.pacman.concurrent;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.Map;
import org.bugflux.pacman.Walkable;
import org.bugflux.pacman.Walker;


public class SharedMap extends Thread implements Walkable {
	protected final Map m;

	public SharedMap(Map m) {
		this.m = m;
	}

	@Override
	public synchronized Coord move(Walker w, Direction d) {
		return m.move(w, d);
	}

	@Override
	public void addWalker(Walker w, Coord c) {
		m.addWalker(w, c);
	}

	@Override
	public synchronized boolean validPosition(Coord c) {
		return m.validPosition(c);
	}

	@Override
	public synchronized boolean isFree(Coord c) {
		return m.isFree(c);
	}

	@Override
	public synchronized boolean isHall(Coord c) {
		// synchronized because walls change into doors and vice-versa
		return m.isHall(c);
	}

	@Override
	public Coord newCoord(Coord c, Direction d) {
		return m.newCoord(c, d);
	}
}
