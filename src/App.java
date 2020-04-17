import java.util.Scanner;

public class App {

    private Map map;
    private Person pc;

    public static void main(String[] args) {
        App app = new App();
        app.initializeWorld("TEST3");
        Scanner input = new Scanner(System.in);
        String inString;
        boolean keepRunning = true;
//        try {
//            Thread.sleep(1000);
//            app.movePerson(app.getPc(), 'e');
//        } catch (InterruptedException e) {
//            System.out.println("EXCEPTION HAPPENED");
//            e.printStackTrace();
//            return;
//        }

        while (keepRunning) {
            inString = input.nextLine();
            if (inString.equals("q")) {
                keepRunning = false;
            } else if (inString.equals("l")) {
                app.getPc().look();
            } else if (inString.length() == 0) {
                continue;
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

    public boolean initializeWorld(String testType) {
        if ((this.getMap() == null) && (this.getPc() == null)) {
            this.setPc(new Person(0, 0, "PC", true, 10));
            Chunk core = new Chunk(null, null, null, null);
            if (testType == null) {
                // current default
                this.setMap(new Map(3, 3));
                this.getMap().placeChunk(core, 1, 1);
            } else if (testType.equals("TEST1") || testType.equals("TEST3")) {
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
            } else if (testType.equals("TEST2")) {
                this.setMap(new Map(7, 5));
                this.getMap().placeChunk(core, 1, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 2, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 3, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 4, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 5, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 5, 2);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 5, 3);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 6, 3);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 6, 4);
            }
            core.addPerson(this.getPc());
            if (testType == "TEST3") {
                this.getPc().getAttacks().add(new Attack("KICK", 4, 6, null, 0));
                this.getPc().getAttacks().add(new Attack("PUNCH", 1, 3, null, 0));
                Attack[] swordattacks = new Attack[2];
                swordattacks[0] = new Attack("SLASH", 2, 4, null, 0);
                swordattacks[1] = new Attack("STAB", 0, 5, null, 0);
                Weapon sword = new Weapon("SWORD", swordattacks);
                this.getMap().placeAt(sword, 7, 3);
                Enemy testEnemy = new Enemy(0, 0, "BIG DOG", 5, 50, 100);
                testEnemy.getAttacks().add(new Attack("BITE", 0, 2, null, 0));
                testEnemy.getAttacks().add(new Attack("SCRATCH", 1, 3, null, 0));
                //testEnemy.getAttacks().add(new Attack("DEVOUR", 10, 10, null));
                // add a better way to put things on the map
                core.getE().getE().getE().getE().getS().getS().getE().getE().getS().addPerson(testEnemy);
            }
            System.out.print(map.fullMap());
            System.out.print(map.cameraRender(7, 5));
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
        if (p.getChunk().getDir(dir) == null) {
            System.out.println("No chunk in that direction");
            return false;
        }
        Chunk chunkToEnter = curChunk.getDir(dir);
        p.removeChunk();
        chunkToEnter.addPerson(p);
        p.setDir(dir);
        System.out.print(this.getMap().cameraRender(7, 5));
        return true;
    }

}
