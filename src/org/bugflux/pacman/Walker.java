package org.bugflux.pacman;

import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Walkable;
import org.bugflux.pacman.entities.Walkable.Direction;

import pt.ua.gboard.Gelem;

public class Walker implements Controllable {
	protected final Walkable w;
	protected Team team; // can change teams!
	protected boolean dead;
	protected Gelem gelem;

	public Walker(Walkable w, Coord c, Gelem g, Team t) {
		assert w != null;
		assert c != null;
		assert t != null;
		
		this.team = t;
		this.w = w;
		this.gelem = g;
		dead = false;
		
		if(w.isFree(c) && w.isHall(c)) {
			w.addWalker(this, c);
		}
		else {
			assert false;
		}
	}

	@Override
	public Coord getCoord() {
		return w.position(this);
	}

	@Override
	public Coord tryMove(Direction d) {
		return w.tryMove(this, d);
	}
	
	@Override
	public Gelem gelem() {
		return gelem;
	}

	@Override
	public boolean canMove(Direction d) {
		if(isDead()) {
			return false;
		}

		Coord c = w.newCoord(getCoord(), d);
		return w.validPosition(c) && w.isHall(c);
	}

	@Override
	public void die() {
		System.err.println("killed!");
	}
	
	@Override
	public boolean isDead() {
		return dead;
	}

	@Override
	public Team team() {
		return team;
	}
}
