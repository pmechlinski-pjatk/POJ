package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

public class Map {

	Random r = new Random();

	ElementyMapy[][] mapka = new ElementyMapy[Config.MAP_SIZE][Config.MAP_SIZE];

	Map() {
		if (Config.MAP == null || Config.MAP.isEmpty())
			generateRandomMap();
		else
			loadMap(Config.MAP);
	}

	private void generateRandomMap() {
		for (int i = 0; i < Config.MAP_SIZE; ++i) {
			for (int j = 0; j < Config.MAP_SIZE; ++j) {

				if (r.nextInt(20) == 0) {
					mapka[i][j] = ElementyMapy.PRZESZKODA;
					// mapka[i][j] = ElementyMapy.PUSTO;
					System.out.print("XX");
				} else

				{
					mapka[i][j] = ElementyMapy.PUSTO;
					System.out.print("  ");
				}

			}
			System.out.println();
		}
	}

	private void loadMap(final String file) {
		for (int i = 0; i < Config.MAP_SIZE; ++i) {
			for (int j = 0; j < Config.MAP_SIZE; ++j) {
				mapka[i][j] = ElementyMapy.PUSTO;
			}
		}

		try {
			@SuppressWarnings("resource")
			final Scanner s = new Scanner(new BufferedReader(new FileReader(file)));
			int y = 0;
			while (s.hasNextLine()) {
				final String myStr = s.nextLine();

				for (int x = 0; x < myStr.length(); ++x) {
					final char znak = myStr.charAt(x);

					if (znak != ' ')
						if (znak == 'm' || znak == 'M')
							mapka[x][y] = ElementyMapy.META;
						else
							mapka[x][y] = ElementyMapy.PRZESZKODA;

				}
				++y;
			}
		} catch (final FileNotFoundException e) {
			System.out.println("Nie odczytałem pliku");
		}
	}

	public boolean isMeta(final float x, final float y) {
		if (x < 0 || y < 0 || x >= Config.MAP_SIZE || y >= Config.MAP_SIZE)
			return false;
		if (mapka[(int) x][(int) y].equals(ElementyMapy.META))
			return true;
		return false;
	}

	public boolean isOccupied(final float x, final float y) {
		if (x < 0 || y < 0 || x >= Config.MAP_SIZE || y >= Config.MAP_SIZE)
			return true;
		if (mapka[(int) x][(int) y].equals(ElementyMapy.POZA_MAPA)
				|| mapka[(int) x][(int) y].equals(ElementyMapy.PRZESZKODA))
			return true;
		return false;
	}

	public Pair<Double, Double> randomEmptyTile() {
		int x, y;
		do {
			x = r.nextInt(Config.MAP_SIZE);
			y = r.nextInt(Config.MAP_SIZE);
		} while (isOccupied(x, y));
		return new Pair<>(x + r.nextDouble(), y + r.nextDouble());
	}

	enum ElementyMapy {
		POZA_MAPA, PRZESZKODA, PUSTO, META
	}

	public ElementyMapy getStructure(final float x, final float y) {
		if (x < 0 || y < 0 || x > Config.MAP_SIZE || y > Config.MAP_SIZE)
			return ElementyMapy.POZA_MAPA;
		return mapka[(int) x][(int) y];
	}

	public void setMeta(final int x, final int y) {
		if (x < 0 || y < 0 || x > Config.MAP_SIZE || y > Config.MAP_SIZE)
			return;
		mapka[x][y] = ElementyMapy.META;
	}

}
