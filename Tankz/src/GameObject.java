public class GameObject {

    private String image;

    private String name;

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

    Cell linkedCell;

    public Cell getLinkedCell() {
        return linkedCell;
    }

    public void setLinkedCell(Cell linkedCell) {
        this.linkedCell = linkedCell;
        linkedCell.setLinkedObject(this);
    }

    private boolean isDestructible;

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

    public void setHp(int hp)
    {
        this.hp = hp;
        if (this.getImage().equals("<html><font color=#A52A2A>###<br/>###<br/>###</font></html>") && this.hp == 1) this.setImage("<html><font color=#A52A2A>%##<br/>##E<br/>_)##</font></html>");
        if (this.hp <= 0 && this.isDestructible)
        {
            linkedCell.setLinkedObject(null);
            linkedCell.redraw();
            this.setLinkedCell(null);
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
