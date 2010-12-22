

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.Map;
import org.bugflux.pacman.Pacman;
import org.bugflux.pacman.Phantom;
import org.bugflux.pacman.Phantom.Guy;
import org.bugflux.pacman.PositionToggler;
import org.bugflux.pacman.WalkerMover;
import org.bugflux.pacman.entities.Collector;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.Toggler;
import org.bugflux.pacman.entities.World;
import org.bugflux.pacman.input.AutomaticDoorman;
import org.bugflux.pacman.input.KeyboardMover;
import org.bugflux.pacman.input.MousePositionToggler;
import org.bugflux.pacman.input.RandomMover;
import org.bugflux.pacman.monitor.MonitorPositionToggler;
import org.bugflux.pacman.monitor.MonitorWalkable;
import org.bugflux.pacman.monitor.MonitorWalker;
import org.bugflux.pacman.monitor.MonitorWalkerMover;


public class MonitorDoIt {
	public static void main(String args[]) throws Throwable {
		if(args.length != 1) {
			System.out.println("usage: java -ea DoIt <map filename>");
			System.exit(-1);
		}

		Map _map = new Map(SequentialDoIt.readLabyrinth(args[0]));
		World map = new MonitorWalkable(_map);

		Collector _pacman = new Pacman(map);
		map.addWalker(_pacman, new Coord(1, 1));
		Controllable pacman = new MonitorWalker(_pacman);
		KeyboardMover controller = new KeyboardMover(new MonitorWalkerMover(new WalkerMover(pacman)));
		_map.getGBoard().addKeyListener(controller);
		
		Controllable _pinky = new Phantom(map, _map.getGBoard(), Guy.Pinky);
		map.addWalker(_pinky, new Coord(7, 10));
		Controllable pinky = new MonitorWalker(_pinky);
		RandomMover controller2 = new RandomMover(new MonitorWalkerMover(new WalkerMover(pinky)), 87);
		
		Controllable _blinky = new Phantom(map, _map.getGBoard(), Guy.Blinky);
		map.addWalker(_blinky, new Coord(7, 11));
		Controllable blinky = new MonitorWalker(_blinky);
		RandomMover controller3 = new RandomMover(new MonitorWalkerMover(new WalkerMover(blinky)), 175);
		
		Controllable _inky = new Phantom(map, _map.getGBoard(), Guy.Inky);
		map.addWalker(_inky, new Coord(7, 12));
		Controllable inky = new MonitorWalker(_inky);
		RandomMover controller4 = new RandomMover(new MonitorWalkerMover(new WalkerMover(inky)), 250);
		
		Controllable _clyde = new Phantom(map, _map.getGBoard(), Guy.Clyde);
		map.addWalker(_clyde, new Coord(7, 13));
		Controllable clyde = new MonitorWalker(_clyde);
		RandomMover controller5 = new RandomMover(new MonitorWalkerMover(new WalkerMover(clyde)), 500);
		
		controller2.start();
		controller3.start();
		controller4.start();
		controller5.start();
		
		Toggler _phantomDoor = new PositionToggler(map);
		map.addPositionToggler(_phantomDoor);
		AutomaticDoorman phantomDoor= new AutomaticDoorman(_phantomDoor, new Coord(6, 10), 3000, 2000);
		phantomDoor.start();

		Toggler _mouseToggler = new PositionToggler(map);
		map.addPositionToggler(_mouseToggler);
		MousePositionToggler toggler = new MousePositionToggler(new MonitorPositionToggler(_mouseToggler), _map.getGBoard());
		_map.getGBoard().addMouseListener(toggler);
	}
}
