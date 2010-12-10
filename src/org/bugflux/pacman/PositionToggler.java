package org.bugflux.pacman;

import org.bugflux.pacman.entities.MorphingWalkable;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.Walkable.PositionType;

public class PositionToggler implements Toggler {
	protected MorphingWalkable w;

	public PositionToggler(MorphingWalkable w) {
		this.w = w;
	}
	
	public PositionType tryToggle(Coord c) {	
		if(w.canToggle(c)) {
			return w.tryTogglePositionType(c);
		}

		return w.positionType(c);
	}
	
	public int height() {
		return w.height();
	}
	
	public int width() {
		return w.width();
	}
}
