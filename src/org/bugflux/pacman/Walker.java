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

	public Walker(Walkable w, Gelem g, Team t) {
		assert w != null;
		assert t != null;
		
		this.team = t;
		this.w = w;
		this.gelem = g;
		dead = false;
	}

	@Override
	public Coord getCoord() {
		return w.position(this);
	}

	@Override
	public Coord move(Direction d) {
		assert !isDead();
		return w.move(this, d);
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
		dead = true;
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
