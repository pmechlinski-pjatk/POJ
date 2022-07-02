public class Player extends MovingObject {

    char direction = 'N';
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
public void tryAction(char k, Cell cells[][])
{
    if (k == 'l') shoot();
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

    public void shoot()
    {

    } // Player should be able to shoot at will / at clicking SPACE or something.


    }

