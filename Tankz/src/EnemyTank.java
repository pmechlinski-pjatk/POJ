public class EnemyTank extends MovingObject {

    private String direction;

    public EnemyTank(String name, String image, int hp, boolean isDestructible, Cell linkedCell) {
        super(name, image, hp, isDestructible, linkedCell);
    }


    public void watchtower() {};
    public void adjustDirection() {};
    public void shoot() {};

    public void move () {

        // some loop with a panther-like moving algo.
            // then watchtower() for ID enemy tanks nearby and shoot if needed.
            // adjustDirection() should be done after every move; it should include adjusting image.
            // then shoot() at random for shit & giggles on a proper direction
    }

}
