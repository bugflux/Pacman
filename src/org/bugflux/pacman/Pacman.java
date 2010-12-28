package org.bugflux.pacman;

import java.awt.Color;

import org.bugflux.pacman.entities.Collector;
import org.bugflux.pacman.entities.Garden;
import org.bugflux.pacman.entities.Scorekeeper;
import org.bugflux.pacman.entities.Walkable.Direction;

public class Pacman extends Walker implements Collector {
	protected int energy;
	protected final Garden w;
	protected final Scorekeeper score;
	protected final int scoreId;
	
	public Pacman(Garden w, Scorekeeper score) {
		super(w, new _PacmanGelem(Color.yellow), Team.GOOD);
		assert score != null;
		this.w = w; // needed to benefit from polymorphism { move(Collector) is present in Garden, not Walkable }
		energy = 10;
		this.score = score;
		scoreId = score.addCounter(gelem(), energy);
	}

	@Override
	public int gainEnergy(int amount) {
		return energy += amount;
	}

	@Override
	public int looseEnergy(int amount) {
		energy -= amount;
		if(energy <= 0) {
			die();
		}
		return energy;
	}
	
	// must override this here because this implements collector.
	// the tryMove in Walker implements controllable, so it won't collect.
	@Override
	public Coord move(Direction d) {
		if(!isDead() && canMove(d)) {
			Coord result = w.move(this, d);
			// could have died in result of that move:
			if(!w.isOver() && w.hasCollectable(result)) {
				w.collect(this);
				score.setValue(scoreId, energy());
			}
			return result;
		}

		return null;
	}

	@Override
	public int energy() {
		return energy;
	}
	
	@Override
	public void die() {
		dead = true;
		gelem = new _PacmanGelem(Color.gray);
	}
}

class _PacmanGelem extends pt.ua.games.PacmanGelem {
	public _PacmanGelem(Color color) {
		super(color, 75.0);
	}
}