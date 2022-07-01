

public class Missile extends MovingObject {
    public Missile(String name, String image, int hp, boolean isDestructible, Cell linkedCell) {
        super(name, image, hp, isDestructible, linkedCell);
    }
    // A missile should have it's starting direction (and coordinates as every gameObject), should at least id another tile on it's track
    // and eventually should id collision and deal damage (or not if inapplicable)



    public void move() {}
}
