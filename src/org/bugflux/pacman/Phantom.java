package org.bugflux.pacman;

import org.bugflux.pacman.entities.Walkable;

import pt.ua.gboard.GBoard;
import pt.ua.gboard.ImageGelem;

public class Phantom extends Walker {
	public Phantom(Walkable w, GBoard g, Guy d) {
		super(w, new PhantomGelem(d.imagePath(), g), Team.VILLAIN); // TODO warning
	}
	
	public static enum Guy {
		Pinky("res/Pinky.png"),
		Blinky("res/Blinky.png"),
		Inky("res/Inky.png"),
		Clyde("res/Clyde.png");
		
		private String imagePath;
		Guy(String s) {
			imagePath = s;
		}
		
		public String imagePath() {
			return imagePath;
		}
	};
}

class PhantomGelem extends ImageGelem {
	public PhantomGelem(String imagePath, GBoard gboard) {
		super(imagePath, gboard, 75.0);
	}
}