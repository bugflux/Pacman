package org.bugflux.pacman;

import java.awt.Color;

import org.bugflux.pacman.entities.Scorekeeper;

import pt.ua.gboard.GBoard;
import pt.ua.gboard.Gelem;
import pt.ua.gboard.StringGelem;

/**
 * keeps counters
 * 
 * @author ndray
 *
 */
public class Scoreboard implements Scorekeeper {
	protected GBoard screen;
	protected int numCounters;
	protected int counters[];
	protected int counterIds[];
	protected Gelem gelems[];

	public Scoreboard() {
		numCounters = 0;
//		counters = new int[numCounters];
//		gelems = new Gelem[numCounters];
//		counterIds = new int[numCounters];
	}
	
	public int addCounter(Gelem gid, int initialValue) {
		numCounters ++;
		// update scores
		int tmpCounters[] = new int[numCounters];
		Gelem tmpGelems[] = new Gelem[numCounters];
		counterIds = new int[numCounters];
		
		if(numCounters() > 1) { // not the first time
			System.arraycopy(counters, 0, tmpCounters, 0, counters.length);
			System.arraycopy(gelems, 0, tmpGelems, 0, gelems.length);
			screen.terminate();
		}
		tmpGelems[tmpGelems.length - 1] = gid;
		tmpCounters[tmpCounters.length - 1] = initialValue;
		counters = tmpCounters;
		gelems = tmpGelems;

		screen = GBoard.init("Scoreboard", numCounters, 2, 200, 200);
		redraw();
		
		
		return numCounters - 1;
	}
	
	public void setValue(int id, int value) {
		assert id < numCounters;
		
		counters[id] = value;
		screen.erase(counterIds[id], id, 1);
		counterIds[id] = screen.registerGelem(new StringGelem(Integer.toString(counters[id]), Color.black));
		screen.draw(counterIds[id], id, 1);
	}
	
	public int getValue(int id) {
		assert id < numCounters;
		return counters[id];
	}
	
	public int numCounters() {
		return numCounters;
	}
	
	private void redraw() {
		for(int r = 0; r < numCounters(); r++) {
			screen.draw(screen.registerGelem(gelems[r]), r, 0);
			counterIds[r] = screen.registerGelem(new StringGelem(Integer.toString(counters[r]), Color.black));
			screen.draw(counterIds[r], r, 1);
		}
	}
	
	public boolean isShowing() {
		return screen.isShowing();
	}
}
