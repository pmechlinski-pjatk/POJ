import java.util.*;
import javax.swing.*;

public class Cell extends JLabel {
	Cell[][] neibers = new Cell[3][3];
	private boolean isAlive;
	public Cell() {
		Random r = new Random();
		this.isAlive = r.nextBoolean();
		if (this.isAlive) this.isAlive = r.nextBoolean();
		if (this.isAlive) this.isAlive = r.nextBoolean();
		// Te dwa paskudniki powyżej zostały dodane, żeby całości nadać większej przejrzystości: pojawia się mniej wzorów, za to łatwiej zauważyć "komórki".
	}
	public int sumAlive() {
		int sum = 0;
		for(int i=0; i< 3; i++)
		{
			for(int j=0; j< 3; j++)
			{
				if (neibers[i][j] == null) continue;
				if (neibers[i][j].isAlive) sum++;
			}
		}
		return sum;
	}
	public void check()
	{
		if (this.isAlive)
		{
			if (sumAlive() < 2)
			{
				this.isAlive = false;
				return;
			}
			if (sumAlive() == 2 || sumAlive() == 3)
			{
				return;
			}
			if (sumAlive() > 3)
			{
				this.isAlive = false;
				return;
			}
		}
		else
		{
			if (sumAlive() == 3)
			{
				this.isAlive = true;
				return;
			}
		}
	}

	public void setNeiber(Cell cell, int p, int q)
	{
		neibers[p][q] = cell;
	}

	@Override
	public String toString()
	{
		if (isAlive) return "@";
		return "";
	}

	void redraw() {
		setText(toString());
	}

}