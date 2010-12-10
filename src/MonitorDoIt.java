

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.Map;
import org.bugflux.pacman.Pacman;
import org.bugflux.pacman.WalkerMover;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.MorphingWalkable;
import org.bugflux.pacman.input.AutonomousMover;
import org.bugflux.pacman.input.KeyboardMover;
import org.bugflux.pacman.input.MousePositionToggler;
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
		
		Controllable pacman2 = new MonitorWalker(new Pacman(map, new Coord(1, 2)));
		AutonomousMover controller2 = new AutonomousMover(new MonitorWalkerMover(new WalkerMover(pacman2)));
		controller2.start();

		MousePositionToggler toggler = new MousePositionToggler(new MonitorPositionToggler(map), _map.getGBoard());
		_map.getGBoard().addMouseListener(toggler);
	}
}
