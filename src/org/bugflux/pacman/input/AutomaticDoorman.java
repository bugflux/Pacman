package org.bugflux.pacman.input;

import java.util.Random;

import org.bugflux.lock.UncheckedInterruptedException;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.Walkable.PositionType;

public class AutomaticDoorman extends Thread {
	protected final Coord door;
	protected final int minWaitPeriod, minToggledPeriod;
	protected boolean toggled;
	protected Toggler toggler;

	/**
	 * Attempts to toggle a door every [minWaitPeriod,2 * minWaitPeriod] for a period of [minToggledPeriod,2 * minToggledPeriod].
	 * These times could be longer if the the toggle fails.
	 * 
	 * @param map
	 * @param door
	 * @param minWaitPeriod
	 * @param minToggledPeriod
	 */
	public AutomaticDoorman(Toggler toggler, Coord door, int minWaitPeriod, int minToggledPeriod) {
		assert door != null;
		assert minWaitPeriod > 0 && minToggledPeriod > 0;
		
		this.toggler = toggler;
		toggled = false;
		this.door = door;
		this.minWaitPeriod = minWaitPeriod;
		this.minToggledPeriod = minToggledPeriod;
	}
	
	public void run() {
		PositionType current = null, previous = null;
		Random rand = new Random();
		
		while(true) { // TODO terminate properly
			try {
				if(!toggled) {
					Thread.sleep(minWaitPeriod + rand.nextInt(minWaitPeriod));
				}
				else {
					Thread.sleep(minToggledPeriod + rand.nextInt(minToggledPeriod));
				}

				previous = current;
				current = toggler.tryToggle(door);
				if(current == previous) {
					toggled = false;
				}
				else {
					toggled = true;
				}
			}
			catch (InterruptedException e) {
				throw new UncheckedInterruptedException(e);
			}
		}
	}
}
