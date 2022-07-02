package main;

public class Config {
	// mnożnik szybkosci gry
	public static final double EMULATION_SPEED = 1;
	// ile odswiezen pozycji na sekunde
	public static final int EMULATION_ACCURACY = 50;

	// takie verbose
	public static final boolean DEBUG = false;
	// puste generuje losowa
	public static final String MAP = "labirynt2"; // labirynt2
	// rozmiar mapy
	public static final int MAP_SIZE = 25;
	// ile pixeli ma kratka
	public static final int ZOOM = 30;
	// wymuś kolizje
	public static final boolean WALLS_CRASH = true;
	// odradzanie po śmierci
	public static final boolean REBORN = true;

	public static final int FRAMES_PER_SECOND = (int) (EMULATION_ACCURACY * EMULATION_SPEED);
}
