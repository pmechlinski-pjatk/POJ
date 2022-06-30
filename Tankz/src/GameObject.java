public class GameObject {

    private String image;
    private int hp;

    private boolean isDestructible;

    public GameObject(String image, int hp, boolean isDestructible) {
        this.image = image;
        this.hp = hp;
        this.isDestructible = isDestructible;
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
