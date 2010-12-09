package org.bugflux.pacman;

import org.bugflux.pacman.Walkable.PositionType;

public class PositionToggler {
	protected MorphingWalkable w;

	public PositionToggler(MorphingWalkable w) {
		this.w = w;
	}
	
	public PositionType tryToggle(Coord c) {	
		if(w.canToggle(c)) {
			return w.togglePositionType(c);
		}

		return w.positionType(c);
	}
}
