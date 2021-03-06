import com.sun.source.tree.WhileLoopTree;

import java.awt.*;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;


import static java.lang.System.exit;
import static java.lang.Thread.sleep;
import static java.util.Objects.isNull;

public class Window {

	static volatile boolean wPressed = false;
	static volatile boolean aPressed = false;
	static volatile boolean sPressed = false;
	static volatile boolean dPressed = false;
	static volatile boolean lPressed = false;

	public static boolean isWPressed() {
		synchronized (KeyListener.class) {
			return wPressed;
		}
	}

	public static boolean isAPressed() {
		synchronized (KeyListener.class) {
			return aPressed;
		}
	}

	public static boolean isSPressed() {
		synchronized (KeyListener.class) {
			return sPressed;
		}
	}

	public static boolean isDPressed() {
		synchronized (KeyListener.class) {
			return dPressed;
		}
	}

	public static boolean isLPressed() {
		synchronized (KeyListener.class) {
			return lPressed;
		}
	}

	public Window(int size) throws InterruptedException {

		LocalUtils u = new LocalUtils();
		// Implement Key Listener
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(KeyEvent ke) {
				synchronized (KeyListener.class) {
					switch (ke.getID()) {
						case KeyEvent.KEY_PRESSED:
							if (ke.getKeyCode() == KeyEvent.VK_W) {
								wPressed = true;
							} else if (ke.getKeyCode() == KeyEvent.VK_A) {
								aPressed = true;
							} else if (ke.getKeyCode() == KeyEvent.VK_S) {
								sPressed = true;
							} else if (ke.getKeyCode() == KeyEvent.VK_D) {
								dPressed = true;
							} else if (ke.getKeyCode() == KeyEvent.VK_L) {
								lPressed = true;
							}
							break;

						case KeyEvent.KEY_RELEASED:
							if (ke.getKeyCode() == KeyEvent.VK_W) {
								wPressed = false;
							} else if (ke.getKeyCode() == KeyEvent.VK_A) {
								aPressed = false;
							} else if (ke.getKeyCode() == KeyEvent.VK_S) {
								sPressed = false;
							} else if (ke.getKeyCode() == KeyEvent.VK_D) {
								dPressed = false;
							} else if (ke.getKeyCode() == KeyEvent.VK_L) {
								lPressed = false;
							}
							break;
					}
					return false;
				}
			}
		});

		// Create game window
		JFrame window = new JFrame("Tankz ASCII REMAKE");
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(size, size));


		// Create cell array object.
		Cell[][] cells = new Cell[size][size];
		//  Create cell for every map tile.
		u.filArray(size, cells);
		// Make every cell know its neighbours; TODO: This may need to be converted to the gameObject class probably.
		u.setNeibers(size, cells);

		// Those make tiles visible and of reasonable size for the ascii graphics to be seen
		Arrays.stream(cells).flatMap(Arrays::stream).forEach(panel::add);

		window.add(panel);
		window.setSize(size * 35, size * 50);
		window.setVisible(true);

		// Game init variables
		GameObject[] terrain = new GameObject[200];
		EnemyTank[] enemies = new EnemyTank[200];
		GameObject[] enemyBases = new GameObject[3];
		Player[] player = new Player[1];
		Missile[] missiles = new Missile[200];
		//		Initialize variables for handling missile threads
		Thread[] missileThread = new Thread[200];
		int missileThreadCounter = 0;
		int baseCounter = 3;


		// Init sprites
		Sprites[] s = new Sprites[8];

		u.addToSprites(0, s, "Wall", "<html><font color=#A52A2A>###<br/>###<br/>###</font></html>");
		u.addToSprites(1, s, "Player", "[+]");
		u.addToSprites(2, s, "EnemyBase", "<html><font color='gray'>@=@<br/>|| <font color='red'>F</font> ||<br/>@=@</font></html>");
		u.addToSprites(3, s, "EnemyTank", "[x]");
		u.addToSprites(4, s, "WallRuined", "<html><font color=#A52A2A>%##<br/>##E<br/>_)##</font></html>");
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
		u.mapGen(size, cells, terrain, enemyBases, enemies, s, player);
		System.out.println("(+)Logical map generated & also rendered!\n");

		// Loop to steer the tank

		Thread ControlLoop = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("(+) Control loop with keyboard controls is on.");
				while (true)
				{
					int cond;

					try {
						cond = player[0].tryAction(easyKeyDispatcher(), cells, size);
						if (cond == 1)
						{
							player[0].Action(easyKeyDispatcher(), cells, size);
						}
						else if (cond == 2) // Shooting action is expected - create a new thread
						{
							int i = getFreeIndex(missileThread);
							System.out.println("(0) New thread for shooting initialized at index "+i);
							newShootingThread(easyKeyDispatcher(), cells, size, missileThread, i, player[0]);
							u.redrawAll(cells, size);

						}
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					} try {
						sleep(1000);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
		});
		Thread EndgameCheckLoop = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("(+) Game loop with endgame check on.");
				while (true) {
					try {
						sleep(100);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					u.redrawAll(cells, size);
					//System.out.println("(0) Test for endgame");
					try {
						testForEndgame(enemyBases, player[0]);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
		});
		ControlLoop.start();
		EndgameCheckLoop.run();





		// There should be the main game loops with:
		// Timing
		// Refreshing tiles (graphics & movables coordinates)
		// Test for win/lose requirements
		// (Lose - player's HP is zero)
		// (Win - there are no more enemy bases)


	}
