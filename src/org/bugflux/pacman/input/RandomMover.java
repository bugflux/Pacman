package org.bugflux.pacman.input;

import java.util.Random;

import org.bugflux.pacman.entities.Mover;
import org.bugflux.pacman.entities.Walkable.Direction;

public class RandomMover extends Thread {
	protected final Mover m;
	
	public RandomMover(Mover m) {
		this.m = m;
	}
	
	public void run() {
		while(true) {
			Random r = new Random();
			Direction d[] = { Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT };
			m.tryMove(d[r.nextInt(d.length)]);
		}
	}
}
