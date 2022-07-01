import javax.swing.*;

// Render + logika + klasa-tablica z polami gry i ich wartościami

public class Cell extends JLabel
{
	Cell[][] neibers = new Cell[3][3];
	private int tiledX;
	private int tiledY;
	private GameObject linkedObject;

	public Cell (int tiledX, int tiledY)
	{
		this.tiledX = tiledX;
		this.tiledY = tiledY;
	}

	public int getTiledY() {
		return tiledY;
	}

	public int getTiledX() {
		return tiledX;
	}

	public int [] getCoordinates()
	{
		int [] coords = new int[2];
		coords[0] = this.tiledX;
		coords[1] = this.tiledY;
		return coords;
	}

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
		//else return tiledX+" "+tiledY; // DEBUG MODE
		else return "";
	}



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