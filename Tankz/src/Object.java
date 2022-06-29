public class Object {
    //
    private int positionX;
    private int positionY;

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    private String ascii;

    private int hpMax; // Set to '-1' to make an object indestructible.
    private int hpCurrent;

}
