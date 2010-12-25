package org.bugflux.pacman.monitor;

import org.bugflux.pacman.Scoreboard;
import org.bugflux.pacman.entities.Scorekeeper;

import pt.ua.gboard.Gelem;

public class MonitorScoreboard implements Scorekeeper {
	protected final Scorekeeper b;
	
	public MonitorScoreboard(Scoreboard b) {
		assert b != null;
		this.b = b;
	}

	@Override
	public synchronized int addCounter(Gelem gid, int initialValue) {
		return b.addCounter(gid, initialValue);
	}

	@Override
	public synchronized void setValue(int id, int value) {
		b.setValue(id, value);
	}

	@Override
	public synchronized int numCounters() {
		return b.numCounters();
	}

	@Override
	public synchronized boolean isShowing() {
		return b.isShowing();
	}
}
