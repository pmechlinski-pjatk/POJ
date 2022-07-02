public class Player extends MovingObject {


    public Player(String name, String image, int hp, boolean isDestructible, Cell linkedCell) {
        super(name, image, hp, isDestructible, linkedCell);
    }
public void tryAction()
{

}
    public void tryMove() {} // Should allow to move player's tank according to the keyboard keys pressed.

    public void shoot() {} // Player should be able to shoot at will / at clicking SPACE or something.
}
