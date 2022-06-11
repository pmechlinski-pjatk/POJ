import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Cell extends JButton {
	Cell[][] neibers = new Cell[3][3];
	private int objectType; // { 1:Wall, 2:Base, 3:Player, 4:Enemy, 5:MissileH, 6:MissileV };

	public Cell() {
		// TODO Auto-generated constructor stub
	}

	public void setNeiber(Cell cell, int p, int q) {
		neibers[p][q] = cell;
	}

	@Override
	public String toString() {
		//1:Wall, 2:Base, 3:Player, 4:Enemy, 5:MissileH, 6:MissileV
		String sprite;
		switch(objectType) {
			case 1:
				sprite = "#";
				break;
			case 2:
				sprite = "[F]";
				break;
			case 3:
				sprite = "[+]";
				break;
			case 4:
				sprite = "[x]";
				break;
			case 5:
				sprite = "~";
				break;
			case 6:
				sprite = ";";
				break;
			default:
				sprite = "";
				break;
		}
		return sprite;
	}

	public boolean isEntity ()
	{
		if (this.toString() == "") return false;
		else return true;
	}


	public void setEntity(int entityNumber) { //Default Set entity function.
		this.objectType = entityNumber;

		}

/*	private void setMines(int size, int mines, Cell[][] cells) {
		for (int i = 0; i < mines; i++) {
			int x, y;
			do {
				x = ThreadLocalRandom.current().nextInt(size);
				y = ThreadLocalRandom.current().nextInt(size);
			} while (cells[x][y].isMine());
			cells[x][y].setMine();
		}
	}*/
	}
}

