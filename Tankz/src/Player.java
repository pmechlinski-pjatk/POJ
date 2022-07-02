import static java.lang.Thread.sleep;

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
    private final String southStandby = "<html><br/>[+]<br/>-|-</html>";
    private final String westStandby =  "<html><br/>--[+]<br/></html>";
    private final String eastStandby = "<html><br/>[+]--<br/></html>";
    private final String northReload =  "<html><font color='red'>|</font><br/>[+]<br/></html>";
    private final String southReload = "<html><br/>[+]<br/><font color='red'>|</font></html>";
    private final String westReload = "<html><br/><font color='red'>-</font>[+]<br/></html>";
    private final String eastReload = "<html><br/>[+]<font color='red'>-</font><br/></html>";

    public Player(String name, String image, int hp, boolean isDestructible, Cell linkedCell) {
        super(name, image, hp, isDestructible, linkedCell);
    }
public void tryAction(char k, Cell cells[][]) throws InterruptedException {
    if (k == 'l') shoot(cells);
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

    public void shoot(Cell[][] cells) throws InterruptedException {
        String neibers[][] = this.getNeibers();
        int x = this.getLinkedCell().getTiledX();
        int y = this.getLinkedCell().getTiledY();
        // CENTRAL SHOULD BE neibers[1][1]
        System.out.println(neibers[0][1]);
        System.out.println(getDirection());
        switch(getDirection()) {
            case 'N':
                if (neibers[0][1] == "0")
                {
                    setImage(northReload);
                    this.linkedCell.redraw();
                    Missile m = new Missile('N', cells[x-1][y], cells);
                }
                sleep(2000);
                setImage(northStandby);
                break;
            case 'E':
                if (neibers[1][0] == "0")
                {
                    setImage(westReload);
                    this.linkedCell.redraw();
                    Missile m = new Missile('E', cells[x][y-1], cells);
                }
                sleep(2000);
                setImage(westStandby);
                break;
            case 'S':
                if (neibers[1][2] == "0")
                {
                    setImage(eastReload);
                    this.linkedCell.redraw();
                    Missile m = new Missile('S', cells[x][y+1], cells);
                }
                sleep(2000);
                setImage(eastStandby);
                break;
            case 'W':
                if (neibers[2][1] == "0")
                {
                    setImage(southReload);
                    this.linkedCell.redraw();
                    Missile m = new Missile('W', cells[x+1][y], cells);
                }
                sleep(2000);
                setImage(southStandby);
                break;
            default:
                System.out.println("(?)Unknown exception at shooting.");
                break;
        }
    } // Player should be able to shoot at will / at clicking SPACE or something.



    }

