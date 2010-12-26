

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
import org.bugflux.pacman.entities.World;
import org.bugflux.pacman.input.AutomaticBonusPlacer;
import org.bugflux.pacman.input.AutomaticDoorman;
import org.bugflux.pacman.input.KeyboardMover;
import org.bugflux.pacman.input.MousePositionToggler;
import org.bugflux.pacman.input.RandomMover;
import org.bugflux.pacman.monitor.MonitorMap;
import org.bugflux.pacman.monitor.MonitorPositionToggler;
import org.bugflux.pacman.monitor.MonitorScoreboard;
import org.bugflux.pacman.monitor.MonitorWalker;


public class MonitorDoIt {
	public static void main(String args[]) throws Throwable {
		if(args.length != 1) {
			System.out.println("usage: java -ea DoIt <map filename>");
			System.exit(-1);
		}

		Scorekeeper score = new MonitorScoreboard(new Scoreboard(5));
		Map _map = new Map(SequentialDoIt.readLabyrinth(args[0]), score);
		World map = new MonitorMap(_map);

		Collector _pacman = new Pacman(map);
		map.addWalker(_pacman, new Coord(1, 1));
		Controllable pacman = new MonitorWalker(_pacman);
		KeyboardMover controller = new KeyboardMover(map, pacman);
		_map.getGBoard().addKeyListener(controller);
		
		Controllable _pinky = new Phantom(map, _map.getGBoard(), Guy.Pinky);
		map.addWalker(_pinky, new Coord(7, 10));
		Controllable pinky = new MonitorWalker(_pinky);
		RandomMover controller2 = new RandomMover(map, pinky, 87);
		
		Controllable _blinky = new Phantom(map, _map.getGBoard(), Guy.Blinky);
		map.addWalker(_blinky, new Coord(7, 11));
		Controllable blinky = new MonitorWalker(_blinky);
		RandomMover controller3 = new RandomMover(map, blinky, 175);
		
		Controllable _inky = new Phantom(map, _map.getGBoard(), Guy.Inky);
		map.addWalker(_inky, new Coord(7, 12));
		Controllable inky = new MonitorWalker(_inky);
		RandomMover controller4 = new RandomMover(map, inky, 250);
		
		Controllable _clyde = new Phantom(map, _map.getGBoard(), Guy.Clyde);
		map.addWalker(_clyde, new Coord(7, 13));
		Controllable clyde = new MonitorWalker(_clyde);
		RandomMover controller5 = new RandomMover(map, clyde, 500);
		
		controller2.start();
		controller3.start();
		controller4.start();
		controller5.start();
		
		Toggler _phantomDoor = new PositionToggler(map);
		map.addPositionToggler(_phantomDoor);
		AutomaticDoorman phantomDoor= new AutomaticDoorman(map, _phantomDoor, new Coord(6, 10), 3000, 2000);
//		phantomDoor.start();

		Toggler _mouseToggler = new PositionToggler(map);
		map.addPositionToggler(_mouseToggler);
		MousePositionToggler toggler = new MousePositionToggler(map, new MonitorPositionToggler(_mouseToggler), _map.getGBoard());
		_map.getGBoard().addMouseListener(toggler);
		
		AutomaticBonusPlacer abp1 = new AutomaticBonusPlacer(map, 5000, 10000, score);
		abp1.start();
		
		AutomaticBonusPlacer abp2 = new AutomaticBonusPlacer(map, 1000, 5000, score);
		abp2.start();
		
		AutomaticBonusPlacer abp3 = new AutomaticBonusPlacer(map, 1500, 5000, score);
		abp3.start();
		
		controller2.join();
		controller3.join();
		controller4.join();
		controller5.join();
		phantomDoor.join();

		System.out.println("All is ok");
	}
}
