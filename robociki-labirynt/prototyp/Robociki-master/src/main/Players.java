package main;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

import gamers.s26129;
import main.PhysicalObject.Cirkle;

public class Players {
	private final Random rand = new Random();
	private final Supplier<Unit>[] playersGenerator;
	private final Unit[] players;

	synchronized Unit get(final int i) {
		return players[i];
	}

	synchronized void set(final int i, final Unit u) {
		players[i] = u;
	}

	synchronized int size() {
		return players.length;
	}

	synchronized Unit regenerate(final int i) {
		if (players[i] == null)
			return players[i] = playersGenerator[i].get();
		return players[i];
	}

	public Players(final Map map) {
		playersGenerator = getUnitsGenerator(map);
		players = Arrays.stream(playersGenerator).map(w -> w.get()).toArray(x -> new Unit[x]);
	}

	@SuppressWarnings("unchecked")
	private Supplier<Unit>[] getUnitsGenerator(final Map map) {
		return toArrayInline(
		// UWAGA pierwszy parametr ID musi być unikalny
//				() -> new ChlebKrojony("ChlebKrojony", map.randomEmptyTile(), rand.nextInt(4) + rand.nextFloat(),
//						new Cirkle(0.3f)),
//				() -> new WhatIamDoingHere("WhatIamDoingHere", map.randomEmptyTile(),
//						rand.nextInt(4) + rand.nextFloat(), new Cirkle(0.3f)),
//				() -> new MultiKilator("MultiKilator", map.randomEmptyTile(), rand.nextInt(4) + rand.nextFloat(),
//						new Cirkle(0.3f)),
				() -> new s26129("s26129", map.randomEmptyTile(), rand.nextInt(4) + rand.nextFloat(), new Cirkle(0.3f))
		// () -> new WychodzadzPrawdziwy("WychodzadzPrawdziwy", new Pair<>(5.75,
		// 5.75),
		// rand.nextInt(4) + rand.nextFloat(), new Cirkle(0.3f), 50, 10, 10)

		);
	}

	@SuppressWarnings("unchecked")
	private Supplier<Unit>[] toArrayInline(final Supplier<Unit>... suppliers) {
		return suppliers;
	}

	// private static Unit[] getUnits(final String playersPackage) throws
	// InstantiationException, IllegalAccessException,
	// InvocationTargetException, NoSuchMethodException, ClassNotFoundException,
	// IOException {
	// // TODO nie dziala
	// final Class<Unit>[] all = getClasses(playersPackage);
	// final Unit[] gamers = new Unit[all.length];
	// for (int i = 0; i < all.length; ++i) {
	// final Class<Unit> x = all[i];
	// final Unit t = x.getDeclaredConstructor(String.class, double.class,
	// double.class, double.class,
	// CollisionModel.class, int.class, int.class,
	// int.class).newInstance(x.getName(),
	// rand.nextInt(Config.MAP_SIZE) + 0.5, rand.nextInt(Config.MAP_SIZE) + 0.5,
	// rand.nextInt(4) + rand.nextFloat(), new Cirkle(0.3f), 50, 10, 10);
	// gamers[i] = t;
	// }
	// return gamers;
	// }
	//
	// /**
	// * Scans all classes accessible from the context class loader which belong
	// * to the given package and subpackages.
	// *
	// * @param packageName
	// * The base package
	// * @return The classes
	// * @throws ClassNotFoundException
	// * @throws IOException
	// */
	// private static Class[] getClasses(final String packageName) throws
	// ClassNotFoundException, IOException {
	// final ClassLoader classLoader =
	// Thread.currentThread().getContextClassLoader();
	// assert classLoader != null;
	// final String path = packageName.replace('.', '/');
	// final Enumeration<URL> resources = classLoader.getResources(path);
	// final List<File> dirs = new ArrayList<>();
	// while (resources.hasMoreElements()) {
	// final URL resource = resources.nextElement();
	// dirs.add(new File(resource.getFile()));
	// }
	// final ArrayList<Class> classes = new ArrayList<>();
	// for (final File directory : dirs) {
	// classes.addAll(findClasses(directory, packageName));
	// }
	// return classes.toArray(new Class[classes.size()]);
	// }
	//
	// /**
	// * Recursive method used to find all classes in a given directory and
	// * subdirs.
	// *
	// * @param directory
	// * The base directory
	// * @param packageName
	// * The package name for classes found inside the base directory
	// * @return The classes
	// * @throws ClassNotFoundException
	// */
	// private static List<Class> findClasses(final File directory, final String
	// packageName)
	// throws ClassNotFoundException {
	// final List<Class> classes = new ArrayList<>();
	// if (!directory.exists()) {
	// return classes;
	// }
	// final File[] files = directory.listFiles();
	// for (final File file : files) {
	// if (file.isDirectory()) {
	// assert !file.getName().contains(".");
	// classes.addAll(findClasses(file, packageName + "." + file.getName()));
	// } else if (file.getName().endsWith(".class")) {
	// classes.add(
	// Class.forName(packageName + '.' + file.getName().substring(0,
	// file.getName().length() - 6)));
	// }
	// }
	// return classes;
	// }
}
