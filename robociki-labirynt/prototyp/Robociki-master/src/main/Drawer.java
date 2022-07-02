package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Drawer extends JPanel {
	private final Map map;
	private final Players units;
	private final Random rand = new Random();
	Stats message;

	public Drawer(final Map map, final Stats stats, final Players players) {
		this.map = map;
		units = players;
		message = stats;

		this.setPreferredSize(new Dimension(Config.MAP_SIZE * Config.ZOOM, Config.MAP_SIZE * Config.ZOOM));
		setBackground(Color.white);

		final JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(this);
		window.pack();
		window.setVisible(true);
	}

	@Override
	public void paintComponent(final Graphics g) {
		final Graphics2D frame = (Graphics2D) g;
		frame.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		frame.clearRect(0, 0, getWidth(), getHeight());

		// przeszkody na mapie
		for (int i = 0; i < Config.MAP_SIZE; ++i)
			for (int j = 0; j < Config.MAP_SIZE; ++j)
				if (map.isOccupied(i, j)) {
					frame.setColor(new Color(0, 100, 0));
					frame.fillRect(i * Config.ZOOM, j * Config.ZOOM, Config.ZOOM, Config.ZOOM);
				} else if (map.isMeta(i, j)) {
					frame.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
					frame.fillRect(i * Config.ZOOM, j * Config.ZOOM, Config.ZOOM, Config.ZOOM);
				}
		final Color[] kolorkiRobotow = new Color[] { Color.RED, Color.BLUE, new Color(165, 42, 42), Color.GREEN,
				Color.ORANGE, new Color(128, 0, 128), new Color(0, 128, 128), Color.PINK, Color.GRAY };
		// wyswietlanie robotow
		for (int i = 0; i < units.size(); ++i) {
			frame.setColor(kolorkiRobotow[i]);
			final Unit unit = units.get(i);
			if (unit != null) {
				if (unit.isAttacking())
					frame.fillOval(
							(int) ((Config.ZOOM * (unit.x + (unit.getMoveVectorX() / 2)))
									- (unit.collisionModel.getRadius() * Config.ZOOM) / 6),
							(int) ((Config.ZOOM * (unit.y + (unit.getMoveVectorY() / 2)))
									- (unit.collisionModel.getRadius() * Config.ZOOM) / 6),
							(int) (unit.collisionModel.getRadius() * 2 * Config.ZOOM) / 6,
							(int) (unit.collisionModel.getRadius() * 2 * Config.ZOOM) / 6);
				frame.drawOval((int) ((Config.ZOOM * unit.x) - (unit.collisionModel.getRadius() * Config.ZOOM)),
						(int) ((Config.ZOOM * unit.y) - (unit.collisionModel.getRadius() * Config.ZOOM)),
						(int) (unit.collisionModel.getRadius() * 2 * Config.ZOOM),
						(int) (unit.collisionModel.getRadius() * 2 * Config.ZOOM));
				frame.drawLine((int) (Config.ZOOM * unit.x), (int) (Config.ZOOM * unit.y),
						(int) (Config.ZOOM * (unit.x + (unit.getMoveVectorX() / 2))),
						(int) (Config.ZOOM * (unit.y + (unit.getMoveVectorY() / 2))));
				frame.drawString(unit.getPlayerID() + " " + (int) (100d * unit.healthPoints / unit.maxHealthPoints) + "%",
						(int) ((Config.ZOOM * unit.x) + (unit.collisionModel.getRadius() * Config.ZOOM))
								+ (Config.ZOOM / 4),
						(int) ((Config.ZOOM * unit.y) + (unit.collisionModel.getRadius() * Config.ZOOM)));
			}
		}
		frame.setColor(Color.BLACK);
		frame.drawString(message.toString(), 10, 20);
	}

}
