package org.bugflux.pacman.monitor;

import org.bugflux.pacman.entities.Bonus;

import pt.ua.gboard.Gelem;

public class MonitorBonus extends Thread implements Bonus {
	protected final Bonus b;
	
	public MonitorBonus(Bonus b) {
		assert b != null;

		this.b = b;
	}

	@Override
	public synchronized Property property() {
		return b.property();
	}

	@Override
	public synchronized Gelem gelem() {
		return b.gelem();
	}
}