private int getFreeIndex (Object [] array)
{
	for(int i = 0; i < array.length; i++)
		if(array[i] == null)
		{
			return i;
		}
	return -1;
}



	public void testForEndgame(GameObject [] enemyBases, Player player) throws InterruptedException {
		{
			for (GameObject base : enemyBases) {
				if (!isNull(base) && base.isObject()) break;
				System.out.println("Wygra??e??!");
				sleep(500);
				exit(0);
			}
		}
		if (isNull(player))
		{
			System.out.println("Przegra??e??!");
			exit(0);
		}

	}

	public char easyKeyDispatcher() {
		if (isWPressed()) {
			//System.out.println("W!");
			return 'w';
		}
		if (isAPressed()) return 'a';
		if (isSPressed()) return 's';
		if (isDPressed()) return 'd';
		if (isLPressed()) return 'l';
		else return ' ';
	}



	public void newShootingThread(char k, Cell[][] cells, int size, Thread [] t, int tCount, Player p) throws InterruptedException {
	t[tCount] = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				p.Action(k, cells, size);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	});
	t[tCount].start();
	//sleep(10000);
	//t[tCount].interrupt();
	//System.out.println("Shooting thread at index " +tCount+" freed." );
	}
}
//public void redrawAll(Cell cells[][], int size)
//{
//	for (int i = 0; i < size; i++)
//	{
//		for (int j = 0; j < size; j++)
//		{
//			cells[i][j].redraw();
//		}
//	}
//}
//
//	public void mapGen(int size, Cell[][] cells, GameObject[] terrain, GameObject [] enemyBases, EnemyTank[] enemies, Sprites [] s, Player [] player) {
//		int starterX, starterY;
//		int baseX, baseY;
//
//		// Generowanie losowej pozycji startowej gracza.
//		do {
//			starterX = ThreadLocalRandom.current().nextInt(size);
//			starterY = ThreadLocalRandom.current().nextInt(size);
//		} while ((starterX > 3 && starterX < size - 3) && (starterY > 3 && starterY < size - 3));
//		// This requirement should ensure that the starting position of the player is close to the map's border.*
//		// *It could crash on maps less than 6x6, but why'd someone use that small map?
//		player[0] = new Player("Player", getSpriteByName(s, "Player"), 3, true, cells[starterX][starterY]);
//		cells[starterX][starterY].redraw();
//		//System.out.println("Player's coords: "+Arrays.toString(player.linkedCell.getCoordinates()));
//		//System.out.println("LinkedObject:" + cells[starterX][starterY].getLinkedObject().getImage());
//		System.out.println("\t(+)Player created at: "+starterX+","+starterY);
//		//System.out.println("Player's neibers are:\n"+(Arrays.deepToString(player.getNeibers())));
//
//
//		for (int i = 0; i < enemyBases.length; i++) {
//			do { // Generowanie losowej pozycji bazy wroga w nie za ma??ej odleglo??ci
//				baseX = ThreadLocalRandom.current().nextInt(size);
//				baseY = ThreadLocalRandom.current().nextInt(size);
//			} while (cells[baseX][baseY].getLinkedObject() != null || Math.abs(starterX - baseX) < 10 || Math.abs(starterY - baseY) < 10);
//			enemyBases[i] = new GameObject("EnemyBase", getSpriteByName(s, "EnemyBase"), 3 , true, cells[baseX][baseY]);
//			cells[baseX][baseY].redraw();
//			System.out.println("\t(+) "+(i+1)+". Enemy base created at: "+baseX+","+baseY);
//			baseX = baseY = 0;
//		}
//
//		for (int i = 0; i <= Math.round(size*4); i++)
//		{
//			int x,y;
//			do
//			{
//				x = ThreadLocalRandom.current().nextInt(size);
//				y = ThreadLocalRandom.current().nextInt(size);
//			} while (cells[x][y].getLinkedObject() != null);
//			terrain[i] = new GameObject("Terrain", getSpriteByName(s, "Wall"), 2, true, cells[x][y]);
//			cells[x][y].redraw();
//			x = y = 0;
//		}
//		System.out.println("\t(+) A few walls are build on your way.");
//
//		for (int i = 0; i <= Math.round(size/3); i++)
//		{
//			int x,y;
//			do
//			{
//				x = ThreadLocalRandom.current().nextInt(size);
//				y = ThreadLocalRandom.current().nextInt(size);
//			} while (cells[x][y].getLinkedObject() != null || Math.abs(starterX - x) < 5 || Math.abs(starterY - y) < 5);
//			enemies[i] = new EnemyTank("EnemyTank", getSpriteByName(s, "EnemyTank"), 1, true, cells[x][y]);
//			//System.out.println("Tank neibers are:\n"+(Arrays.deepToString(enemies[i].getNeibers())));
//			cells[x][y].redraw();
//			x = y = 0;
//		}
//		System.out.println("\t(+) Some enemy tanks are approaching.");
//
//
//	}
//		// For more enemy bases there could be probably an array of them...
//
////		for (int i = 0; i < 20; i++) {
////			// 1#  4[x]
////			int x, y, p;
////			do { // Generowanie losowej pozycji bazy wroga w nie za ma??ej odleglo??ci
////				x = ThreadLocalRandom.current().nextInt(size);
////				y = ThreadLocalRandom.current().nextInt(size);
////			} while (cells[x][y].isEntity());
////			p = ThreadLocalRandom.current().nextInt(16);
////			if (p > 14)	cells[x][y].setEntity(4);
////			else cells[x][y].setEntity(1);
////		}
//
////		for (int i = 0; i < 40; i++) {  // Generate more walls to make the game more interesting
////			// 1#
////			int x, y, p;
////			do { // Generowanie losowej pozycji bazy wroga w nie za ma??ej odleglo??ci
////				x = ThreadLocalRandom.current().nextInt(size);
////				y = ThreadLocalRandom.current().nextInt(size);
////			} while (cells[x][y].isEntity());
////			p = ThreadLocalRandom.current().nextInt(16);
////			cells[x][y].setEntity(1);
////		}
////	}
//
//		/**
//		 * @param size
//		 * @param cells
//		 */
//		private void setNeibers ( int size, Cell[][] cells){
//			for (int i = 0; i < size; i++) {
//				for (int j = 0; j < size; j++) {
//
//					for (int p = 0; p <= 2; p++) {
//						for (int q = 0; q <= 2; q++) {
//							int x = i + p - 1, y = j + q - 1;
//							if (x >= 0 && x < size && y >= 0 && y < size)
//								cells[i][j].setNeiber(cells[x][y], p, q);
//
//						}
//					}
//				}
//			}
//		}
//
//		/**
//		 * @param size
//		 * @param cells
//		 */
//		private void filArray ( int size, Cell[][] cells){
//			for (int i = 0; i < size; i++) {
//				for (int j = 0; j < size; j++) {
//					//System.out.print("New cell with x = "+i+" & y = "+j+"\n");  //DEBUG
//					cells[i][j] = new Cell(i, j);
//				}
//			}
//		}
//
//	public String getSpriteByName(Sprites [] s, String name)
//	{
//		for (Sprites sprite : s)
//		{
//			if (sprite.getName() == name) return sprite.getImage();
//		}
//		return "NULL!";
//	}
//
//	public void addToSprites(int n, Sprites [] s, String name, String image)
//	{
//		Sprites sprite = new Sprites(name,image);
//		s[n] = sprite;
//		n++;
//	}
//
//
//	}
//
