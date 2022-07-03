import static java.lang.Thread.sleep;
import static java.util.Objects.isNull;
public class Player extends MovingObject {
    public Player(String name, String image, int hp, boolean isDestructible, Cell linkedCell) {
        super(name, image, hp, isDestructible, linkedCell);
    }
    private char direction = 'N';
    private boolean isReloading;

    //Player's sprites
    private final String northStandby = "<html>_|_<br/>[+]<br/></html>";
    private final String southStandby = "<html><br/>[+]<br/>`|`</html>";
    private final String westStandby =  "<html><br/>--[+]<br/></html>";
    private final String eastStandby = "<html><br/>[+]--<br/></html>";
    private final String northReload =  "<html>_<font color='red'>|</font>_<br/>[+]<br/></html>";
    private final String southReload = "<html><br/>[+]<br/>'<font color='red'>|</font>'</html>";
    private final String westReload = "<html><br/><font color='red'>--</font>[+]<br/></html>";
    private final String eastReload = "<html><br/>[+]<font color='red'>--</font><br/></html>";

    // Getters & Setters
    @Override
    public char getDirection() {
        return direction;
    }
    @Override
    public void setDirection(char direction) {
        this.direction = direction;
    }

    //      (Standby's are defaulted in MovingObject)
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

    // Additional util methods




    // Control functions
    //      Test for which action there's need.
    public int tryAction(char k, Cell cells[][], int size) throws InterruptedException
    {
        if (isNull(k)) return -1;
        else if (k == 'l') return 2;
        else if (k != ' ') return 1;
        else return 0;
    }

    //      Initalize action in regards to key clicked.
    public void Action(char k, Cell cells[][], int size) throws InterruptedException
    {
        if (k == 'l') shoot(cells, size);
        else if (k != ' ') tryMove(k, cells);
    }

    //      Move if possible in a given direction.
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

//
//
//
//
//    public boolean isReloading() {
//        return isReloading;
//    }
//
//    public void setReloading(boolean reloading) {
//        isReloading = reloading;
//    }
