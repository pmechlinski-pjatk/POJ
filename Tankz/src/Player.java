import static java.lang.Thread.sleep;
import static java.util.Objects.isNull;
public class Player extends MovingObject {

    @Override
    public char getDirection() {
        return direction;
    }

    @Override
    public void setDirection(char direction) {
        this.direction = direction;
    }

    private char direction = 'N';
    private boolean isReloading;

    public boolean isReloading() {
        return isReloading;
    }

    public void setReloading(boolean reloading) {
        isReloading = reloading;
    }

    private final String northStandby = "<html>_|_<br/>[+]<br/></html>";
    private final String southStandby = "<html><br/>[+]<br/>`|`</html>";
    private final String westStandby =  "<html><br/>--[+]<br/></html>";
    private final String eastStandby = "<html><br/>[+]--<br/></html>";
    private final String northReload =  "<html>_<font color='red'>|</font>_<br/>[+]<br/></html>";
    private final String southReload = "<html><br/>[+]<br/>'<font color='red'>|</font>'</html>";
    private final String westReload = "<html><br/><font color='red'>--</font>[+]<br/></html>";
    private final String eastReload = "<html><br/>[+]<font color='red'>--</font><br/></html>";

    public Player(String name, String image, int hp, boolean isDestructible, Cell linkedCell) {
        super(name, image, hp, isDestructible, linkedCell);
    }
public int tryAction(char k, Cell cells[][], int size) throws InterruptedException
{
    if (isNull(k)) return -1;
    else if (k == 'l') return 2;
    else if (k != ' ') return 1;
    else return 0;
}

public void Action(char k, Cell cells[][], int size) throws InterruptedException
{
    if (k == 'l') shoot(cells, size);
    else if (k != ' ') tryMove(k, cells);
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
    } // Should allow to move player's tank according to the keyboard keys pressed.
    private void shootAtDir(Cell[][] cells, int size, int relX, int relY, char direction) throws InterruptedException
    {
        setImage(getReloadImage(direction));
        this.linkedCell.redraw();
        Missile m = new Missile(direction, cells[relX][relY], cells, size);
        sleep(2000);
        setImage(getStandbyImage(direction));
    }

    private int getRelX(int x, char d)
    {
    switch (d)
        {
            case 'N':
                return x-1;
            case 'S':
                return x+1;
            default:
                return 0;
        }
    }

    private int getRelY(int y, char d)
    {
    switch (d)
        {
            case 'W':
                return y-1;
            case 'E':
                return y+1;
            default:
                return 0;
        }
    }

    public String getNorthReload() {
        return northReload;
    }

    public String getSouthReload() {
        return southReload;
    }

    public String getWestReload() {
        return westReload;
    }

    public String getEastReload() {
        return eastReload;
    }

    private String getReloadImage(char d)
    {
        switch (d)
        {
            case 'N':
                return getNorthReload();
            case 'E':
                return getEastReload();
            case 'W':
                return getWestReload();
            case 'S':
                return getSouthReload();
        }
        return "WTF";
    }

    private String getStandbyImage(char d)
    {
        switch (d)
        {
            case 'N':
                return getNorthStandby();
            case 'E':
                return getEastStandby();
            case 'W':
                return getWestStandby();
            case 'S':
                return getSouthStandby();
        }
        return "WTF";
    }

    public void shoot(Cell[][] cells, int size) throws InterruptedException
    {
        String neibers[][] = this.getNeibers();
        int x = this.getLinkedCell().getTiledX();
        int y = this.getLinkedCell().getTiledY();
        char direction = getDirection();
        int relX = getRelX(x, direction);
        int relY = getRelY(y, direction);
        // CENTRAL SHOULD BE neibers[1][1]
        System.out.println("Shoot() initalized:");
        System.out.println("Next tile code: "+neibers[0][1]);
        System.out.println("Current direction: "+getDirection());
        shootAtDir(cells, size, relX, relY, direction);
    } // Player should be able to shoot at will / at clicking SPACE or something.

}

//        switch(getDirection()) {
//                case 'N':
//                if (neibers[0][1] == "0")
//                {
//                setImage(northReload);
//                this.linkedCell.redraw();
//                try
//                {
//                new Thread(() -> {
//                Missile m = new Missile('N', cells[x - 1][y], cells, size);
//                }).start();
//                } catch (IndexOutOfBoundsException e) { return; }
//                }
//                sleep(2000);
//                setImage(northStandby);
//                break;
//                case 'E':
//                if (neibers[1][0] == "0")
//                {
//                setImage(eastReload);
//                this.linkedCell.redraw();
//                new Thread(() -> {
//                Missile m = new Missile('E', cells[x][y+1], cells, size);
//                }).start();
//                }
//                sleep(2000);
//                setImage(eastStandby);
//                break;
//                case 'S':
//                if (neibers[1][2] == "0")
//                {
//                setImage(southReload);
//                this.linkedCell.redraw();
//                new Thread(() -> {
//                Missile m = new Missile('S', cells[x+1][y], cells, size);
//                }).start();
//                }
//                sleep(2000);
//                setImage(southStandby);
//                break;
//                case 'W':
//                if (neibers[0][1] == "0")
//                {
//                setImage(westReload);
//                this.linkedCell.redraw();
//                Missile m = new Missile('W', cells[x][y-1], cells, size);
//                sleep(2000);
//                setImage(westStandby);
//                }
//
//                break;
//default:
//        System.out.println("(?)Unknown exception at shooting.");
//        break;
//        }

