package org.bugflux.pacman;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map.Entry;

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
	protected int lastCounterId;
	protected final HashMap<Integer, Score> counters;
	protected final int maxCounters;

	public Scoreboard(int maxCounters) {
		lastCounterId = 0;
		this.maxCounters = maxCounters;
		counters = new HashMap<Integer, Score>();
	}

	@Override
	public int addCounter(Gelem gid, int initialValue) {
		lastCounterId ++;

		if(screen != null) {
			screen.terminate();
		}
		screen = GBoard.init("Scoreboard", counters.size() + 1, 2, 50, 50);
		redraw();

		int gelemId = screen.registerGelem(gid);
		int valueId = screen.registerGelem(new StringGelem(Integer.toString(initialValue), Color.black));
		screen.draw(gelemId, counters.size(), 0);
		screen.draw(valueId, counters.size(), 1);
		
		counters.put(lastCounterId, new Score(gid, initialValue, valueId, counters.size()));

		return lastCounterId;
	}
	
	@Override
	public void setValue(int id, int value) {
		assert counters.containsKey(id);
		
		Score sc = counters.get(id);
		sc.value = value;
		screen.erase(sc.valueId, sc.index, 1);
		sc.valueId = screen.registerGelem(new StringGelem(Integer.toString(sc.value), Color.black));
		screen.draw(sc.valueId, sc.index, 1);
	}
	
	@Override
	public int getValue(int id) {
		assert counters.containsKey(id);
		return counters.get(id).value;
	}
	
	private void redraw() {
		int r = 0;
		for(Entry<Integer, Score> s : counters.entrySet()) {
			Score x = s.getValue();
			x.valueId = screen.registerGelem(new StringGelem(Integer.toString(x.value), Color.black));
			screen.draw(screen.registerGelem(x.gelem), x.index, 0);
			screen.draw(x.valueId, x.index, 1);
			r++;
		}
	}
	
	@Override
	public boolean isShowing() {
		return screen.isShowing();
	}

	@Override
	public void removeCounter(int id) {
		assert counters.containsKey(id);
		int itsIndex = counters.get(id).index;
		
		counters.remove(id);
		for(Entry<Integer, Score> s : counters.entrySet()) {
			Score x = s.getValue();
			if(x.index > itsIndex) {
				screen.erase(screen.gelemID(x.gelem), x.index, 0);
				screen.erase(x.valueId, x.index, 1);
				x.index--;
				screen.draw(screen.gelemID(x.gelem), x.index, 0);
				screen.draw(x.valueId, x.index, 1);
			}
		}
		screen.terminate();
		screen = GBoard.init("Scoreboard", counters.size(), 2, 50, 50);
		redraw();
	}
}

class Score {
	protected Gelem gelem;
	protected int value;
	protected int valueId;
	protected int index;

	public Score(Gelem gelem, int value, int valueId, int index) {
		this.gelem = gelem;
		this.value = value;
		this.valueId = valueId;
		this.index = index;
	}
}
