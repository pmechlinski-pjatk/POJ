import java.awt.*;
import java.net.StandardSocketOptions;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

public class Window {



	public Window(int size) {
		// Create game window
		JFrame window = new JFrame("Tankz ASCII REMAKE");
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(size, size));

		// Create cell array object.
		Cell[][] cells = new Cell[size][size];
		//  Create cell for every map tile.
		filArray(size, cells);
		// Make every cell know its neighbours; TODO: This may need to be converted to the gameObject class probably.
		setNeibers(size, cells);

		// Those make tiles visible and of reasonable size for the ascii graphics to be seen
		Arrays.stream(cells).flatMap(Arrays::stream).forEach(panel::add);

		window.add(panel);
		window.setSize(size * 35, size * 50);
		window.setVisible(true);

		// Game init variables
		GameObject [] terrain;
		EnemyTank [] enemies;
		GameObject [] enemyBases;
		Missile [] missiles;
		Player player;

		// Generate procedural map.


		//TODO: [Math.round(size/33)]  <- generate enemies.
		Sprites [] s = new Sprites[9];


		s[0] = new Sprites("Wall", "<html>###<br/>###<br/>###</html>");
		//s[1] = new Sprites("Player", "<html>_|_<br/>[+]<br/></html>");

		//Player player = new Player(s[0].getImage(), 3, true, cells[2][5]);
		//mapGen(size, cells);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cells[i][j].redraw();
			}
		}
		//Player player = new Player("XD", 3, true, cells[1][1]);
		//Player player2 = new Player("%%", 3, true, cells[9][9]);
		//cells[1][1].setLinkedObject(player);
		//cells[1][1].redraw();
		//cells[9][9].redraw();


		// There should be the main game loops with:
		// Timing
		// Refreshing tiles (graphics & movables coordinates)
		// Test for win/lose requirements
		// (Lose - player's HP is zero)
		// (Win - there are no more enemy bases)


	}


	public void mapGen(int size, Cell[][] cells, GameObject[] terrain, GameObject enemyBases, EnemyTank[] enemies, Player player)
	{
		int starterX, starterY;
		int baseX, baseY;

		// Generowanie losowej pozycji startowej gracza.
		do {
			starterX = ThreadLocalRandom.current().nextInt(size);
			starterY = ThreadLocalRandom.current().nextInt(size);
		} while ((starterX > 3 && starterX < size - 3) && (starterY > 3 && starterY < size - 3));
		// This requirement should ensure that the starting position of the player is close to the map's border.*
		// *It could crash on maps less than 6x6, but why'd someone use that small map?
		player.setLinkedCell(cells[starterX][starterY]);

		do { // Generowanie losowej pozycji bazy wroga w nie za małej odleglości
			baseX = ThreadLocalRandom.current().nextInt(size);
			baseY = ThreadLocalRandom.current().nextInt(size);
		} while (cells[baseX][baseY].getLinkedObject() == null && Math.abs(starterX - baseX) < 20 && Math.abs(starterY - baseY) < 20);
	}
		// For more enemy bases there could be probably an array of them...

//		for (int i = 0; i < 20; i++) {
//			// 1#  4[x]
//			int x, y, p;
//			do { // Generowanie losowej pozycji bazy wroga w nie za małej odleglości
//				x = ThreadLocalRandom.current().nextInt(size);
//				y = ThreadLocalRandom.current().nextInt(size);
//			} while (cells[x][y].isEntity());
//			p = ThreadLocalRandom.current().nextInt(16);
//			if (p > 14)	cells[x][y].setEntity(4);
//			else cells[x][y].setEntity(1);
//		}

//		for (int i = 0; i < 40; i++) {  // Generate more walls to make the game more interesting
//			// 1#
//			int x, y, p;
//			do { // Generowanie losowej pozycji bazy wroga w nie za małej odleglości
//				x = ThreadLocalRandom.current().nextInt(size);
//				y = ThreadLocalRandom.current().nextInt(size);
//			} while (cells[x][y].isEntity());
//			p = ThreadLocalRandom.current().nextInt(16);
//			cells[x][y].setEntity(1);
//		}
//	}

		/**
		 * @param size
		 * @param cells
		 */
		private void setNeibers ( int size, Cell[][] cells){
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {

					for (int p = 0; p <= 2; p++) {
						for (int q = 0; q <= 2; q++) {
							int x = i + p - 1, y = j + q - 1;
							if (x >= 0 && x < size && y >= 0 && y < size)
								cells[i][j].setNeiber(cells[x][y], p, q);

						}
					}
				}
			}
		}

		/**
		 * @param size
		 * @param cells
		 */
		private void filArray ( int size, Cell[][] cells){
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					//System.out.print("New cell with x = "+i+" & y = "+j+"\n");  //DEBUG
					cells[i][j] = new Cell(i, j);
				}
			}
		}



	}

