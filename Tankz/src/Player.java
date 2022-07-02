public class Player extends MovingObject {


    public Player(String name, String image, int hp, boolean isDestructible, Cell linkedCell) {
        super(name, image, hp, isDestructible, linkedCell);
    }
public void tryAction(char k, Cell cells[][])
{
    if (k == 'l') shoot(this.getDirection());
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
              this.setDirection(Direction.NORTH);
              System.out.println(neibers[0][1]);
              if (neibers[0][1] == "0") changeLinkedCell(cells[x-1][y]);
              break;
          case 'a':
              this.setDirection(Direction.EAST);
              if (neibers[1][0] == "0") changeLinkedCell(cells[x][y-1]);
              break;
          case 'd':
              this.setDirection(Direction.WEST);
              if (neibers[1][2] == "0") changeLinkedCell(cells[x][y+1]);
              break;
          case 's':
              this.setDirection(Direction.SOUTH);
              if (neibers[2][1] == "0") changeLinkedCell(cells[x+1][y]);
              break;
          default:
              System.out.println("(?)Unknown control error.");
              break;
      }
    } // Should allow to move player's tank according to the keyboard keys pressed.

    public void shoot(Direction direction) {} // Player should be able to shoot at will / at clicking SPACE or something.
}
