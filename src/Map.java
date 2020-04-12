import java.util.ArrayList;

public class Map {

    private Chunk[][] chunks;
    private ArrayList<Person> people;
    private int xSize;
    private int ySize;

    public Map(int x, int y) {
        this.chunks = new Chunk[y][x];
        this.people = new ArrayList<>();
        this.xSize = x;
        this.ySize = y;
    }

    protected boolean placeChunk(Chunk c, int x, int y) {
        // check that location exists
        if ((x >= this.xSize) || y >= this.ySize) {
            System.out.println("Invalid coordinates");
            return false;
        }
        // check that chunk doesn't already exist
        if (this.getChunks()[y][x] != null) {
            System.out.println("Chunk already in map");
            return false;
        }
        // put chunk in chunk matrix and set chunk atttributes
        this.getChunks()[y][x] = c;
        c.setMap(this);
        c.setY(y);
        c.setX(x);
        // link chunk with nearby chunks
        // e
        if (x + 1 < this.xSize) {
            linkChunks(c, this.getChunks()[y][x+1], 'e');
        }
        // w
        if (x - 1 > 0) {
            linkChunks(c, this.getChunks()[y][x-1], 'w');
        }
        // n
        if (y + 1 < this.ySize) {
            linkChunks(c, this.getChunks()[y+1][x], 'n');
        }
        // s
        if (y - 1 > 0) {
            linkChunks(c, this.getChunks()[y-1][x], 's');
        }
        return true;
    }

    private void linkChunks(Chunk c, Chunk d, char dir) {
        // check that chunks are valid
        if ((c != null) && (d != null)) {
            System.out.println("LINKED CHUNKS: " + "[" + c.getX() + ", " + c.getY() + "], " + "[" + d.getX() + ", " + d.getY() + "]");
            // link d onto c in direction dir
            c.link(d, dir);
        }
    }

    private Chunk[][] getChunks() {
        return this.chunks;
    }

    protected ArrayList<Person> getPeople() {
        return this.people;
    }

    private boolean hasChunk(Chunk c) {
        for (int i = 0; i < this.ySize; i++) {
            for (int j = 0; j < this.xSize; j++) {
                if (this.getChunks()[i][j].equals(c)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean removeAt(int x, int y) {
        if ((x >= this.xSize) || y >= this.ySize) {
            System.out.println("Invalid coordinates");
            return false;
        }
        if (this.getChunks()[y][x] != null) {
            this.getChunks()[y][x] = null;
            return true;
        }
        return false;
    }

    private int[] findChunk(Chunk c) {
        for (int i = 0; i < this.ySize; i++) {
            for (int j = 0; j < this.xSize; j++) {
                if (this.getChunks()[i][j].equals(c)) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private boolean removeChunk(Chunk c) {
        if (!c.getPeople().isEmpty()) {
            System.out.println("This chunk contains a person");
            return false;
        }
        int[] loc = this.findChunk(c);
        if (loc != null) {
            this.getChunks()[loc[0]][loc[1]] = null;
        }
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

    public String toString() {
        String outStr = "";
        for (int i = 0; i < this.ySize; i++) {
            for (int j = 0; j < this.xSize; j++) {
                if (this.getChunks()[i][j] == null) {
                    // empty space
                    outStr = String.join("", outStr, "[ ]");
                } else {
                    // valid chunk
                    if (!this.getChunks()[i][j].getPeople().isEmpty()) {
                        // person
                        outStr = String.join("", outStr, "[C]");
                    } else {
                        // no person
                        outStr = String.join("", outStr, "[O]");
                    }
                }
            }
            outStr = String.join("", outStr, "\n");
        }
        return outStr;
    }

}
