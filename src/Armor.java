public class Armor extends Item {

    int def;
    String special;

    public Armor(String name, int def, String special) {
        super(name);
        this.def = def;
        this.special = special;
        this.setEquippable(true);
    }
}
