package org.bugflux.pacman.shared;

import org.bugflux.pacman.entities.Scorekeeper;

import pt.ua.gboard.Gelem;

public abstract class SharedScoreboard implements Scorekeeper {

	@Override
	public abstract int addCounter(Gelem gid, int initialValue);

	@Override
	public abstract void removeCounter(int id);

	@Override
	public abstract void setValue(int id, int value);

	@Override
	public abstract int getValue(int id);

	@Override
	public abstract boolean isOver();
}
