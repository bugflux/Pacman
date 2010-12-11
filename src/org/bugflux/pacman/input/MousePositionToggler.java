package org.bugflux.pacman.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.entities.Toggler;

import pt.ua.gboard.GBoard;

public class MousePositionToggler implements MouseListener {
	protected final Toggler t;
	protected final GBoard g;
	
	public MousePositionToggler(Toggler t, GBoard g) {
		this.t = t;
		this.g = g;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mousePressed(MouseEvent e) {
		t.tryToggle(
				new Coord(
						g.numberOfLines() * e.getY() / (int)(g.getHeight()),
						g.numberOfColumns() * e.getX() / (int)(g.getWidth())
						)
				);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// do nothing
	}

}
