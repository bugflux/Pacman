package org.bugflux.pacman.monitor;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.MorphingWalkable;

public class MonitorWalkable extends Thread implements MorphingWalkable {
	protected final MorphingWalkable m;

	public MonitorWalkable(MorphingWalkable m) {
		this.m = m;
	}

	@Override
	public synchronized Coord tryMove(Controllable w, Direction d) {
		return m.tryMove(w, d);
	}

	@Override
	public void addWalker(Controllable w, Coord c) {
		m.addWalker(w, c);
	}
	
	@Override
	public synchronized Coord position(Controllable c) {
		return m.position(c);
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

	@Override
	public synchronized PositionType positionType(Coord c) {
		return m.positionType(c);
	}

	@Override
	public int height() {
		return m.height();
	}

	@Override
	public int width() {
		return m.width();
	}

	@Override
	public synchronized PositionType tryTogglePositionType(Coord c) {
		return m.tryTogglePositionType(c);
	}

	@Override
	public synchronized boolean canToggle(Coord c) {
		return m.canToggle(c);
	}
}
