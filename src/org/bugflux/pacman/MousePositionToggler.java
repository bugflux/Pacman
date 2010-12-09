package org.bugflux.pacman;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MousePositionToggler extends PositionToggler implements MouseListener {
	protected final Component c;
	
	public MousePositionToggler(MorphingWalkable w, Component c) {
		super(w);
		this.c = c;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mousePressed(MouseEvent e) {
		tryToggle(
				new Coord(
						w.height() * e.getY() / (int)(c.getHeight()),
						w.width() * e.getX() / (int)(c.getWidth())
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
