public class Item {

    private String name;
    private String category;
    private boolean equippable;
    private boolean equipped;
    private String desc;

    public void setEquippable(boolean equippable) {
        this.equippable = equippable;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isEquippable() {
        return equippable;
    }

    public Item(String name) {
        this.name = name;
        this.category = null;
        this.desc = "default description";
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
