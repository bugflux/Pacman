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
	
	public GenericBonus(Gelem gelem, Property p) {
		assert gelem != null;
		assert p != null;

		this.gelem = gelem;
		this.p = p;
	}

	@Override
	public Property property() {
		return p;
	}

	@Override
	public Gelem gelem() {
		return gelem;
	}
}
