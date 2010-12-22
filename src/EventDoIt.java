import org.bugflux.lock.Metronome;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.Map;
import org.bugflux.pacman.Pacman;
import org.bugflux.pacman.Phantom;
import org.bugflux.pacman.Phantom.Guy;
import org.bugflux.pacman.PositionToggler;
import org.bugflux.pacman.WalkerMover;
import org.bugflux.pacman.entities.Collector;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.World;
import org.bugflux.pacman.event.EventPositionToggler;
import org.bugflux.pacman.event.EventWalkerMover;
import org.bugflux.pacman.event.TickMap;
import org.bugflux.pacman.input.KeyboardMover;
import org.bugflux.pacman.input.MousePositionToggler;
import org.bugflux.pacman.input.RandomMover;
import org.bugflux.pacman.monitor.MonitorWalkable;


public class EventDoIt {
	public static void main(String[] args) throws Exception {
		if(args.length != 1) {
			System.out.println("usage: java -ea DoIt <map filename>");
			System.exit(-1);
		}

		Map _map = new Map(SequentialDoIt.readLabyrinth(args[0]));
		World __map = new MonitorWalkable(_map);
		Metronome metronome = new Metronome(100);
		metronome.start();
		TickMap map = new TickMap(__map, metronome);

		Collector pacman1 = new Pacman(map);
		map.addWalker(pacman1, new Coord(1, 1));
		EventWalkerMover mover1 = new EventWalkerMover(new WalkerMover(pacman1));
		KeyboardMover controller1 = new KeyboardMover(map, mover1);
		_map.getGBoard().addKeyListener(controller1);

		Controllable pinky = new Phantom(map, _map.getGBoard(), Guy.Pinky);
		map.addWalker(pinky, new Coord(7, 10));
		EventWalkerMover mover2 = new EventWalkerMover(new WalkerMover(pinky));
		RandomMover controller2 = new RandomMover(map, mover2, 100);
//		
//		Controllable blinky = new Phantom(map, _map.getGBoard(), Guy.Blinky);
//		map.addWalker(blinky, new Coord(7, 11));
//		EventWalkerMover mover3 = new EventWalkerMover(new WalkerMover(blinky));
//		RandomMover controller3 = new RandomMover(map, mover3, 0);
//		
//		Controllable inky = new Phantom(map, _map.getGBoard(), Guy.Inky);
//		map.addWalker(inky, new Coord(7, 12));
//		EventWalkerMover mover4 = new EventWalkerMover(new WalkerMover(inky));
//		RandomMover controller4 = new RandomMover(map, mover4, 250);
//		
//		Controllable clyde = new Phantom(map, _map.getGBoard(), Guy.Clyde);
//		map.addWalker(clyde, new Coord(7, 13));
//		EventWalkerMover mover5 = new EventWalkerMover(new WalkerMover(clyde));
//		RandomMover controller5 = new RandomMover(map, mover4, 500);
//		
		controller2.start();
//		controller3.start();
//		controller4.start();
//		controller5.start();
//
//		mover3.start();
//		mover4.start();
//		mover5.start();

		EventPositionToggler positionToggler = new EventPositionToggler(new PositionToggler(map));
		positionToggler.start();
		MousePositionToggler toggler = new MousePositionToggler(map, positionToggler, _map.getGBoard());
		_map.getGBoard().addMouseListener(toggler);
	}
}
