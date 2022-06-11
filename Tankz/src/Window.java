import java.awt.GridLayout;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class Window {

	public Window(int size, int entities) { //In this version it will generate the random map very randomly xD
		JWindow window = new JWindow();
		JPanel panel = new JPanel(new GridLayout(size, size));
		Cell[][] cells = new Cell[size][size];
		filArray(size, cells);
		setNeibers(size, cells);
//		Arrays.setAll(cells, i -> new Cell());

		Arrays.stream(cells).flatMap(Arrays::stream).forEach(q -> panel.add(q));
		window.add(panel);
		window.setSize(size * 50, size * 50);
		window.setVisible(true);
	}







	private void setEntities(int entitiesNumber, int size, Cell[][] cells) {
		int entityType = 0; // Default type gives empty field.
		int starterX, starterY;
		int baseX, baseY;

		// Generowanie losowej pozycji startowej gracza.
		starterX = ThreadLocalRandom.current().nextInt(size);
		starterY = ThreadLocalRandom.current().nextInt(size);
		cells[starterX][starterY].setEntity(3); //

		do { // Generowanie losowej pozycji bazy wroga w nie za małej odleglości
			baseX = ThreadLocalRandom.current().nextInt(size);
			baseY = ThreadLocalRandom.current().nextInt(size);
		} while (cells[baseX][baseY].isEntity() && Math.abs(starterX-baseX) < 5 && Math.abs(starterY-baseY) < 5);
		cells[baseX][baseY].setEntity(2); //

		for (int i = 0; i < entitiesNumber; i++) {
			// 1#  4[x]
			int x, y, p;
			do { // Generowanie losowej pozycji bazy wroga w nie za małej odleglości
				x = ThreadLocalRandom.current().nextInt(size);
				y = ThreadLocalRandom.current().nextInt(size);
			} while (cells[x][y].isEntity());
			p = ThreadLocalRandom.current().nextInt(16);
			if (p > 14)	cells[x][y].setEntity(4);
			else cells[x][y].setEntity(1);
		}
	}

	/**
	 * @param size
	 * @param cells
	 */
	private void setNeibers(int size, Cell[][] cells) {
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
	private void filArray(int size, Cell[][] cells) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cells[i][j] = new Cell();
			}
		}
	}

}
