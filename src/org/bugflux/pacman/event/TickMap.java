package org.bugflux.pacman.event;

import org.bugflux.lock.Metronome;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Collector;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.World;


public class TickMap implements World {
	protected final World w;
	protected final Metronome tick;

	public TickMap(World w, Metronome tick) {
		assert w != null;
		assert tick != null;

		this.w = w;
		this.tick = tick;
	}
	
	@Override
	public void addPositionToggler(Toggler t) {
		w.addPositionToggler(t);
		tick.await();
	}
	
	@Override
	public void addWalker(Controllable co, Coord c) {
		w.addWalker(co, c);
		tick.await();
	}
	
	@Override
	public void addWalker(Collector co, Coord c) {
		w.addWalker(co, c);
		tick.await();
	}

	@Override
	public Coord tryMove(Controllable c, Direction d) {
		Coord r = w.tryMove(c, d);
		tick.await();

		return r;
	}
	
	@Override
	public Coord tryMove(Collector c, Direction d) {
		Coord r = w.tryMove(c, d);
		tick.await();

		return r;
	}
	
	@Override
	public Coord position(Controllable c) {
		Coord result = w.position(c);
		tick.await();
		
		return result;
	}

	@Override
	public PositionType tryTogglePositionType(Toggler t, Coord c) {
		PositionType r = w.tryTogglePositionType(t, c);
		tick.await();

		return r;
	}

	@Override
	public boolean canToggle(Toggler t, Coord c) {
		boolean result = w.canToggle(t, c);
		tick.await();

		return result;
	}

	@Override
	public boolean validPosition(Coord c) {
		return w.validPosition(c);
	}

	@Override
	public boolean isFree(Coord c) {
		boolean result = w.isFree(c);
		tick.await();

		return result;
	}

	@Override
	public boolean isHall(Coord c) {
		boolean result = w.isHall(c);
		tick.await();

		return result;
	}

	@Override
	public Coord newCoord(Coord c, Direction d) {
		return w.newCoord(c, d);
	}

	@Override
	public PositionType positionType(Coord c) {
		PositionType result = w.positionType(c);
		tick.await();

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
		w.tryCollect(c);
		tick.await();
	}

	@Override
	public boolean hasCollectable(Coord c) {
		boolean result = w.hasCollectable(c);
		tick.await();

		return result;
	}

	@Override
	public boolean isOver() {
		boolean result = w.isOver();
		tick.await();

		return result;
	}
}
