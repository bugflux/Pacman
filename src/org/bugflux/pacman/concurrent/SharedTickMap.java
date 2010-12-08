package org.bugflux.pacman.concurrent;

import org.bugflux.lock.Metronome;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.Map;
import org.bugflux.pacman.Walkable;
import org.bugflux.pacman.Walker;


public class SharedTickMap extends SharedMap implements Walkable {
	private final Metronome tick;

	public SharedTickMap(Map m, Metronome tick) {
		super(m);
		this.tick = tick;
	}

	@Override
	public synchronized Coord move(Walker w, Direction d) {
		Coord r = m.move(w, d);
		tick.await();
		return r;
	}

	@Override
	public synchronized void addWalker(Walker w, Coord c) {
		m.addWalker(w, c);
		tick.await();
	}
}
