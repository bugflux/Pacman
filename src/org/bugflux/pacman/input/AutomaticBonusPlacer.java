package org.bugflux.pacman.input;

import java.util.Random;

import org.bugflux.lock.UncheckedInterruptedException;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.FreezeOpposingBonus;
import org.bugflux.pacman.InvincibilityBonus;
import org.bugflux.pacman.entities.Bonus;
import org.bugflux.pacman.entities.Garden;
import org.bugflux.pacman.entities.Scorekeeper;

public class AutomaticBonusPlacer extends Thread {
	protected final Garden garden;
	protected final int everyMs, duringMs;
	protected final Scorekeeper sc;

	/**
	 * will try to randomly place a random property Bonus
	 * on a garden every everMs for a period of duringMs.
	 * 
	 * When activated, the bonus will last duringMs.
	 * 
	 * Might fail if it generates
	 * 
	 * @param everyMs
	 * @param duringMs
	 */
	public AutomaticBonusPlacer(Garden garden, int everyMs, int duringMs, Scorekeeper sc) {
		assert garden != null;
		assert sc != null;
		assert everyMs > 0;
		assert duringMs > 0;

		this.garden = garden;
		this.everyMs = everyMs;
		this.duringMs = duringMs;
		this.sc = sc;
	}
	
	private Bonus getRandomBonus(Random r) {
		Bonus b = null;
		switch(r.nextInt(2)) {
			case 0: b = new InvincibilityBonus(); break;
			case 1: b = new FreezeOpposingBonus(); break;
			default: assert false;
		}
		
		return b;
	}
	
	public void run() {
		boolean isOver = false;
		Bonus b = null;
		Coord c;
		Random rand = new Random();
		// random factory

		while(!isOver) {
			int counterId;
			try {
				Thread.sleep(everyMs);
			
				c = new Coord(rand.nextInt(garden.height()), rand.nextInt(garden.width()));
				b = getRandomBonus(rand);
	
				synchronized(garden) {
					if(!(isOver = garden.isOver())) {
						if(garden.isFree(c) && !garden.hasBonus(c) && garden.isHall(c)) {
							garden.addBonus(b, c);
						} else { continue; }
					}
				}

				// if not active after duringMs, remove from map
				int counter = duringMs / 1000;
				synchronized(garden) {
					if(!(isOver = garden.isOver())) {
						counterId = sc.addCounter(b.gelem(), counter);
					} else { continue; }
				}
				for(; counter >= 0 && !isOver; counter--) {
					synchronized(garden) {
						if(!(isOver = garden.isOver())) {
							sc.setValue(counterId, counter);
						} else { continue; }
					}
					Thread.sleep(1000);
				}
			}
			catch (InterruptedException e) {
				throw new UncheckedInterruptedException(e);
			}
			
			synchronized(garden) {
				if(!(isOver = garden.isOver())) {
					garden.removeBonus(b);
					sc.removeCounter(counterId);
				}
			}
		}
	}
}
