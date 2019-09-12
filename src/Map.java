import java.util.ArrayList;

public class Map {

    private ArrayList<Chunk> chunks;
    private ArrayList<Person> people;
    private boolean coreChunk;

    public Map() {
        this.chunks = new ArrayList<>();
        this.people = new ArrayList<>();
    }

    private boolean placeChunk(Chunk c, boolean isCore) {
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

    private ArrayList<Person> getPeople() {
        return this.people;
    }

}
