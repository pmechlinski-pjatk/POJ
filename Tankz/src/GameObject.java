public class GameObject {

    private String image;
    private int hp;

    Cell linkedCell;

    private boolean isDestructible;

    public GameObject(String image, int hp, boolean isDestructible, Cell linkedCell) {
        this.image = image;
        this.hp = hp;
        this.isDestructible = isDestructible;
        this.linkedCell = linkedCell;
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

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
