package org.bugflux.pacman.shared;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Bonus;
import org.bugflux.pacman.entities.Collector;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Scorekeeper;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.World;

public abstract class SharedMap implements World {

	@Override
	public abstract void collect(Collector c);

	@Override
	public abstract boolean hasCollectable(Coord c);

	@Override
	public abstract void addWalker(Collector w, Coord c);

	@Override
	public abstract void addBonus(Bonus b, Coord c);

	@Override
	public abstract void removeBonus(Bonus b);

	@Override
	public abstract boolean hasBonus(Coord c);

	@Override
	public abstract boolean hasBean(Coord c);

	@Override
	public abstract Scorekeeper getScorekeeper();

	@Override
	public abstract Coord move(Controllable w, Direction d);

	@Override
	public abstract boolean canMove(Controllable w, Direction d);

	@Override
	public abstract void addWalker(Controllable w, Coord c);

	@Override
	public abstract Coord position(Controllable w);

	@Override
	public abstract boolean validPosition(Coord c);

	@Override
	public abstract boolean isFree(Coord c);

	@Override
	public abstract boolean isHall(Coord c);

	@Override
	public abstract Coord newCoord(Coord c, Direction d);

	@Override
	public abstract PositionType positionType(Coord c);

	@Override
	public abstract int height();

	@Override
	public abstract int width();

	@Override
	public abstract boolean isOver();

	@Override
	public abstract void addPositionToggler(Toggler t);

	@Override
	public abstract PositionType togglePositionType(Toggler t, Coord c);

	@Override
	public abstract boolean canToggle(Toggler t, Coord c);
}
