import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JLabel;
// Render + logika + klasa-tablica z polami gry i ich wartościami
@SuppressWarnings("serial")
public class Cell extends JLabel {
	Cell[][] neibers = new Cell[3][3];
	public boolean isPlayer () { if (this.objectType == 3) return true; return false; };

	public int hp;
	public Cell movable (int kierunek) // TODO: Change into probably enum with used keys
	{
		setHorizontalTextPosition(CENTER);
		Cell movable;
		switch (kierunek) {
			case 1: //góra
				movable = neibers[2][1];
				break;
			case 2: //dół
				movable = neibers[2][3];
				break;
			case 3: //lewo
				movable = neibers[1][2];
				break;
			case 4: //prawo
				movable = neibers[3][2];
				break;
			default:
				movable = neibers[2][2];
				break;
		}
		return movable;
	}
	private int objectType; // { 1:Wall, 2:Base, 3:Player, 4:Enemy, 5:MissileH, 6:MissileV };

//	public Cell() {
//		setHorizontalTextPosition(CENTER);
//	}

	public void setNeiber(Cell cell, int p, int q) {
		neibers[p][q] = cell;
	}

	@Override
	public String toString() {
		//1:Wall, 2:Base, 3:Player, 4:Enemy, 5:MissileH, 6:MissileV
		// Using a pinch of html in JLabels can make every gameObject have 3x3 dimension.
		String sprite;
		switch(objectType) {
			case 1:
				sprite = "<html>###<br/>###<br/>###</html>";
				break;
			case 2:
				sprite = "|F|";
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
		toString();
	}

	void redraw() {
		setText(toString());
	}
}