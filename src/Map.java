import java.util.ArrayList;

public class Map {

    private ArrayList<Chunk> chunks;
    private ArrayList<Person> people;
    private boolean coreChunk;

    public Map() {
        this.chunks = new ArrayList<>();
        this.people = new ArrayList<>();
    }

    protected boolean placeChunk(Chunk c, boolean isCore) {
        if (this.getChunks().contains(c)) {
            System.out.println("Chunk already in map");
            return false;
        }
        this.getChunks().add(c);
        return true;
    }

    private ArrayList<Chunk> getChunks() {
        return this.chunks;
    }

    protected ArrayList<Person> getPeople() {
        return this.people;
    }

    private boolean removeChunk(Chunk c) {
        if (!c.getPeople().isEmpty()) {
            System.out.println("This chunk contains a person");
            return false;
        }
        if (!this.getChunks().contains(c)) {
            System.out.println("Chunk not on map");
            return false;
        }
        for (int i = 0; i < 4; i++) {
            c.removeChunk(i, true);
        }
        this.getChunks().remove(c);
        return true;
    }

    private boolean removePerson(Person p) {
        if (!this.getPeople().contains(p)) {
            System.out.println("No such person on map");
            return false;
        }
        p.getChunk().removePerson(p);
        p.removeChunk();
        return true;
    }

}
