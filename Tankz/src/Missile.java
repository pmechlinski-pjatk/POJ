import static java.lang.Thread.sleep;

public class Missile extends MovingObject {

    // A missile should have it's starting direction (and coordinates as every gameObject), should at least id another tile on it's track
    // and eventually should id collision and deal damage (or not if inapplicable)

   private String MissileHorizontal = "<html><font color='orange'>~</font></html>";
   private String MissileVertical = "<html><font color='orange'>\"</font></html>";

    public Missile(char direction, Cell startingTile, Cell[][] cells)
    {
        super();
        this.setDirection(direction);
        this.setLinkedCell(startingTile);
        int x = startingTile.getTiledX();
        int y = startingTile.getTiledY();
        cells[x][y].setLinkedObject(this);
        this.setLinkedCell(cells[x][y]);
        this.linkedCell.redraw();
        new Thread(() -> {
            while (true) {
                this.missileMove(cells);
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void missileMove(Cell[][] cells)
    {
        String neibers[][] = this.getNeibers();
        int x = this.getLinkedCell().getTiledX();
        int y = this.getLinkedCell().getTiledY();

        switch (getDirection())
        {
            case 'W':
                setImage(MissileVertical);
                setDirection('N');
                while (true)
                {
                    if (neibers[0][1] == "0") {
                        changeLinkedCell(cells[x - 1][y]);
                        this.linkedCell.redraw();
                        continue;
                    } else if (neibers[0][1] == "EnemyBase" || neibers[0][1] == "Player" || neibers[0][1] == "EnemyTank" || neibers[0][1] == "Wall") {
                        cells[x - 1][y].getLinkedObject().setHp(getHp()-1);
                        this.setHp(-1);
                    } else this.setHp(-1);
                }
            case 'A':
                setImage(MissileHorizontal);
                setDirection('S');
                while (true)
                {
                    if (neibers[1][0] == "0") {
                        changeLinkedCell(cells[x][y-1]);
                        this.linkedCell.redraw();
                        continue;
                    } else if (neibers[1][0] == "EnemyBase" || neibers[1][0] == "Player" || neibers[1][0] == "EnemyTank" || neibers[1][0] == "Wall") {
                        cells[x][y-1].getLinkedObject().setHp(getHp()-1);
                        this.setHp(-1);
                    } else this.setHp(-1);
                }
            case 'D':
                setImage(MissileHorizontal);
                setDirection('E');
                while (true)
                {
                    if (neibers[1][2] == "0") {
                        changeLinkedCell(cells[x - 1][y]);
                        this.linkedCell.redraw();
                        continue;
                    } else if (neibers[1][2] == "EnemyBase" || neibers[1][2] == "Player" || neibers[1][2] == "EnemyTank" || neibers[1][2] == "Wall") {
                        cells[x][y + 1].getLinkedObject().setHp(getHp()-1);
                        this.setHp(-1);
                    } else this.setHp(-1);
                }
            case 'S':
                setImage(MissileVertical);
                setDirection('S');
                while (true)
                {
                    if (neibers[2][1] == "0") {
                        changeLinkedCell(cells[x - 1][y]);
                        this.linkedCell.redraw();
                        continue;
                    } else if (neibers[2][1] == "EnemyBase" || neibers[2][1] == "Player" || neibers[2][1] == "EnemyTank" || neibers[2][1] == "Wall") {
                        cells[x + 1][y].getLinkedObject().setHp(getHp()-1);
                        this.setHp(-1);
                    } else this.setHp(-1);
                }
            default:
                this.setHp(-1);
        }
    }
    public Missile(String name, String image, int hp, boolean isDestructible, Cell linkedCell) {
        super(name, image, hp, isDestructible, linkedCell);
    }

    public void move() {}
}
