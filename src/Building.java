public class Building {

    private String name;
    private boolean locked;

    public Building(String name, boolean locked) {
        this.name = name;
        this.locked = locked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }
}
