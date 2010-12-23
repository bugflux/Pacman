package org.bugflux.pacman.input;

import java.util.Random;

import org.bugflux.lock.UncheckedInterruptedException;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Walkable;
import org.bugflux.pacman.entities.Walkable.Direction;

public class RandomMover extends Thread {
	protected final Controllable m;
	protected final Walkable game;
	protected int ms;
	
	public RandomMover(Walkable game, Controllable m, int ms) {
		assert game != null;
		assert m != null;
		assert ms >= 0;

		this.game = game;
		this.m = m;
		this.ms = ms;
	}
	
	public void run() {
		Random r = new Random();
		Direction d[] = { Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT };
		Direction previousD, currentD;
		Coord currentC, previousC;
		boolean isOver = false;
		
		currentC = null;
		previousC = null;
		currentD = d[r.nextInt(d.length)];
		while(!isOver) {
			if(ms > 0) {
				try {
					Thread.sleep(ms);
				} catch (InterruptedException e) {
					throw new UncheckedInterruptedException(e);
				} // TODO don't sleep!
			}

			previousD = currentD;
			currentD = d[r.nextInt(d.length)]; // new, Random direction
			
			// if last direction originated a move,
			// move in the same direction with .75 probability!
			if(currentC != previousC) {
				currentD = r.nextDouble() < 0.75 ? previousD : currentD;
			}
			
			previousC = currentC;
			synchronized(game) {
				if(!(isOver = game.isOver())) {
					synchronized(m) {
						if(m.canMove(currentD)) {
							currentC = m.move(currentD);
						}
					}
				}
			}
		}
	}
}
