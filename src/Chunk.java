import java.util.ArrayList;

public class Chunk {

    private Chunk[] chunks;
    private ArrayList<Person> people;
    private boolean full;
    private int x;
    private int y;
    private boolean coreChunk;

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
        this.coreChunk = false;
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

    protected boolean addPerson(Person p) {
        if (!this.isFull()) {
            this.getPeople().add(p);
            p.setX(this.getX());
            p.setY(this.getY());
            p.setChunk(this);
            return true;
        }
        System.out.println("Chunk is full");
        return false;
    }

    protected boolean removePerson(Person p) {
        if (this.getPeople().contains(p)) {
            this.getPeople().remove(p);
            return true;
        }
        System.out.println("Person not found");
        return false;
    }

    protected boolean addChunk(Chunk c, int direction, boolean base) {
        // finds direction for other chunk
        int opposite = 0;
        int dx = 0;
        int dy = 0;

        // 0 is n, 1 is e, 2 is s, 3 is w
        if (direction == 0) {
            opposite = 2;
            dy = 1;
            // dx remains the same
        } else if (direction == 1) {
            opposite = 3;
            dx = 1;
            // dy remains the same
        } else if (direction == 2) {
            // opposite of direction 2 is already 0
            dy = -1;
            // dx remains the same
        } else if (direction == 3) {
            opposite = 1;
            dx = -1;
            // dy remains the same
        }

        if (this.getChunks()[direction] == null) {  // if base chunk has a free slot
            if (base) { // if this chunk is the one being appended
                // adds this chunk to other chunk
                c.addChunk(this, opposite, false);
                c.setX(this.getX() + dx);
                c.setY(this.getY() + dy);
            }
            this.getChunks()[direction] = c; // set chunk
            return true;
        }
        System.out.println("Chunk in the way");
        return false;
    }

    protected boolean removeChunk(int direction, boolean base) {
        int opposite = 0;

        // 0 is n, 1 is e, 2 is s, 3 is w
        if (direction == 0) {
            opposite = 2;
        } else if (direction == 1) {
            opposite = 3;
        } else if (direction == 3) {
            opposite = 1;
        }
        // else if direction == 2, opposite default of 0

        if (this.getChunks()[direction] == null) {
            System.out.println("There's no chunk in that direction");
            if (!base) {
                System.out.println("Other chunk wasn't linked with base chunk");
            }
            return false;
        }

        if (base) {
            Chunk otherChunk = this.getChunks()[direction];
            otherChunk.removeChunk(opposite, false);
        }

        this.getChunks()[direction] = null;
        return true;

    }

}
