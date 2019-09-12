import java.util.ArrayList;

public class Chunk {

    private Chunk[] chunks;
    private ArrayList<Person> people;
    private boolean full;
    private int x;
    private int y;

    public Chunk(Chunk n, Chunk e, Chunk s, Chunk w) {
        this.chunks = new Chunk[4];
        this.chunks[0] = n;
        this.chunks[1] = e;
        this.chunks[2] = s;
        this.chunks[3] = w;
        this.people = new ArrayList<>();
        this.full = false;
        this.x = 0;
        this.y = 0;
    }

    protected Chunk[] getChunks() {
        return this.chunks;
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

    //TODO
    // write setters

    protected boolean addPerson(Person p) {
        if (!this.full) {
            people.add(p);
            return true;
        }
        System.out.println("Chunk is full");
        return false;
    }

    protected boolean removePerson(Person p) {
        if (people.contains(p)) {
            people.remove(p);
            return true;
        }
        System.out.println("Person not found");
        return false;
    }

    protected boolean addChunk(Chunk c, int direction, boolean base) {
        // finds direction for other chunk
        int opposite = 0;
        if (direction == 0) {
            opposite = 2;
        }
        if (direction == 1) {
            opposite = 3;
        }
        if (direction == 2) {
            opposite = 0;
        }
        if (direction == 3) {
            opposite = 1;
        }

        if ((this.chunks[direction] == null) && (c.getChunks[opposite] == null)) {  // if chunks are free
            this.chunks[direction] = c; // set chunk
            if (base) { // if this chunk is the one being appended
                // adds this chunk to other chunk
                c.addChunk(this, opposite, false);
            }
            return true;
        }
        System.out.println("Chunk in the way");
        return false;

    }


}
