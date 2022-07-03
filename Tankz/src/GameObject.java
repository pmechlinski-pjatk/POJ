import static java.lang.Thread.sleep;
import static java.util.Objects.isNull;


public class GameObject {

    private String image;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public GameObject() {

    }

    public String getName() {
        return name;
    }

    private int hp;

    public boolean isObject() {
        return isObject;
    }

    private boolean isObject = true;

    private boolean isDestructible;

    public boolean isDestructible() {
        return isDestructible;
    }

    public void setDestructible(boolean destructible) {
        isDestructible = destructible;
    }

    Cell linkedCell;

    public Cell getLinkedCell() {
        return linkedCell;
    }

    public void setLinkedCell(Cell linkedCell) {
        this.linkedCell = linkedCell;
        if (!isNull(linkedCell)) linkedCell.setLinkedObject(this);
    }


    public GameObject(String name, String image, int hp, boolean isDestructible, Cell linkedCell) {
        this.image = image;
        this.hp = hp;
        this.isDestructible = isDestructible;
        this.linkedCell = linkedCell;
        this.name = name;
        linkedCell.setLinkedObject(this);
        linkedCell.redraw();
    }

    public int[] getCoordinates()
    {
        int[] coordinates = new int[1];
        coordinates[0] = linkedCell.getX();
        coordinates[1] = linkedCell.getY();
        return coordinates;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) throws InterruptedException {
        this.hp = hp;

        // Destroying walls
        if (this.getName() == "Terrain" && this.hp == 1) {
            this.setImage("<html><font color=#A52A2A>%##<br/>##E<br/>_)##</font></html>");
            this.getLinkedCell().redraw();
            return;
        } else if (this.getName() == "Terrain" && this.hp <= 0)
        {
            if (!isNull(getLinkedCell()))
            {
                this.getLinkedCell().setLinkedObject(null);
            }
            this.getLinkedCell().redraw();
            setLinkedCell(null);
        }

        // Destroying tanks and bases
        if ((this.getName() == "EnemyTank" || this.getName() == "EnemyBase" || this.getName() == "Player") && this.hp <= 0)
        {
            // Explosion sprite
            this.setImage("<html><font color='red'>__\\|/__<br>=@=<br>--/|\\--</font></html>");
            this.getLinkedCell().redraw();
            this.setImage(" ");


            // Unlink object
            if (!isNull(getLinkedCell()))
            {
                this.getLinkedCell().setLinkedObject(null);
            }

            // End sprite
            sleep(100);
            this.getLinkedCell().redraw();

            // Unlink cell
            //setLinkedCell(null);

        //if (!isNull(this.getLinkedCell())) this.setLinkedCell(null);
        this.isObject = false;

        if (!isNull(getLinkedCell()))this.getLinkedCell().redraw();
        if (this.hp <= 0 && this.isDestructible) {
            if (!isNull(linkedCell)) {
                linkedCell.setLinkedObject(null);
                linkedCell.redraw();
            }
        }

            //if (!isNull(this.getLinkedCell())) this.setLinkedCell(null);
            this.isObject = false;
        }

    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
