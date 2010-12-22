package org.bugflux.pacman;

import java.awt.Color;

import org.bugflux.pacman.entities.Collector;
import org.bugflux.pacman.entities.Garden;
import org.bugflux.pacman.entities.Walkable.Direction;

public class Pacman extends Walker implements Collector {
	protected int energy;
	protected Garden w;

	public Pacman(Garden w) {
		super(w, new _PacmanGelem(Color.yellow), Team.GOOD);
		this.w = w;
		energy = 10;
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

	@Override
	public int energy() {
		return energy;
	}
	
	@Override
	public void die() {
		dead = true;
		gelem = new _PacmanGelem(Color.gray);
	}
	
	@Override
	public Coord tryMove(Direction d) {
		if(!isDead()) {
			return w.tryMove(this, d);
		}
		return null;
	}
}

class _PacmanGelem extends pt.ua.games.PacmanGelem {
	public _PacmanGelem(Color color) {
		super(color, 75.0);
	}
}