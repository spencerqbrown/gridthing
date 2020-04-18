public class Weapon extends Item {

    private Attack[] attacks;

    public Weapon(String name, Attack[] attacks) {
        super(name);
        this.attacks = attacks;
        this.setCategory("Weapon");
        this.setEquippable(true);
    }

    public Attack[] getAttacks() {
        return attacks;
    }
}
