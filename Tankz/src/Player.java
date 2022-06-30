public class Player extends movingObject{
    public Player(String image, int hp, boolean isDestructible) {
        super(image, hp, isDestructible);
    }

    public void move() {} // Should allow to move player's tank according to the keyboard keys pressed.

    public void shoot() {} // Player should be able to shoot at will / at clicking SPACE or something.
}
