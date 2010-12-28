package org.bugflux.pacman;

import org.bugflux.pacman.entities.Bonus;

import pt.ua.gboard.Gelem;

/**
 * Never expires, once activated!
 * 
 * @author ndray
 *
 */
public class GenericBonus implements Bonus {
	protected final Property p;
	protected final Gelem gelem;
	protected final int energyCost;
	
	public GenericBonus(Gelem gelem, Property p, int energyCost) {
		assert gelem != null;
		assert p != null;
		assert energyCost >= 0;

		this.gelem = gelem;
		this.p = p;
		this.energyCost = energyCost;
	}

	@Override
	public Property property() {
		return p;
	}

	@Override
	public Gelem gelem() {
		return gelem;
	}

	@Override
	public int energyCost() {
		return energyCost;
	}
}
