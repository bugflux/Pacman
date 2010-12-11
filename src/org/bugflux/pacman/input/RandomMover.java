package org.bugflux.pacman.input;

import java.util.Random;

import org.bugflux.lock.UncheckedInterruptedException;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Mover;
import org.bugflux.pacman.entities.Walkable.Direction;

public class RandomMover extends Thread {
	protected final Mover m;
	
	public RandomMover(Mover m) {
		this.m = m;
	}
	
	public void run() {
		Random r = new Random();
		Direction d[] = { Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT };
		Direction previousD, currentD;
		Coord currentC, previousC;
		
		currentC = null;
		previousC = null;
		currentD = d[r.nextInt(d.length)];
		while(true) {
			previousD = currentD;
			currentD = d[r.nextInt(d.length)]; // new, Random direction
			
			// if last direction originated a move,
			// move in the same direction with .75 probability!
			if(currentC != previousC) {
				currentD = r.nextDouble() < 0.75 ? previousD : currentD;
			}
			
			previousC = currentC;
			currentC = m.tryMove(currentD);

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new UncheckedInterruptedException(e);
			} // TODO remove
		}
	}
}
