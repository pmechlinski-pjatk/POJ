import java.awt.GridLayout;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

public class Window {

	public Window(int size) { //In this version it will generate the random map very randomly xD
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JPanel panel = new JPanel(new GridLayout(size, size));

		Map map = new Map(size, 1, 5);

		Arrays.stream(map).flatMap(Arrays::stream).forEach(q -> panel.add(q));
		window.add(panel);
		window.setSize(size * 30, size * 30);
		window.setVisible(true);

	}

}
