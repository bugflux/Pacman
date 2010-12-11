package org.bugflux.pacman;

import java.awt.Color;

import org.bugflux.pacman.entities.Collector;
import org.bugflux.pacman.entities.Walkable;

public class Pacman extends Walker implements Collector {
	protected int energy;

	public Pacman(Walkable w, Coord c) {
		super(w, c, new _PacmanGelem(), Team.GOOD);
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
}

class _PacmanGelem extends pt.ua.games.PacmanGelem {
	public _PacmanGelem() {
		super(Color.yellow, 75.0);
	}
}