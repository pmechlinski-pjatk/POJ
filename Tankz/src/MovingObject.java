public class MovingObject extends GameObject {
    public MovingObject(String name, String image, int hp, boolean isDestructible, Cell linkedCell) {
        super(name, image, hp, isDestructible, linkedCell);
    }

    //private enum movementType { RANDOM, PANTHER, CONTROLLABLE }; // For now I'm not sure, if this will even be needed as of now.

    private char direction;

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    // TODO: Function to return neibourghs, e.g. "None" For EOM (EndOfMap), "Obstacle" for immovable object like wall, "Friend", "Enemy", "EmptyField".

    public String[][] getNeibers() {
        Cell neibers[][] = this.getLinkedCell().neibers;
        String results[][] = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
            {
                if (i == 1 && j == 1) {
                    results[i][j] = "CENTER";
                    continue;
                } else if (neibers[i][j] == null) {
                    results[i][j] = "EOM"; // End of Map
                    continue;
                } else if (neibers[i][j].getLinkedObject() == null) {
                    results[i][j] = "0"; // Clear to go.
                    continue;
                } else {
                    results[i][j] = neibers[i][j].getLinkedObject().getName();
                    continue;
                }
            }
        }
        return results;
    }

    public void changeLinkedCell(Cell newCell)
    {
        linkedCell.setLinkedObject(null);
        linkedCell.redraw();
        this.linkedCell = newCell;
        newCell.setLinkedObject(this);
        newCell.redraw();
    }
}

