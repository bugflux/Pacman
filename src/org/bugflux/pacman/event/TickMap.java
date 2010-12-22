package org.bugflux.pacman.event;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.bugflux.lock.Metronome;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Collector;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.World;


public class TickMap implements World {
	protected final World w;
	protected final Metronome tick;
	protected Lock mutex;

	public TickMap(World w, Metronome tick) {
		mutex = new ReentrantLock();
		this.w = w;
		this.tick = tick;
	}
	
	@Override
	public void addPositionToggler(Toggler t) {
		mutex.lock();
		w.addPositionToggler(t);
		mutex.unlock();
	}
	
	@Override
	public void addWalker(Controllable co, Coord c) {
		mutex.lock();
		w.addWalker(co, c);
		mutex.unlock();
	}
	
	@Override
	public void addWalker(Collector co, Coord c) {
		mutex.lock();
		w.addWalker(co, c);
		mutex.unlock();
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
	public synchronized Coord tryMove(Collector c, Direction d) {
		mutex.lock();
		Coord r = w.tryMove(c, d);
		mutex.unlock();
		tick.await();

		return r;
	}
	
	@Override
	public Coord position(Controllable c) {
		mutex.lock();
		Coord result = w.position(c);
		mutex.unlock();
		
		return result;
	}

	@Override
	public PositionType tryTogglePositionType(Toggler t, Coord c) {
		mutex.lock();
		PositionType r = w.tryTogglePositionType(t, c);
		mutex.unlock();
		tick.await();

		return r;
	}

	@Override
	public boolean canToggle(Toggler t, Coord c) {
		mutex.lock();
		boolean result = w.canToggle(t, c);
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

	@Override
	public void tryCollect(Collector c) {
		mutex.lock();
		w.tryCollect(c);
		mutex.unlock();
	}

	@Override
	public boolean hasCollectable(Coord c) {
		mutex.lock();
		boolean result = w.hasCollectable(c);
		mutex.unlock();
		
		return result;
	}
}
