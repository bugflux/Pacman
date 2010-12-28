package org.bugflux.pacman;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
public class Scoreboard implements Scorekeeper, WindowListener {
	protected final GBoard screen;
	protected final HashMap<Integer, Score> counters;
	protected final int maxCounters;
	protected int lastCounterId;
	protected boolean isOver;

	public Scoreboard(int maxCounters) {
		assert maxCounters > 1;

		isOver = false;
		lastCounterId = 0;
		this.maxCounters = maxCounters;
		counters = new HashMap<Integer, Score>();
		screen = GBoard.init("Scoreboard", maxCounters, 2, 50, 50);
		screen.frame().addWindowListener(this);
	}
	
	@Override public void windowOpened(WindowEvent e) {}
	@Override public void windowClosing(WindowEvent e) { isOver = true; }
	@Override public void windowClosed(WindowEvent e) {}
	@Override public void windowIconified(WindowEvent e) {}
	@Override public void windowDeiconified(WindowEvent e) {}
	@Override public void windowActivated(WindowEvent e) {}
	@Override public void windowDeactivated(WindowEvent e) {}

	@Override
	public int addCounter(Gelem gid, int initialValue) {
		assert counters.size() < maxCounters;
		lastCounterId++;

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
	
	@Override
	public boolean isOver() {
		return isOver;
	}

	@Override
	public void removeCounter(int id) {
		assert counters.containsKey(id);

		Score it = counters.get(id);
		int itsIndex = it.index;
		
		screen.erase(screen.gelemID(it.gelem), it.index, 0);
		screen.erase(it.valueId, it.index, 1);
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
	}
}

// just a tuple
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
