public class MovingObject extends GameObject {
    public MovingObject(String image, int hp, boolean isDestructible, Cell linkedCell) {
        super(image, hp, isDestructible, linkedCell);
    }

    //private enum movementType { RANDOM, PANTHER, CONTROLLABLE }; // For now I'm not sure, if this will even be needed as of now.




    // TODO: Function to return neibourghs, e.g. "None" For EOM (EndOfMap), "Obstacle" for immovable object like wall, "Friend", "Enemy", "EmptyField".

    /*public String[][] getNeibers {

        return neibers[][]
    }

     */
}
