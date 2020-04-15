import java.util.ArrayList;

public class Chunk {

    private Chunk e;
    private Chunk w;
    private Chunk n;
    private Chunk s;
    private ArrayList<Person> people;
    private boolean full;
    private int x;
    private int y;
    private boolean coreChunk;

    public void setE(Chunk e) {
        this.e = e;
    }

    public void setW(Chunk w) {
        this.w = w;
    }

    public void setN(Chunk n) {
        this.n = n;
    }

    public void setS(Chunk s) {
        this.s = s;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public Chunk getE() {
        return e;
    }

    public Chunk getW() {
        return w;
    }

    public Chunk getN() {
        return n;
    }

    public Chunk getS() {
        return s;
    }

    public boolean isCoreChunk() {
        return coreChunk;
    }

    public Map getMap() {
        return map;
    }

    private Map map;

    public Chunk(Chunk n, Chunk e, Chunk s, Chunk w) {
        this.e = null;
        this.w = null;
        this.n = null;
        this.s = null;
        this.people = new ArrayList<>();
        this.full = false;
        this.x = 0;
        this.y = 0;
        this.coreChunk = false;
        this.map = null;
    }

    protected ArrayList getPeople() {
        return this.people;
    }

    protected int getX() {
        return this.x;
    }

    protected int getY() {
        return this.y;
    }

    protected boolean isFull() {
        return this.full;
    }

    protected boolean getCoreChunk() {
        return this.coreChunk;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    protected void setCoreChunk(boolean isCore) {
        this.coreChunk = isCore;
    }

    protected Chunk getDir(char dir) {
        if (dir == 'e') {
            return this.getE();
        } else if (dir == 'w') {
            return this.getW();
        } else if (dir == 'n') {
            return this.getN();
        } else if (dir == 's') {
            return this.getS();
        }
        System.out.println("Invalid direction");
        return null;
    }

    protected boolean addPerson(Person p) {
        if (!this.isFull()) {
            this.getPeople().add(p);
            p.setX(this.getX());
            p.setY(this.getY());
            p.setChunk(this);
            this.getMap().getPeople().add(p);
            ArrayList<Person> enemies = new ArrayList<>();
            if (p.getPC()) {
                // if pc is moving onto chunk check for enemies
                for (Object q:this.getPeople()) {
                    if (q instanceof Enemy) {
                        enemies.add((Person) q);
                    }
                }
                if (enemies.size() > 0) {
                    // if enemies are present, fight them
                    enemies.add(p);
                    this.getMap().startBattle(enemies);
                }
            } else if (p instanceof Enemy) {
                // if enemy is moving onto chunk check for pc
                for (Object q:this.getPeople()) {
                    if (((Person) q).getPC()) {
                        // if pc is present, fight them
                        enemies.add((Person) q);
                        enemies.add(p);
                        this.getMap().startBattle(enemies);
                    }
                }
            }
            return true;
        }
        System.out.println("Chunk is full");
        return false;
    }

    protected void link(Chunk c, char dir) {
        if (dir == 'e') {
            c.setW(this);
            this.setE(c);
        } else if (dir == 'w') {
            c.setE(this);
            this.setW(c);
        } else if (dir == 'n') {
            c.setS(this);
            this.setN(c);
        } else if (dir == 's') {
            c.setN(this);
            this.setS(c);
        }
    }

    protected void wall(char dir) {
        // TODO
    }

    protected boolean removePerson(Person p) {
        if (this.getPeople().contains(p)) {
            this.getMap().removePerson(p);
            this.getPeople().remove(p);
            return true;
        }
        System.out.println("Person not found");
        return false;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    protected void startBattle(ArrayList<Person> people) {

    }

}
