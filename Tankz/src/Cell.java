import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Cell extends JButton {
	Cell[][] neibers = new Cell[3][3];
	private boolean isMine;
	private boolean isFlag;

	public Cell() {
		// TODO Auto-generated constructor stub
	}

	public void setNeiber(Cell cell, int p, int q) {
		neibers[p][q] = cell;
	}

	public boolean isMine() {
		return isMine;
	}

	public void setMine() {
		isMine = true;
	}

	public void setFlag() {
		if (!isEnabled())
			return;
		isFlag = !isFlag;
		redraw();
	}

	@Override
	public String toString() {
		if (isFlag)
			return "F";
		if (isEnabled())
			return "";
		if (isMine)
			return "+";
		if (getValue() > 0)
			return getValue() + "";
		return "";
	}

	private long getValue() {
		return Arrays.stream(neibers).flatMap(Arrays::stream).filter(q -> q != null && q.isMine).count();
	}

	private void redraw() {
		setText(toString());
	}

	public void uncover() {
		if (isMine) {
			// TODO przegrana
			System.out.println("FRAJER");
			System.exit(0);
		}
		uncover(new ArrayList<Cell>());
	}

	private void uncover(List<Cell> done) {
		done.add(this);
		if (isMine)
			return;
		setEnabled(false);
		redraw();
		if (getValue() > 0)
			return;

		for (Cell[] cells : neibers) {
			for (Cell cell : cells) {
				if (cell != null && !done.contains(cell))
					cell.uncover(done);
			}

		}

	}
}
