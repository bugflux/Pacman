package org.bugflux.pacman.shared.monitor;

import org.bugflux.pacman.entities.Bonus;
import org.bugflux.pacman.shared.SharedBonus;

import pt.ua.gboard.Gelem;

public class MonitorBonus extends SharedBonus implements Bonus {
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
