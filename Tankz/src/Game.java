
public class Game {
	final static int SIZE = 10; //  Currently the game field will be randomly generated.
	//							    For the (hypothetical later) game version in which there are predefined maps, also predefined map size would be unnecessary
	// 							  	Eventually there could be other things parametrized, such as:
	//								number of enemies, enemy bases, difficulty etc. in a still random game variant.

	public static void main(String[] args) {
		Window main = new Window(SIZE);
	}
}
