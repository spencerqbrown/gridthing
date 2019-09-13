import java.util.Scanner;

public class App {

    private Map map;
    private Person pc;

    public static void main(String[] args) {
        App app = new App();
        app.initializeWorld();
        Scanner input = new Scanner(System.in);
        String inString = "";
        // TODO this doesn't work, need to do a multithreading thing
        while (inString != "q") {
            inString = input.next();
            if (inString == "q") {
                break;
            }
            if (inString == "n") {
                app.movePerson(app.getPc(), 0);
            }
            if (inString == "e") {
                app.movePerson(app.getPc(), 1);
            }
            if (inString == "s") {
                app.movePerson(app.getPc(), 2);
            }
            if (inString == "w") {
                app.movePerson(app.getPc(), 3);
            }
        }
    }

    protected Map getMap() {
        return this.map;
    }

    protected Person getPc() {
        return this.pc;
    }

    protected void setMap(Map m) {
        this.map = m;
    }

    protected void setPc (Person pc) {
        this.pc = pc;
    }

    public boolean initializeWorld() {
        if ((this.getMap() == null) && (this.getPc() == null)) {
            this.setMap(new Map());
            this.setPc(new Person(0, 0, "PC", true));
            Chunk core = new Chunk(null, null, null, null);
            this.getMap().placeChunk(core, true);
            core.addPerson(this.getPc());
            return true;
        }
        System.out.println("World already initialized");
        return false;
    }

    public boolean movePerson(Person p, int direction) {
        if (!this.getMap().getPeople().contains(p)) {
            System.out.println("This person is not in map");
            return false;
        }
        Chunk curChunk = p.getChunk();
        if (p.getChunk().getChunks()[direction] == null) {
            System.out.println("No chunk in that direction");
            return false;
        }
        Chunk chunkToEnter = curChunk.getChunks()[direction];
        p.removeChunk();
        chunkToEnter.addPerson(p);
        return true;
    }

}
