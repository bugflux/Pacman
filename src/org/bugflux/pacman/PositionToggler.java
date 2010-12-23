package org.bugflux.pacman;

import org.bugflux.pacman.entities.MorphingWalkable;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.Walkable.PositionType;

public class PositionToggler implements Toggler {
	protected MorphingWalkable w;
	protected boolean over;

	public PositionToggler(MorphingWalkable w) {
		this.w = w;
		this.over = false;
	}
	
	@Override
	public PositionType toggle(Coord c) {
		return w.togglePositionType(this, c);
	}
	
	@Override
	public boolean canToggle(Coord c) {
		return w.canToggle(this, c);
	}
	
	public int height() {
		return w.height();
	}
	
	public int width() {
		return w.width();
	}
}
