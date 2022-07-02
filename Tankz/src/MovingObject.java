import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

public class MovingObject extends GameObject {
    public MovingObject(String name, String image, int hp, boolean isDestructible, Cell linkedCell) {
        super(name, image, hp, isDestructible, linkedCell);
    }

    //private enum movementType { RANDOM, PANTHER, CONTROLLABLE }; // For now I'm not sure, if this will even be needed as of now.

    private char direction;

    private String northStandby;
    private String southStandby;
    private String eastStandby;
    private String westStandby;

    public String getWestStandby() {
        return westStandby;
    }

    public void setWestStandby(String westStandby) {
        this.westStandby = westStandby;
    }

    public String getEastStandby() {
        return eastStandby;
    }

    public void setEastStandby(String eastStandby) {
        this.eastStandby = eastStandby;
    }

    public String getSouthStandby() {
        return southStandby;
    }

    public void setSouthStandby(String southStandby) {
        this.southStandby = southStandby;
    }

    public String getNorthStandby() {
        return northStandby;
    }

    public void setNorthStandby(String northStandby) {
        this.northStandby = northStandby;
    }

    public MovingObject() {
        super();
    }

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

    public void panther(Cell [][] cells) throws InterruptedException // Standard enemy tank moving pattern
    {
        int test = 0;
        // Random starting direction.
        int k4 = ThreadLocalRandom.current().nextInt(4);
        while (true)
        {


            System.out.println("k4: "+k4);
            switch(k4)
            {
                case 0:
                    test = tryMoveRespond('N', cells);
                case 1:
                    test = tryMoveRespond('E', cells);
                case 2:
                    test = tryMoveRespond('W', cells);
                case 3:
                    test = tryMoveRespond('S', cells);
                default:
                    System.out.println("(-) Tank AI movement error.");
                    break;

            }
            // Choose a new random destination if current is unavailable
            if (test == 0) k4 = ThreadLocalRandom.current().nextInt(4);
            sleep(2000);
        }
    }

    // Utility functions
    public String getTestNeiber(char direction) {
        String neibers[][] = getNeibers();
        switch (direction) {
            case 'N':
                return neibers[0][1];
            case 'E':
                return neibers[1][0];
            case 'S':
                return neibers[2][1];
            case 'W':
                return neibers[1][2];
            default:
                return "EOM";
        }
    }

    public Cell getCoordCell(char direction, Cell[][] cells) {
        String neibers[][] = getNeibers();
        int x = this.getLinkedCell().getTiledX();
        int y = this.getLinkedCell().getTiledY();

        switch (direction) {
            case 'N':
                return cells[x - 1][y];
            case 'E':
                return cells[x][y + 1];
            case 'S':
                return cells[x + 1][y];
            case 'W':
                return cells[x][y - 1];
            default:
                return null;
        }
    }

        public void tryMove(char k, Cell cells[][])
        {
            String neibers[][] = this.getNeibers();
            int x = this.getLinkedCell().getTiledX();
            int y = this.getLinkedCell().getTiledY();
            // CENTRAL SHOULD BE neibers[1][1]
            switch(k) {
                case 'w':
                    setImage(northStandby);
                    setDirection('N');
                    this.linkedCell.redraw();
                    if (neibers[0][1] == "0") changeLinkedCell(cells[x-1][y]);
                    break;
                case 'a':
                    setImage(westStandby);
                    setDirection('W');
                    this.linkedCell.redraw();
                    if (neibers[1][0] == "0") changeLinkedCell(cells[x][y-1]);
                    break;
                case 'd':
                    setImage(eastStandby);
                    setDirection('E');
                    this.linkedCell.redraw();
                    if (neibers[1][2] == "0") changeLinkedCell(cells[x][y+1]);
                    break;
                case 's':
                    setImage(southStandby);
                    setDirection('S');
                    this.linkedCell.redraw();
                    if (neibers[2][1] == "0") changeLinkedCell(cells[x+1][y]);
                    break;
                default:
                    System.out.println("(?)Unknown control error.");
                    break;
            }
        }

    private int tryMoveRespond(char direction, Cell[][] cells)
    {
        // Will work agnostically both for direction uppercase (geographical) and for keyboard strokes 'directions'.
        char d = dirUnif(direction);
        String neibers[][] = this.getNeibers();
        int x = this.getLinkedCell().getTiledX();
        int y = this.getLinkedCell().getTiledY();

        switch(d) {
            case 'N':
                setImage(northStandby);
                setDirection('N');

                if (neibers[0][1] == "0")
                {
                    changeLinkedCell(cells[x-1][y]);
                    this.linkedCell.redraw();
                    return 1;
                }
                break;
            case 'W':
                setImage(westStandby);
                setDirection('W');

                if (neibers[1][0] == "0")
                {
                    changeLinkedCell(cells[x][y-1]);
                    this.linkedCell.redraw();
                    return 1;
                }
                break;
            case 'E':
                setImage(eastStandby);
                setDirection('E');
                this.linkedCell.redraw();
                if (neibers[1][2] == "0")
                {
                    changeLinkedCell(cells[x][y+1]);
                    this.linkedCell.redraw();
                    return 1;
                }
                break;
            case 'S':
                setImage(southStandby);
                setDirection('S');
                this.linkedCell.redraw();
                if (neibers[2][1] == "0")
                {
                    changeLinkedCell(cells[x+1][y]);
                    this.linkedCell.redraw();
                    return 1;
                }
                break;
            default:
                System.out.println("(?)Unknown control error.");
                return -1;

        }
        return 0; // If there's no move or error. For success return 1
    }

    private char dirUnif(char direction)
    {
        if (direction == 'w') return 'N';
        else if (direction == 's') return 'S';
        else if (direction == 'a') return 'A';
        else if (direction == 'd') return 'E';
        else if (direction == 'w') return 'N';
        else return direction;
    }
}


