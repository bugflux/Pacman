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
	
	public PositionType tryToggle(Coord c) {
		if(!over) {
			return w.tryTogglePositionType(this, c);
		}
		else {
			return null;
		}
	}
	
	public int height() {
		return w.height();
	}
	
	public int width() {
		return w.width();
	}
	
	@Override
	public void gameOver() {
		over = true;
	}
}
