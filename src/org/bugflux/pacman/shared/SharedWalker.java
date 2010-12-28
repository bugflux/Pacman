package org.bugflux.pacman.shared;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Walkable.Direction;

import pt.ua.gboard.Gelem;

public abstract class SharedWalker implements Controllable {

	@Override
	public abstract Team team();

	@Override
	public abstract Gelem gelem();

	@Override
	public abstract void die();

	@Override
	public abstract boolean isDead();

	@Override
	public abstract Coord getCoord();

	@Override
	public abstract Coord move(Direction d);

	@Override
	public abstract boolean canMove(Direction d);
}
