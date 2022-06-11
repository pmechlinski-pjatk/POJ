import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class Window {

	public Window(int size, int mines) {
		JWindow window = new JWindow();
		JPanel panel = new JPanel(new GridLayout(size, size));
		Cell[][] cells = new Cell[size][size];
		filArray(size, cells);
		setNeibers(size, cells);
		setMines(size, mines, cells);
		setClicks(size, mines, cells);
//		Arrays.setAll(cells, i -> new Cell());

		Arrays.stream(cells).flatMap(Arrays::stream).forEach(q -> panel.add(q));
		window.add(panel);
		window.setSize(size * 50, size * 50);
		window.setVisible(true);
	}

	/**
	 * @param size
	 * @param mines
	 * @param cells
	 */
	private void setClicks(int size, int mines, Cell[][] cells) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Cell cell = cells[i][j];
				cell.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (SwingUtilities.isLeftMouseButton(e)) {
							cell.uncover();
							long clicked = Arrays.stream(cells).flatMap(Arrays::stream).filter(q -> !q.isEnabled())
									.count();
							if (clicked == Math.pow(size, 2) - mines) {
								System.out.println("WYGRAŁEŚ");
								System.exit(0);
							}
						} else if (SwingUtilities.isRightMouseButton(e)) {
							cell.setFlag();
						}
					}
				});
			}

		}
	}

	/**
	 * @param size
	 * @param mines
	 * @param cells
	 */
	private void setMines(int size, int mines, Cell[][] cells) {
		for (int i = 0; i < mines; i++) {
			int x, y;
			do {
				x = ThreadLocalRandom.current().nextInt(size);
				y = ThreadLocalRandom.current().nextInt(size);
			} while (cells[x][y].isMine());
			cells[x][y].setMine();
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
