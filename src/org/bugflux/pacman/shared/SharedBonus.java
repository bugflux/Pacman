package org.bugflux.pacman.shared;

import org.bugflux.pacman.entities.Bonus;

import pt.ua.gboard.Gelem;

public abstract class SharedBonus implements Bonus {

	@Override
	public abstract Property property();

	@Override
	public abstract Gelem gelem();
}
