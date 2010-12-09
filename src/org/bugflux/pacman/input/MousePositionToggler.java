package org.bugflux.pacman.input;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.PositionToggler;

public class MousePositionToggler implements MouseListener {
	protected final PositionToggler t;
	protected final Component c;
	
	public MousePositionToggler(PositionToggler t, Component c) {
		this.t = t;
		this.c = c;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mousePressed(MouseEvent e) {
		t.tryToggle(
				new Coord(
						t.height() * e.getY() / (int)(c.getHeight()),
						t.width() * e.getX() / (int)(c.getWidth())
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
