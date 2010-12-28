

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.Map;
import org.bugflux.pacman.Pacman;
import org.bugflux.pacman.Phantom;
import org.bugflux.pacman.Phantom.Guy;
import org.bugflux.pacman.PositionToggler;
import org.bugflux.pacman.Scoreboard;
import org.bugflux.pacman.entities.Collector;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Scorekeeper;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.input.AutomaticBonusPlacer;
import org.bugflux.pacman.input.AutomaticDoorman;
import org.bugflux.pacman.input.KeyboardMover;
import org.bugflux.pacman.input.MousePositionToggler;
import org.bugflux.pacman.input.RandomMover;
import org.bugflux.pacman.shared.SharedMap;
import org.bugflux.pacman.shared.monitor.MonitorMap;
import org.bugflux.pacman.shared.monitor.MonitorPositionToggler;
import org.bugflux.pacman.shared.monitor.MonitorScoreboard;
import org.bugflux.pacman.shared.monitor.MonitorWalker;


public class MonitorDoIt {
	public static void main(String args[]) throws Throwable {
		if(args.length != 1) {
			System.out.println("usage: java -ea DoIt <map filename>");
			System.exit(-1);
		}

		// create
		Scorekeeper score = new MonitorScoreboard(new Scoreboard(6));
		Map _map = new Map(SequentialDoIt.readLabyrinth(args[0]), score);
		SharedMap map = new MonitorMap(_map);

//		Collector _pacman_ = new Pacman(map);
//		map.addWalker(_pacman_, new Coord(1, 2));

		Collector _pacman = new Pacman(map);
		map.addWalker(_pacman, new Coord(1, 1));
		Controllable pacman = new MonitorWalker(_pacman);
		KeyboardMover controller = new KeyboardMover(map, pacman);
		_map.getGBoard().addKeyListener(controller);

		Coord coords[] = { new Coord(7, 10), new Coord(7, 11), new Coord(7, 12), new Coord(7, 13) };
		Guy guys[] = { Guy.Pinky, Guy.Blinky, Guy.Inky, Guy.Clyde };
		Controllable phantoms[] = new Controllable[coords.length];
		RandomMover movers[] = new RandomMover[coords.length];
		int speeds[] = { 87, 175, 250, 500 };
		
		for(int r = 0; r < coords.length; r++) {
			phantoms[r] = new Phantom(map, _map.getGBoard(), guys[r]);
			map.addWalker(phantoms[r], coords[r]);
			phantoms[r] = new MonitorWalker(phantoms[r]);
			movers[r] = new RandomMover(map, phantoms[r], speeds[r]);
		}
		
		Toggler _phantomDoor = new PositionToggler(map);
		map.addPositionToggler(_phantomDoor);
		AutomaticDoorman phantomDoor= new AutomaticDoorman(map, _phantomDoor, new Coord(6, 10), 3000, 2000);

		Toggler _mouseToggler = new PositionToggler(map);
		map.addPositionToggler(_mouseToggler);
		MousePositionToggler toggler = new MousePositionToggler(map, new MonitorPositionToggler(_mouseToggler), _map.getGBoard());

		int waitPlace[] = { 5000, 1000, 1500 };
		int waitActive[] = { 10000, 7000, 7500 };
		AutomaticBonusPlacer placers[] = new AutomaticBonusPlacer[waitPlace.length];
		
		for(int r = 0; r < waitPlace.length; r++) {
			placers[r] = new AutomaticBonusPlacer(map, waitPlace[r], waitActive[r], score);
		}
		
		// launch
		_map.getGBoard().addMouseListener(toggler);
		phantomDoor.start();
		for(int r = 0; r < coords.length; r++) {
			movers[r].start();
		}
		for(int r = 0; r < waitPlace.length; r++) {
			placers[r].start();
		}

		// join
		phantomDoor.join();
		for(int r = 0; r < waitPlace.length; r++) {
			placers[r].join();
		}
		for(int r = 0; r < coords.length; r++) {
			movers[r].join();
		}
		_map.getGBoard().removeMouseListener(toggler);

		System.out.println("All is ok");
	}
}
