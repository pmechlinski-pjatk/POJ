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


    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public void changeLinkedCell(Cell newCell) {
        linkedCell.setLinkedObject(null);
        int x = linkedCell.getTiledX();
        int y = linkedCell.getTiledY();
        linkedCell.redraw();
        this.linkedCell = newCell;
        newCell.setLinkedObject(this);
        newCell.redraw();

    }

    public void stop() {
        this.setDirection('0');
        while (true) {
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Missile(char direction, Cell startingTile, Cell[][] cells, int size) {
        super();
        this.setDirection(direction);
        this.setLinkedCell(startingTile);
        int x = startingTile.getTiledX();
        int y = startingTile.getTiledY();
        cells[x][y].setLinkedObject(this);
        this.setLinkedCell(cells[x][y]);
        this.setImage("*");
        this.linkedCell.redraw();
        new Thread(() -> {
            while (true) {
                if (this.getHp() != -1) {
                    this.missileMove(cells, size);

                    try {
                        sleep(1500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else break;
            }
        }).start();
    }

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

    public void moveInLoop(char direction, String neiber, Cell[][] cells, int size) {
        int x = this.getLinkedCell().getTiledX();
        int y = this.getLinkedCell().getTiledY();

        if (isNull(neiber)) {
            stop();
            return;
        }

        switch (neiber) {
            case "0": // Can move freely
                if (!isNull(getCoordCell(direction, cells))) {
                    this.setLinkedCell(getCoordCell(direction, cells));
                    getCoordCell(direction, cells).setLinkedObject(this);
                    cells[x][y].setLinkedObject(null);
                }
                return;
            case "Missile": // Two missiles should cancel each other
                if (!isNull(getCoordCell(direction, cells))) {
                    getLinkedCell().setLinkedObject(null);
                    this.setLinkedCell(null);
                    getCoordCell(direction, cells).setLinkedObject(null);
                    stop();
                }
            case "EOM": // End of map
                if (!isNull(getCoordCell(direction, cells))) {
                    getLinkedCell().setLinkedObject(null);
                    this.setLinkedCell(null);
                    stop();
                }
            case "Destructible": // Deal damage before disappearing
                if (!isNull(getCoordCell(direction, cells))) {
                    getLinkedCell().setLinkedObject(null);
                    this.setLinkedCell(null);
                    getCoordCell(direction, cells).getLinkedObject().setHp(getHp() - 1);
                    stop();
                }
            default:
                stop();
                return;
        }
    }

    public void controlLoop(char direction, String[][] neibers, Cell[][] cells, int size) {
        if (direction == 'N' || direction == 'S') setImage(MissileVertical);
        else if (direction == 'E' || direction == 'W') setImage(MissileHorizontal);
        else {
            setImage(" ");
            stop();
        }

        int x = this.getLinkedCell().getTiledX();
        int y = this.getLinkedCell().getTiledY();

        String testNeiber;

        while (!isNull(getDirection()) && getDirection() != '0') {
            testNeiber = getTestNeiber(direction);
            moveInLoop(direction, testNeiber, cells, size);
        }
        stop();
    }

    public void missileMove(Cell[][] cells, int size) {
        if (this.getLinkedCell() == null) stop();
        String neibers[][] = this.getNeibers();
        int x = this.getLinkedCell().getTiledX();
        int y = this.getLinkedCell().getTiledY();

        while (!isNull(getDirection()) && getDirection() != '0') {
            controlLoop(this.getDirection(), this.getNeibers(), cells, size);
            // TODO: Check, if I can get along without 'size' variable  - maybe isNotNull will be enough?
        }
        stop();
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