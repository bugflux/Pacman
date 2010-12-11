package org.bugflux.pacman;

import java.awt.Color;

import org.bugflux.pacman.entities.Collector;
import org.bugflux.pacman.entities.Walkable;

public class Pacman extends Walker implements Collector {
	protected int energy;

	public Pacman(Walkable w, Coord c) {
		super(w, c, new _PacmanGelem(Color.yellow), Team.GOOD);
		energy = 10;
	}

	@Override
	public int gainEnergy(int amount) {
		return energy += amount;
	}

	@Override
	public int looseEnergy(int amount) {
		return energy -= amount;
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