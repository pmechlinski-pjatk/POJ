public class missile extends movingObject{
    // A missile should have it's starting direction (and coordinates as every gameObject), should at least id another tile on it's track
    // and eventually should id collision and deal damage (or not if inapplicable)
    public missile(String image, int hp, boolean isDestructible) {
        super(image, hp, isDestructible);
    }

    private String direction;

    public void move() {}
}