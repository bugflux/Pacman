

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.bugflux.lock.Metronome;
import org.bugflux.pacman.Coord;
import org.bugflux.pacman.Map;
import org.bugflux.pacman.Pacman;
import org.bugflux.pacman.concurrent.KeyboardMoveEvent;
import org.bugflux.pacman.concurrent.SharedMap;
import org.bugflux.pacman.concurrent.SharedPacman;
import org.bugflux.pacman.concurrent.SharedTickMap;
import org.bugflux.pacman.concurrent.WalkerController;


public class ConcurrentlyDoIt {
	public static void main(String args[]) throws Throwable {
		if(args.length != 1) {
			System.out.println("usage: java -ea DoIt <map filename>");
			System.exit(-1);
		}

		Map m = new Map(readLabyrinth(args[0]));
		Metronome tick = new Metronome(200);
		tick.start();
		SharedMap sm = new SharedTickMap(m, tick);

		Pacman p = new SharedPacman(sm, new Coord(1, 1));
		KeyboardMoveEvent kme = new KeyboardMoveEvent();
		m.getGBoard().addKeyListener(kme);
		WalkerController controller = new WalkerController(p, kme);
		
		controller.start();
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
