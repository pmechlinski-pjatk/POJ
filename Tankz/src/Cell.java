import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;

// Render + logika + klasa-tablica z polami gry i ich wartościami
@SuppressWarnings("serial")
public class Cell extends JLabel {
	public Cell() {
		this.entityType = "";
	}

	Cell[][] neibers = new Cell[3][3];

	public String entityType = "";

	public boolean isEntity() {
		if (this.entityType == "") {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String toString() {
		//1:Wall, 2:Base, 3:Player, 4:Enemy, 5:MissileH, 6:MissileV
		String sprite;
		switch(entityType) {
			case "Wall":
				sprite = String.valueOf('#');
				break;
			case "EnemyBase":
				sprite = "F";
				break;
			case "Player":
				sprite = "[+]";
				break;
			case "Enemy":
				sprite = "x";
				break;
			case "Missile":
				sprite = "*";
				break;
			default:
				sprite = "";
				break;
		}
		return sprite;
	}
//	public Cell movable (int kierunek) // TODO: Change into probably enum with used keys
//	{
//		setHorizontalTextPosition(CENTER);
//		Cell movable;
//		switch (kierunek) {
//			case 1: //góra
//				movable = neibers[2][1];
//				break;
//			case 2: //dół
//				movable = neibers[2][3];
//				break;
//			case 3: //lewo
//				movable = neibers[1][2];
//				break;
//			case 4: //prawo
//				movable = neibers[3][2];
//				break;
//			default:
//				movable = neibers[2][2];
//				break;
//		}
//		return movable;
//	}
//	private int objectType; // { 1:Wall, 2:Base, 3:Player, 4:Enemy, 5:MissileH, 6:MissileV };

//	public Cell() {
//		setHorizontalTextPosition(CENTER);
//	}

	public void setNeiber(Cell cell, int p, int q) {
		neibers[p][q] = cell;
	}


	void redraw() {
		setText(toString());
	}
}


