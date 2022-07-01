import java.awt.GridLayout;
import java.util.Arrays;
import javax.swing.*;

import static java.lang.Thread.sleep;

public class Window {

	private int size;

	public Window(int size, int entities) throws InterruptedException {
		this.size = size;
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JPanel panel = new JPanel(new GridLayout(size, size));

		Cell[][] cells = new Cell[size][size];
		filArray(size, cells);
		setNeibers(size, cells);

		Arrays.stream(cells).flatMap(Arrays::stream).forEach(q -> panel.add(q));
		window.add(panel);
		window.setSize(size * 15, size * 15);
		window.setVisible(true);

		gameLoop(cells, 1000);

	}

	private void gameLoop(Cell[][] cells, int interval) throws InterruptedException {
		while (true) {
			sleep(interval);

			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					cells[i][j].redraw();
					cells[i][j].check();
				}
			}
			setNeibers(size, cells);
		}
	}
	private void setNeibers(int size, Cell[][] cells)
	{
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{

				for (int p = 0; p <= 2; p++)
				{
					for (int q = 0; q <= 2; q++)
					{
						int x = i + p - 1, y = j + q - 1;
						if (x >= 0 && x < size && y >= 0 && y < size)
							cells[i][j].setNeiber(cells[x][y], p, q);

					}
				}
			}
		}
	}

	private void filArray(int size, Cell[][] cells)
	{
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				cells[i][j] = new Cell();
			}
		}
	}

}