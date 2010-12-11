import org.bugflux.lock.Metronome;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.Map;
import org.bugflux.pacman.Pacman;
import org.bugflux.pacman.WalkerMover;
import org.bugflux.pacman.entities.Controllable;
import org.bugflux.pacman.entities.MorphingWalkable;
import org.bugflux.pacman.event.EventWalkerMover;
import org.bugflux.pacman.event.TickMap;
import org.bugflux.pacman.input.RandomMover;
import org.bugflux.pacman.input.KeyboardMover;


public class EventDoIt {
	public static void main(String[] args) throws Exception {
		if(args.length != 1) {
			System.out.println("usage: java -ea DoIt <map filename>");
			System.exit(-1);
		}

		Map _map = new Map(SequentialDoIt.readLabyrinth(args[0]));
		Metronome metronome = new Metronome(100);
		metronome.start();
		MorphingWalkable map = new TickMap(_map, metronome);

		Controllable pacman1 = new Pacman(map, new Coord(1, 1));
		EventWalkerMover mover1 = new EventWalkerMover(new WalkerMover(pacman1));
		mover1.start();
		KeyboardMover controller1 = new KeyboardMover(mover1);
		_map.getGBoard().addKeyListener(controller1);
		
		Controllable pacman2 = new Pacman(map, new Coord(1, 2));
		EventWalkerMover mover2 = new EventWalkerMover(new WalkerMover(pacman2));
		mover2.start();
		RandomMover controller2 = new RandomMover(mover2, 200);
		controller2.start();

//		MousePositionToggler toggler = new MousePositionToggler(new EventPositionToggler(map), _map.getGBoard());
//		_map.getGBoard().addMouseListener(toggler);
	}
}
