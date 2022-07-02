import static java.lang.Thread.sleep;
import static java.util.Objects.isNull;

public class Missile extends MovingObject {

    // A missile should have it's starting direction (and coordinates as every gameObject), should at least id another tile on it's track
    // and eventually should id collision and deal damage (or not if inapplicable)

    //   private String MissileHorizontal = "<html><font color='orange'>~</font></html>";
//   private String MissileVertical = "<html><font color='orange'>\"</font></html>";
    private String MissileHorizontal = "<html><font color='orange'>@</font></html>";
    private String MissileVertical = "<html><font color='orange'>@</font></html>";

    private int hp = 0;
    private String name = "Missile";


//    @Override
//    public int getHp() {
//        return hp;
//    }
//
//    @Override
//    public void setHp(int hp) {
//        this.hp = hp;
//    }

//    @Override
//    public void changeLinkedCell(Cell newCell) {
//        linkedCell.setLinkedObject(null);
//        int x = linkedCell.getTiledX();
//        int y = linkedCell.getTiledY();
//        linkedCell.redraw();
//        this.linkedCell = newCell;
//        newCell.setLinkedObject(this);
//        newCell.redraw();
//
//    }



    public Missile(char direction, Cell startingTile, Cell[][] cells, int size) {
        super();
        this.setDirection(direction);
        this.setLinkedCell(startingTile);
        int x = startingTile.getTiledX();
        int y = startingTile.getTiledY();
        cells[x][y].setLinkedObject(this);
        this.setImage("*");
        this.linkedCell.redraw();
//        new Thread(() -> {
            System.out.println("New thread initialized");
            if (!isNull(this.getLinkedCell()) && this.getDirection() != '0')
                {
                    System.out.println("missileMove initializing");
                    this.missileMove(cells, size);
                }
//        }).start();
        System.out.println("Missile stopping");
        stop();
    }

    // Move control flow

    public void missileMove(Cell[][] cells, int size) {
        System.out.println("missileMove initialized");
        if (this.getLinkedCell() == null) stop();
        String neibers[][] = this.getNeibers();
        int x = this.getLinkedCell().getTiledX();
        int y = this.getLinkedCell().getTiledY();
        if (!isNull(getDirection()) && getDirection() != '0')
        {
            //            try {
//                sleep(1500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            System.out.println("controlLoop initalizing");
            controlLoop(this.getDirection(), this.getNeibers(), cells, size);
            // TODO: Check, if I can get along without 'size' variable  - maybe isNotNull will be enough?
        }
        stop();
    }

    public void controlLoop(char direction, String[][] neibers, Cell[][] cells, int size) {
        if (direction == 'N' || direction == 'S') setImage(MissileVertical);
        else if (direction == 'E' || direction == 'W') setImage(MissileHorizontal);
        else {
            setImage(" ");
            stop();
            return;
        }

        int x = this.getLinkedCell().getTiledX();
        int y = this.getLinkedCell().getTiledY();

        String testNeiber;


        while (!isNull(getDirection()) && getDirection() != '0')
        {
            testNeiber = getTestNeiber(direction);
            if (testNeiber == "EnemyBase" || testNeiber == "Player" || testNeiber == "EnemyTank" || testNeiber == "Wall") testNeiber = "Destructible";
            System.out.println("moveInLoop initialized");
            System.out.println("testNeiber: "+testNeiber);
            moveInLoop(direction, testNeiber, cells, size);
        }
        stop();
    }

    public void moveInLoop(char direction, String neiber, Cell[][] cells, int size) {
        int x = this.getLinkedCell().getTiledX();
        int y = this.getLinkedCell().getTiledY();

        if (isNull(neiber)) {
            System.out.println("moveInLoop: Neiber is null");
            stop();
            return;
        }

        switch (neiber) {
            case "0": // Can move freely
                if (!isNull(getCoordCell(direction, cells))) {
                    System.out.println("moveInLoop: Neiber is empty, can move");
                    this.setLinkedCell(getCoordCell(direction, cells));
                    getLinkedCell().redraw();
                    getCoordCell(direction, cells).setLinkedObject(this);
                    cells[x][y].setLinkedObject(null);
                    cells[x][y]. redraw();
                    return;
                }
                return;
            case "Missile": // Two missiles should cancel each other
                if (!isNull(getCoordCell(direction, cells))) {
                    System.out.println("moveInLoop: Neiber is missile, should self-destruct");
                    getLinkedCell().setLinkedObject(null);
                    getLinkedCell().redraw();
                    this.setLinkedCell(null);
                    getCoordCell(direction, cells).setLinkedObject(null);
                    getCoordCell(direction, cells).redraw();
                    stop();
                    return;
                }
            case "EOM": // End of map
                if (!isNull(getCoordCell(direction, cells))) {
                    System.out.println("moveInLoop: Neiber is out of range, should self-destruct");
                    getLinkedCell().setLinkedObject(null);
                    getLinkedCell().redraw();
                    this.setLinkedCell(null);
                    stop();
                    return;
                }
            case "Destructible": // Deal damage before disappearing
                if (!isNull(getCoordCell(direction, cells))) {
                    System.out.println("moveInLoop: Neiber is destructible, should deal damage and self-destruct");
                    getLinkedCell().setLinkedObject(null);
                    getLinkedCell().redraw();
                    this.setLinkedCell(null);
                    getCoordCell(direction, cells).getLinkedObject().setHp(getHp() - 1);
                    stop();
                    return;
                }
            default:
                System.out.println("moveInLoop: Neiber is something else, stopping");
                stop();
                return;
        }
    }

