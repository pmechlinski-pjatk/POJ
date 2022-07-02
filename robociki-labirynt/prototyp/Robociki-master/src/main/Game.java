package main;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Game {

	public static void main(final String[] args) throws InterruptedException, ClassNotFoundException, IOException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, NoSuchFieldException {

		final Map map = new Map();
		final Players players = new Players(map);

		final Stats stats = new Stats();
		setColisionCounterData("main.PhysicalObject$Collisions", map, stats, players);

		final Drawer display = new Drawer(map, stats, players);
		final Thread logic = new Logic(map, players);
		logic.start();

		while (true) {
			display.repaint();
			Thread.sleep(25);
		}

	}

	private static void setColisionCounterData(final String collisionClassName, final Map map, final Stats stats,
			final Players players) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
		final Class<?> col = Class.forName(collisionClassName);
		final Field field = col.getDeclaredField("map");
		field.setAccessible(true);
		field.set(null, map);
		final Field field2 = col.getDeclaredField("units");
		field2.setAccessible(true);
		field2.set(null, players);
		final Field field3 = col.getDeclaredField("stats");
		field3.setAccessible(true);
		field3.set(null, stats);
	}

}
