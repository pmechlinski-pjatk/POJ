package main;

public class Logic extends Thread {
	@Override
	public String toString() {
		return ((allTimesFramesGenerated / (Config.FRAMES_PER_SECOND * 1f)) + "     ").substring(0, 5);
	}

	private final int INTERVAL = 1000 / Config.FRAMES_PER_SECOND;
	final Map mapa;
	protected long allTimesFramesGenerated = 0;

	private final Players gamers;

	public Logic(final Map mapa, final Players players) {
		this.mapa = mapa;
		gamers = players;
	}

	@Override
	public void run() {
		final Thread[] playersThreads = new Thread[gamers.size()];

		for (int i = 0; i < gamers.size(); ++i) {
			playersThreads[i] = new Thread(gamers.get(i));
			// watkiGraczy[i].setPriority(MIN_PRIORITY);
			playersThreads[i].start();
		}

		long timeBeforeTick;
		boolean theEnd;

		while (true) {
			++allTimesFramesGenerated;
			timeBeforeTick = System.currentTimeMillis();
			theEnd = true;
			for (int i = 0; i < gamers.size(); ++i) {
				Unit unit = gamers.get(i);
				if (unit == null) {
					if (Config.REBORN) {
						unit = gamers.regenerate(i);
						playersThreads[i] = new Thread(unit);
						// watkiGraczy[i].setPriority(MIN_PRIORITY);
						playersThreads[i].start();
					}
					continue;
				}
				theEnd = false;
				if (unit.die() || mapa.isMeta(unit.x, unit.y)) {
					playersThreads[i].stop();
					if (mapa.isMeta(unit.x, unit.y))
						System.out.println(this + "s: Wygrywa gracz " + unit.getClass() + " o nazwie " + unit.ID);
					else
						System.out.println(this + "s: Ginie gracz " + unit.getClass() + " o nazwie " + unit.ID);

					while (playersThreads[i].isAlive())
						try {
							Thread.sleep(1);
						} catch (final InterruptedException e) {
						}
					playersThreads[i] = null;
					gamers.set(i, null);
				} else
					unit.step();

			}
			if (theEnd)
				System.exit(0);
			final long timeCurrnet = System.currentTimeMillis();
			try {
				Thread.sleep(INTERVAL - (timeCurrnet - timeBeforeTick));
			} catch (final Exception e) {
				System.out.println(
						"Nie wyrabiam z logika glowna! Zabrakło mi: " + (((timeCurrnet - timeBeforeTick) - INTERVAL))
								+ "ms a na jedną klatke mialem: " + INTERVAL + "ms");
			}

		}

	}
}
