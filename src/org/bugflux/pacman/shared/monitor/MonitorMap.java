package org.bugflux.pacman.shared.monitor;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Bonus;
import org.bugflux.pacman.entities.Collector;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Scorekeeper;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.World;
import org.bugflux.pacman.shared.SharedMap;

public class MonitorMap extends SharedMap implements World {
	protected final World m;

	public MonitorMap(World m) {
		this.m = m;
	}

	@Override
	public synchronized void addPositionToggler(Toggler t) {
		m.addPositionToggler(t);
	}

	@Override
	public synchronized void addWalker(Collector w, Coord c) {
		m.addWalker(w, c);
	}
	
	@Override
	public synchronized  void addWalker(Controllable w, Coord c) {
		m.addWalker(w, c);
	}

	@Override
	public synchronized Coord move(Controllable w, Direction d) {
		return m.move(w, d);
	}
	
	@Override
	public synchronized boolean canMove(Controllable w, Direction d) {
		return m.canMove(w, d);
	}
	
	@Override
	public Coord move(Collector w, Direction d) {
		return m.move(w, d);
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
	public synchronized PositionType togglePositionType(Toggler t, Coord c) {
		return m.togglePositionType(t, c);
	}

	@Override
	public synchronized boolean canToggle(Toggler t, Coord c) {
		return m.canToggle(t, c);
	}

	@Override
	public synchronized void collect(Collector c) {
		m.collect(c);
	}

	@Override
	public synchronized boolean hasCollectable(Coord c) {
		return m.hasCollectable(c);
	}

	@Override
	public synchronized boolean isOver() {
		return m.isOver();
	}

	@Override
	public synchronized void addBonus(Bonus b, Coord c) {
		m.addBonus(b, c);
	}
	
	@Override
	public synchronized void removeBonus(Bonus b) {
		m.removeBonus(b);
	}

	@Override
	public synchronized boolean hasBonus(Coord c) {
		return m.hasBonus(c);
	}

	@Override
	public synchronized boolean hasBean(Coord c) {
		return m.hasBean(c);
	}
	
	@Override
	public synchronized Scorekeeper getScorekeeper() {
		return m.getScorekeeper();
	}
}
