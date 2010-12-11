

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.Map;
import org.bugflux.pacman.Pacman;
import org.bugflux.pacman.Phantom;
import org.bugflux.pacman.Phantom.Guy;
import org.bugflux.pacman.WalkerMover;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.MorphingWalkable;
import org.bugflux.pacman.input.KeyboardMover;
import org.bugflux.pacman.input.MousePositionToggler;
import org.bugflux.pacman.input.RandomMover;
import org.bugflux.pacman.monitor.MonitorMap;
import org.bugflux.pacman.monitor.MonitorPositionToggler;
import org.bugflux.pacman.monitor.MonitorWalker;
import org.bugflux.pacman.monitor.MonitorWalkerMover;


public class MonitorDoIt {
	public static void main(String args[]) throws Throwable {
		if(args.length != 1) {
			System.out.println("usage: java -ea DoIt <map filename>");
			System.exit(-1);
		}

		Map _map = new Map(SequentialDoIt.readLabyrinth(args[0]));
		MorphingWalkable map = new MonitorMap(_map);

		Controllable pacman = new MonitorWalker(new Pacman(map, new Coord(1, 1)));
		KeyboardMover controller = new KeyboardMover(new MonitorWalkerMover(new WalkerMover(pacman)));
		_map.getGBoard().addKeyListener(controller);
		
		Controllable pinky = new MonitorWalker(new Phantom(map, new Coord(7, 10), _map.getGBoard(), Guy.Pinky));
		RandomMover controller2 = new RandomMover(new MonitorWalkerMover(new WalkerMover(pinky)), 87);
		
		Controllable blinky = new MonitorWalker(new Phantom(map, new Coord(7, 11), _map.getGBoard(), Guy.Blinky));
		RandomMover controller3 = new RandomMover(new MonitorWalkerMover(new WalkerMover(blinky)), 175);
		
		Controllable inky = new MonitorWalker(new Phantom(map, new Coord(7, 12), _map.getGBoard(), Guy.Inky));
		RandomMover controller4 = new RandomMover(new MonitorWalkerMover(new WalkerMover(inky)), 250);
		
		Controllable clyde = new MonitorWalker(new Phantom(map, new Coord(7, 13), _map.getGBoard(), Guy.Clyde));
		RandomMover controller5 = new RandomMover(new MonitorWalkerMover(new WalkerMover(clyde)), 500);
		
		controller2.start();
		controller3.start();
		controller4.start();
		controller5.start();

		MousePositionToggler toggler = new MousePositionToggler(new MonitorPositionToggler(map), _map.getGBoard());
		_map.getGBoard().addMouseListener(toggler);
	}
}
