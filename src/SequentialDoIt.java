

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.bugflux.pacman.Coord;
import org.bugflux.pacman.Map;
import org.bugflux.pacman.Pacman;
import org.bugflux.pacman.Phantom;
import org.bugflux.pacman.Phantom.Guy;
import org.bugflux.pacman.PositionToggler;
import org.bugflux.pacman.WalkerMover;
import org.bugflux.pacman.entities.Collector;
import org.bugflux.pacman.input.KeyboardMover;
import org.bugflux.pacman.input.MousePositionToggler;


public class SequentialDoIt {
	public static void main(String args[]) throws Throwable {
		if(args.length != 1) {
			System.out.println("usage: java -ea DoIt <map filename>");
			System.exit(-1);
		}

		Map m = new Map(readLabyrinth(args[0]));

		Collector p = new Pacman(m, new Coord(1, 1));
		KeyboardMover pacmanController = new KeyboardMover(new WalkerMover(p));
		m.getGBoard().addKeyListener(pacmanController);
		
		/*Pinky pinky = */new Phantom(m, new Coord(7, 12), m.getGBoard(), Guy.Pinky);

		MousePositionToggler mpt = new MousePositionToggler(new PositionToggler(m), m.getGBoard());
		m.getGBoard().addMouseListener(mpt);
	}

	public static char[][] readLabyrinth(String filename) throws Exception {
		FileInputStream fstream = new FileInputStream(filename);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		int height = 0;
		int maxWidth = 0;

		ArrayList<String> a = new ArrayList<String>();
		String line;
		while((line = br.readLine()) != null) {
			if(maxWidth < line.length()) {
				maxWidth = line.length();
			}

			a.add(line);
			
			height++;
		}

		char map[][] = new char[height][maxWidth];
		for(int r = 0; r < map.length; r++) {

			line = a.get(r);
			for(int c = 0; c < line.length(); c++) {
				switch(line.charAt(c)) {
					case ' ': // nothing
						break;
					case 'S': // copy
						// break;
					case 'X': // copy
						// break;
					default: // wall, copy
						map[r][c] = line.charAt(c);
				}
			}
		}

		in.close();

		return map;
	}
}
