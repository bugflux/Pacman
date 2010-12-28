package org.bugflux.pacman.shared.monitor;

import org.bugflux.pacman.Scoreboard;
import org.bugflux.pacman.entities.Scorekeeper;
import org.bugflux.pacman.shared.SharedScoreboard;

import pt.ua.gboard.Gelem;

public class MonitorScoreboard extends SharedScoreboard implements Scorekeeper {
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
	public synchronized boolean isShowing() {
		return b.isShowing();
	}

	@Override
	public void removeCounter(int id) {
		b.removeCounter(id);
	}

	@Override
	public int getValue(int id) {
		return b.getValue(id);
	}
}
