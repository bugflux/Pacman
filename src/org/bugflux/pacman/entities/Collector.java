package org.bugflux.pacman.entities;

public interface Collector extends Controllable {
	/**
	 * Gain a certain amount of energy, based on what was collected.
	 * 
	 * @param amount The energy amount.
	 * @return The resulting amount of energy.
	 */
	public abstract int gainEnergy(int amount);

	/**
	 * Loose a certain amount of energy, based on the action performed.
	 * 
	 * @param amount The energy amount.
	 * @return The resulting amount of energy.
	 */
	public abstract int looseEnergy(int amount);
	
	/**
	 * Get the current amount of energy for this Collector.
	 * 
	 * @return The energy amount.
	 */
	public abstract int energy();
}
