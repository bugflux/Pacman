package org.bugflux.pacman.event;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.bugflux.lock.Metronome;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.MorphingWalkable;


public class TickMap implements MorphingWalkable {
	protected final MorphingWalkable w;
	protected final Metronome tick;
	protected Lock mutex;

	public TickMap(MorphingWalkable w, Metronome tick) {
		mutex = new ReentrantLock();
		this.w = w;
		this.tick = tick;
	}

	@Override
	public Coord tryMove(Controllable c, Direction d) {
		mutex.lock();
		Coord r = w.tryMove(c, d);
		mutex.unlock();

		tick.await();
		return r;
	}

	@Override
	public void addWalker(Controllable co, Coord c) {
		mutex.lock();
		w.addWalker(co, c);
		mutex.unlock();
	}

	@Override
	public PositionType tryTogglePositionType(Coord c) {
		mutex.lock();
		PositionType r = w.tryTogglePositionType(c);
		mutex.unlock();

		tick.await();

		return r;
	}

	@Override
	public boolean canToggle(Coord c) {
		mutex.lock();
		boolean result = w.canToggle(c);
		mutex.unlock();

		return result;
	}

	@Override
	public boolean validPosition(Coord c) {
		return w.validPosition(c);
	}

	@Override
	public boolean isFree(Coord c) {
		mutex.lock();
		boolean result = w.isFree(c);
		mutex.unlock();

		return result;
	}

	@Override
	public boolean isHall(Coord c) {
		mutex.lock();
		boolean result = w.isHall(c);
		mutex.unlock();

		return result;
	}

	@Override
	public Coord newCoord(Coord c, Direction d) {
		return w.newCoord(c, d);
	}

	@Override
	public PositionType positionType(Coord c) {
		mutex.lock();
		PositionType result = w.positionType(c);
		mutex.unlock();
		
		return result;
	}

	@Override
	public int height() {
		return w.height();
	}

	@Override
	public int width() {
		return w.width();
	}
}