    public void stop() {
        this.setDirection('0');
        System.out.println("Missile stopped");
        this.setImage(" ");
        this.getLinkedCell().redraw();
        if (!isNull(getLinkedCell()))
        {
            this.getLinkedCell().setLinkedObject(null);
            setLinkedCell(null);
        }
        return;
        }








    //        switch (getDirection())
//        {
//            case 'N':
//                setImage(MissileVertical);
//
//                while (true)
//                {
//                    x = getLinkedCell().getTiledX();
//                    y = getLinkedCell().getTiledY();
//                    switch (neibers[0][1])
//                    {
//                        case "Missile":
//                            cells[x-1][y].getLinkedObject().setLinkedCell(null);
//                            cells[x-1][y].setLinkedObject(null);
//                            this.getLinkedCell().setLinkedObject(null);
//                            this.getLinkedCell().setLinkedObject(null);
//                    }
//                    if (neibers[0][1] == "Missile")
//                    {
//
//                    }
//                    if (neibers[0][1] == "EOM" )
//                    {
//                        cells[x][y].redraw();
//                        this.setLinkedCell(null);
//                        cells[x][y].setLinkedObject(null);
//                        break;
//                    } else if (neibers[0][1] == "0") {
//
//                        cells[x][y].redraw();
//                        if (x-1 >= 0) changeLinkedCell(cells[x - 1][y]);
//                        continue;
//                    } else if (neibers[0][1] == "EnemyBase" || neibers[0][1] == "Player" || neibers[0][1] == "EnemyTank" || neibers[0][1] == "Wall") {
//                        cells[x - 1][y].getLinkedObject().setHp(getHp()-1);
//                        cells[x][y].redraw();
//                        this.setLinkedCell(null);
//                        cells[x][y].setLinkedObject(null);
//                        break;
//                    } else {
//                        cells[x][y].redraw();
//                        this.setLinkedCell(null);
//                        cells[x][y].setLinkedObject(null);
//                        break;
//                    }
//                }
//            case 'W':
//                setImage(MissileHorizontal);
//                setDirection('W');
//                while (true)
//                {
//                    if (neibers[1][0] == "EOM")
//                    {
//                        this.setHp(-1);
//                        break;
//                    } else if (neibers[1][0] == "0") {
//                        x = getLinkedCell().getTiledX();
//                        y = getLinkedCell().getTiledY();
//                        cells[x][y - 1].redraw();
//                        if (y - 1 >= 0) changeLinkedCell(cells[x][y - 1]);
//                        continue;
//                    } else if (neibers[1][0] == "EnemyBase" || neibers[1][0] == "Player" || neibers[1][0] == "EnemyTank" || neibers[1][0] == "Terrain") {
//                        cells[x][y-1].getLinkedObject().setHp(getHp()-1);
//                        this.setHp(-1);
//                    } else this.setHp(-1);
//                }
//            case 'E':
//                setImage(MissileHorizontal);
//                setDirection('E');
//                while (true)
//                {
//                    if (neibers[1][2] == "EOM")
//                    {
//                        this.setHp(-1);
//                        break;
//                    } else if (neibers[1][2] == "0") {
//                        x = getLinkedCell().getTiledX();
//                        y = getLinkedCell().getTiledY();
//                        if (y + 1 < size) changeLinkedCell(cells[x][y + 1]);
//                        this.linkedCell.redraw();
//                        continue;
//                    } else if (neibers[1][2] == "EnemyBase" || neibers[1][2] == "Player" || neibers[1][2] == "EnemyTank" || neibers[1][2] == "Wall") {
//                        cells[x][y + 1].getLinkedObject().setHp(getHp()-1);
//                        this.setHp(-1);
//                    } else this.setHp(-1);
//                }
//            case 'S':
//                setImage(MissileVertical);
//                setDirection('S');
//                while (true)
//                {
//                    if (neibers[2][1] == "EOM")
//                    {
//                        this.setHp(-1);
//                        break;
//                    } else if (neibers[2][1] == "0") {
//                        x = getLinkedCell().getTiledX();
//                        y = getLinkedCell().getTiledY();
//                        if (x + 1 < size) changeLinkedCell(cells[x + 1][y]);
//                        this.linkedCell.redraw();
//                        continue;
//                    } else if (neibers[2][1] == "EnemyBase" || neibers[2][1] == "Player" || neibers[2][1] == "EnemyTank" || neibers[2][1] == "Wall") {
//                        cells[x + 1][y].getLinkedObject().setHp(getHp()-1);
//                        this.setHp(-1);
//                    } else this.setHp(-1);
//                }
//            default:
//                stop();
//        }
//    }

    public Missile(String name, String image, int hp, boolean isDestructible, Cell linkedCell) {
        super(name, image, hp, isDestructible, linkedCell);
    }
}
