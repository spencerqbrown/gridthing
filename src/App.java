import java.util.Scanner;

public class App {

    private Map map;
    private Person pc;

    public static void main(String[] args) {
        App app = new App();
        app.initializeWorld("TEST1");
        Scanner input = new Scanner(System.in);
        String inString;
        boolean keepRunning = true;
        while (keepRunning) {
            inString = input.nextLine();
            if (inString.equals("q")) {
                keepRunning = false;
            } else {
                app.movePerson(app.getPc(), inString.charAt(0));
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

    public boolean initializeWorld(String mapType) {
        if ((this.getMap() == null) && (this.getPc() == null)) {
            this.setPc(new Person(0, 0, "PC", true));
            Chunk core = new Chunk(null, null, null, null);
            if (mapType == null) {
                // current default
                this.setMap(new Map(3, 3));
                this.getMap().placeChunk(core, 1, 1);
            } else if (mapType.equals("TEST1")) {
                this.setMap(new Map(10, 10));
                this.getMap().placeChunk(core, 1, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 2, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 3, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 4, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 5, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 5, 2);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 5, 3);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 6, 3);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 6, 4);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 7, 3);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 7, 4);
            }
            core.addPerson(this.getPc());
            System.out.println(map.toString());
            return true;
        }
        System.out.println("World already initialized");
        return false;
    }

    public boolean movePerson(Person p, char dir) {
        if (!this.getMap().getPeople().contains(p)) {
            System.out.println("This person is not in map");
            return false;
        }
        Chunk curChunk = p.getChunk();
        System.out.println(dir);
        if (p.getChunk().getDir(dir) == null) {
            System.out.println("No chunk in that direction");
            return false;
        }
        Chunk chunkToEnter = curChunk.getDir(dir);
        p.removeChunk();
        chunkToEnter.addPerson(p);
        p.setChunk(chunkToEnter);
        System.out.println(this.getMap().toString());
        return true;
    }

}
