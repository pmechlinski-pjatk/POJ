import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
		GameObject [] terrain = new GameObject[200];
		EnemyTank [] enemies = new EnemyTank[200];
		GameObject [] enemyBases = new GameObject[3];
		Missile [] missiles = new Missile[200];

		//TODO: [Math.round(size/33)]  <- generate enemies.
		Sprites [] s = new Sprites[8];

		addToSprites(0, s, "Wall", "<html><font color=#A52A2A>###<br/>###<br/>###</font></html>");
		addToSprites(1, s, "Player", "[+]");
		addToSprites(2, s, "EnemyBase", "<html>@=@<br/>|| F ||<br/>@=@</html>");
		addToSprites(3, s, "EnemyTank", "[x]");
		addToSprites(4, s, "MissileHorizontal", "<html>_|_<br/>[+]<br/></html>");
		addToSprites(5, s, "MissileVertical", "<html>_|_<br/>[+]<br/></html>");
		addToSprites(6, s, "WallRuined", "<html><font color=#A52A2A>%##<br/>##E<br/>_)##</font></html>");
		//s[] = new Sprites("Water", "<html><font color='blue'>~~~<br/>~~~<br/>~~~</font></html>"); // TODO: Water block? Need more tweaking due to being passable for missiles.
		System.out.println("(+) Sprites loaded!\n");

//		s[0] = new Sprites("Wall", "<html>###<br/>###<br/>###</html>");
//		s[1] = new Sprites("Player", "<html>_|_<br/>[+]<br/></html>");
//		s[2] = new Sprites("EnemyBase", "<html>@=@<br/>|| F ||<br/>@=@</html>");
//		s[3] = new Sprites("EnemyTank", "<html>_!_<br/>[X]<br/>###</html>");
//		s[4] = new Sprites("MissileHorizontal", "<html>_|_<br/>[+]<br/></html>");
//		s[5] = new Sprites("MissileVertical", "<html>_|_<br/>[+]<br/></html>");

		// Generate procedural map.
		System.out.println("(0) Initialize map generation...");
		mapGen(size, cells, terrain, enemyBases, enemies, s);
		System.out.println("(+)Logical map generated & also rendered!\n");



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


	public void mapGen(int size, Cell[][] cells, GameObject[] terrain, GameObject [] enemyBases, EnemyTank[] enemies, Sprites [] s) {
		int starterX, starterY;
		int baseX, baseY;

		// Generowanie losowej pozycji startowej gracza.
		do {
			starterX = ThreadLocalRandom.current().nextInt(size);
			starterY = ThreadLocalRandom.current().nextInt(size);
		} while ((starterX > 3 && starterX < size - 3) && (starterY > 3 && starterY < size - 3));
		// This requirement should ensure that the starting position of the player is close to the map's border.*
		// *It could crash on maps less than 6x6, but why'd someone use that small map?
		Player player = new Player("Player", getSpriteByName(s, "Player"), 3, true, cells[starterX][starterY]);
		cells[starterX][starterY].redraw();
		//System.out.println("Player's coords: "+Arrays.toString(player.linkedCell.getCoordinates()));
		//System.out.println("LinkedObject:" + cells[starterX][starterY].getLinkedObject().getImage());
		System.out.println("\t(+)Player created at: "+starterX+","+starterY);


		for (int i = 0; i < enemyBases.length; i++) {
			do { // Generowanie losowej pozycji bazy wroga w nie za małej odleglości
				baseX = ThreadLocalRandom.current().nextInt(size);
				baseY = ThreadLocalRandom.current().nextInt(size);
			} while (cells[baseX][baseY].getLinkedObject() != null || Math.abs(starterX - baseX) < 10 || Math.abs(starterY - baseY) < 10);
			enemyBases[i] = new GameObject("EnemyBase", getSpriteByName(s, "EnemyBase"), 3 , true, cells[baseX][baseY]);
			cells[baseX][baseY].redraw();
			System.out.println("\t(+) "+(i+1)+". Enemy base created at: "+baseX+","+baseY);
			baseX = baseY = 0;
		}

		for (int i = 0; i <= Math.round(size*4); i++)
		{
			int x,y;
			do
			{
				x = ThreadLocalRandom.current().nextInt(size);
				y = ThreadLocalRandom.current().nextInt(size);
			} while (cells[x][y].getLinkedObject() != null);
			terrain[i] = new GameObject("Terrain", getSpriteByName(s, "Wall"), 2, true, cells[x][y]);
			cells[x][y].redraw();
			x = y = 0;
		}
		System.out.println("\t(+) A few walls are build on your way.");

		for (int i = 0; i <= Math.round(size/3); i++)
		{
			int x,y;
			do
			{
				x = ThreadLocalRandom.current().nextInt(size);
				y = ThreadLocalRandom.current().nextInt(size);
			} while (cells[x][y].getLinkedObject() != null || Math.abs(starterX - x) < 5 || Math.abs(starterY - y) < 5);
			enemies[i] = new EnemyTank("EnemyTank", getSpriteByName(s, "EnemyTank"), 1, true, cells[x][y]);
			cells[x][y].redraw();
			x = y = 0;
		}
		System.out.println("\t(+) Some enemy tanks are approaching.");


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

	public String getSpriteByName(Sprites [] s, String name)
	{
		for (Sprites sprite : s)
		{
			if (sprite.getName() == name) return sprite.getImage();
		}
		return "NULL!";
	}

	public void addToSprites(int n, Sprites [] s, String name, String image)
	{
		Sprites sprite = new Sprites(name,image);
		s[n] = sprite;
		n++;
	}


	}

