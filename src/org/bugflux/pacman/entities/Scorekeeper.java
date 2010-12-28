package org.bugflux.pacman.entities;

import pt.ua.gboard.Gelem;

public interface Scorekeeper {
	/**
	 * Adds a counter to this scorekeeper.
	 * 
	 * @param gid A Gelem that represents the holder of the score.
	 * @param initialValue The initial value for the score.
	 * @return The id of the added score in this keeper, for future reference.
	 */
	public abstract int addCounter(Gelem gid, int initialValue);
	
	/**
	 * Remove a counter from the scorekeeping.
	 * 
	 * @param id The counter to remove.
	 */
	public abstract void removeCounter(int id);
	
	/**
	 * Sets a new score value for a score.
	 * 
	 * @param id The score to set.
	 * @param value The value to set the score to.
	 */
	public abstract void setValue(int id, int value);
	
	/**
	 * Gets a score value for a score.
	 * 
	 * @param id The score to get.
	 * @return The value of that score.
	 */
	public abstract int getValue(int id);
	
	/**
	 * Check whether this keeper is still active.
	 * @return
	 */
	public abstract boolean isOver();
}
