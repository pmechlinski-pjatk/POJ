
public class Game {
	final static int SIZE = 20; //  Currently the game field will be randomly generated.
	//							    For the (hypothetical later) game version in which there are predefined maps, also predefined map size would be unnecessary
	// 							  	Eventually there could be other things parametrized, such as:
	//								number of enemies, enemy bases, difficulty etc. in a still random game variant.
	//
	//								<!> Map size shouldn't be smaller than 7 & optimally it would be around 10-20.
	public static void main(String[] args) {
		Window main = new Window(SIZE);
	}
}
