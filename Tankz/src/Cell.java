import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JLabel;
// Render + logika + klasa-tablica z polami gry i ich wartościami
@SuppressWarnings("serial")
public class Cell extends JLabel
{
	Cell[][] neibers = new Cell[3][3];
	private GameObject linkedObject;
	public void setNeiber(Cell cell, int p, int q)
	{
		neibers[p][q] = cell;
	}

	@Override
	public String toString()
	{
		if (linkedObject != null)
		{
			return linkedObject.getImage();
		}
		return "";
	}

//		//1:Wall, 2:Base, 3:Player, 4:Enemy, 5:MissileH, 6:MissileV
//		// Using a pinch of html in JLabels can make every gameObject have 3x3 dimension.
//		String sprite;
//		switch(objectType) {
//			case 1:
//				sprite = "<html>###<br/>###<br/>###</html>";
//				break;
//			case 2:
//				sprite = "<html>@=@<br/>|| F ||<br/>@=@</html>";
//				break;
//			case 3:
//				sprite = "<html>_|_<br/>[+]<br/></html>";
//				break;
//			case 4:
//				sprite = "<html>_!_<br/>[X]<br/>###</html>";
//				break;
//			case 5:
//				sprite = "*";
//				break;
//			default:
//				sprite = "";
//				break;
//		}
//		return sprite;
//	}

	public GameObject getLinkedObject() {
		return linkedObject;
	}

	public void setLinkedObject(GameObject linkedObject) {
		this.linkedObject = linkedObject;
	}

	void redraw() {
		setText(toString());
	}
}