import java.util.ArrayList;
import java.util.HashMap;

public class Map {

    private Chunk[][] chunks;
    private ArrayList<Person> people;
    private int xSize;
    private int ySize;
    private HashMap<String, String> mapIcons;

    public Map(int x, int y) {
        this.chunks = new Chunk[y][x];
        this.people = new ArrayList<>();
        this.xSize = x;
        this.ySize = y;
        this.mapIcons = new HashMap<>();
        this.mapIcons.put("chunk0", "[ ]");
        this.mapIcons.put("chunk1", "[O]");
        this.mapIcons.put("pc", "[P]");
        this.mapIcons.put("pcs", "[v]");
        this.mapIcons.put("pcn", "[^]");
        this.mapIcons.put("pce", "[>]");
        this.mapIcons.put("pcw", "[<]");
        this.mapIcons.put("enemy", "[E]");
        this.mapIcons.put("npc", "[C]");
        this.mapIcons.put("border", "[B]");
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
        // s
        if (y + 1 < this.ySize) {
            linkChunks(c, this.getChunks()[y+1][x], 's');
        }
        // n
        if (y - 1 > 0) {
            linkChunks(c, this.getChunks()[y-1][x], 'n');
        }
        return true;
    }

    private void linkChunks(Chunk c, Chunk d, char dir) {
        // check that chunks are valid
        if ((c != null) && (d != null)) {
            //System.out.println("LINKED CHUNKS: " + "[" + c.getX() + ", " + c.getY() + "], " + "[" + d.getX() + ", " + d.getY() + "]");
            // link d onto c in direction dir
            c.link(d, dir);
        }
    }

    public boolean placeAt(Object o, int x, int y) {
        Chunk chunk = this.chunkAt(x, y);
        if (chunk == null) {
            System.out.println("No such chunk");
            return false;
        } else {
            if (o instanceof Item) {
                // THIS MAY NOT WORK
                chunk.getItems().add((Item) o);
            } else if (o instanceof Person) {
                // THIS MAY NOT WORK
                chunk.addPerson((Person) o);
            } else if (o instanceof Building) {
                chunk.setBuilding((Building) o);
            }
        }
        return true;
    }

    private Chunk[][] getChunks() {
        return this.chunks;
    }

    private Chunk chunkAt(int x, int y) {
        if ((x >= this.xSize) || (y >= this.ySize) || (y < 0) || (x < 0)) {
            System.out.println("Out of bounds");
            return null;
        }
        Chunk chunk = this.getChunks()[y][x];
        return chunk;
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

    protected boolean removePerson(Person p) {
        if (!this.getPeople().contains(p)) {
            System.out.println("No such person on map");
            return false;
        }
        this.getPeople().remove(p);
        return true;
    }

    public String toString() {
        String outStr = "";
        for (int i = 0; i < this.ySize; i++) {
            for (int j = 0; j < this.xSize; j++) {
                if (this.getChunks()[i][j] == null) {
                    // empty space
                    outStr = String.join("", outStr, this.mapIcons.get("chunk0"));
                } else {
                    // valid chunk
                    if (!this.getChunks()[i][j].getPeople().isEmpty()) {
                        for (Object per:this.getChunks()[i][j].getPeople()) {
                            if (((Person) per).getPC()) {
                                // pc
                                outStr = String.join("", outStr, this.mapIcons.get("pc" + ((Person) per).getDir()));
                            } else if (per instanceof Enemy) {
                                // enemy
                                outStr = String.join("", outStr, this.mapIcons.get("enemy"));
                            } else {
                                // npc
                                outStr = String.join("", outStr, this.mapIcons.get("npc"));
                            }
                        }

                    } else {
                        // no person
                        outStr = String.join("", outStr, this.mapIcons.get("chunk1"));
                    }
                }
            }
            outStr = String.join("", outStr, "\n");
        }
        return outStr;
    }

    public String fullMap() {
        return cameraRender(this.xSize, this.ySize);
    }

    public String cameraRender(int xlim, int ylim) {
        for (Person p:this.getPeople()) {
            if (p.getPC()) {
                // render map relative to pc
                int x = p.getX();
                int y = p.getY();
                int xwest = xlim / 2;
                int xeast = xlim / 2;
                // odd xlim and ylim will have a space for pc, even it must be taken out of xwest and ynorth
                // this way pc will be slightly flushed up left
                // this is not ideal so xlim and ylim should be odd and pc is centered
                if (xlim % 2 == 0) {
                    xwest--;
                }
                int ysouth = ylim / 2;
                int ynorth = ylim / 2;
                if (ylim % 2 == 0) {
                    ynorth--;
                }

                // get edges
                // east and west
                int xeastedge = x + xeast;
                int xwestedge = x - xwest;
                if (xwestedge < 0) {
                    if ((xeastedge + Math.abs(xwestedge)) < this.xSize) {
                        xeastedge = xeastedge + Math.abs(xwestedge); // shift east edge east
                        xwestedge = 0; // flush west edge to map west border
                    } else {
                        // if map is too small for camera view
                        xeastedge = this.xSize - 1;
                        xwestedge = 0;
                    }
                }
                if (xeastedge >= this.xSize) {
                    if ((xwestedge - (xeastedge - this.xSize + 1) >= 0)) {
                        xwestedge = xwestedge - (xeastedge - this.xSize + 1); // shift west edge west
                        xeastedge = this.xSize - 1; // flush east edge to map east border
                    } else {
                        // if map is too small for camera view, put edges at map side borders
                        xwestedge = 0;
                        xeastedge = this.xSize - 1;
                    }
                }

                // north and south
                int ynorthedge = y - ynorth;
                int ysouthedge = y + ysouth;
                if (ynorthedge < 0) {
                    if ((ysouthedge + Math.abs(ynorthedge)) < this.ySize) {
                        ysouthedge = ysouthedge + Math.abs(ynorthedge); // shift north edge north
                        ynorthedge = 0; // flush north edge to map north border
                    } else {
                        // if map is too small for camera view
                        ysouthedge = this.ySize - 1;
                        ynorthedge = 0;
                    }
                }
                if (ysouthedge >= this.ySize) {
                    if ((ysouthedge - (this.ySize - ynorthedge + 1) >= 0)) {
                        ynorthedge = ysouthedge - (this.ySize - ynorthedge + 1); // shift north edge north
                        ysouthedge = this.ySize - 1; // flush south edge to map south border
                    } else {
                        // if map is too small for camera view, put edges at map side borders
                        ynorthedge = 0;
                        ysouthedge = this.ySize - 1;
                    }
                }

                // actual render
                String outStr = "";
                for (int i = ynorthedge; i <= ysouthedge; i++) {
                    for (int j = xwestedge; j <= xeastedge; j++) {
                        if (this.getChunks()[i][j] == null) {
                            // empty space
                            outStr = String.join("", outStr, this.mapIcons.get("chunk0"));
                        } else {
                            // valid chunk
                            if (!this.getChunks()[i][j].getPeople().isEmpty()) {
                                for (Object per:this.getChunks()[i][j].getPeople()) {
                                    if (((Person) per).getPC()) {
                                        // pc
                                        outStr = String.join("", outStr, this.mapIcons.get("pc" + ((Person) per).getDir()));
                                    } else if (per instanceof Enemy) {
                                        // enemy
                                        outStr = String.join("", outStr, this.mapIcons.get("enemy"));
                                    } else {
                                        // npc
                                        outStr = String.join("", outStr, this.mapIcons.get("npc"));
                                    }
                                }

                            } else {
                                // no person
                                outStr = String.join("", outStr, this.mapIcons.get("chunk1"));
                            }
                        }
                    }
                    outStr = String.join("", outStr, "\n");
                }
                return outStr;
            }
        }
        return this.toString();
    }

    public void startBattle(ArrayList<Person> enemies) {
        // first is arbitrary right now
        boolean runningBattle = true;
        Person currentPerson;
        Attack currentAttack;
        Person currentTarget;
        ArrayList<Person> currentEnemies = new ArrayList<>();
        ArrayList<Person> currentAllies = new ArrayList<>();
        // get lists of enemies and allies
        for (Person p:enemies) {
            if (p instanceof Enemy) {
                currentEnemies.add((Enemy) p);
            }
        }
        for (Person p:enemies) {
            if (!(p instanceof Enemy)) {
                currentAllies.add(p);
            }
        }
        int turn = 0;
        // do actual battle
        while (runningBattle) {
            // get current person, their attack, and their target
            currentPerson = enemies.get(turn);
            if (currentPerson instanceof Enemy) {
                currentTarget = currentPerson.chooseTarget(currentAllies);
            } else {
                currentTarget = currentPerson.chooseTarget(currentEnemies);
            }
            currentAttack = currentPerson.chooseAttack();
            // perform attack on target
            currentPerson.attack(currentTarget, currentAttack);
            // if target dies, remove them from the battle
            if (!currentTarget.isAlive()) {
                if (currentTarget.getPC()) {
                    // if player dies, end battle
                    System.out.println("You have died.");
                    runningBattle = false;
                } else {
                    if (currentAllies.contains(currentTarget)) {
                        currentAllies.remove(currentTarget);
                    } else {
                        currentEnemies.remove(currentTarget);
                        // gain xp from killing enemy
                        currentPerson.gainXP(((Enemy) currentTarget).takeXP(), true);
                    }
                }
            }
            // if a side runs out of people, end battle
            if (currentEnemies.isEmpty() || currentAllies.isEmpty()) {
                runningBattle = false;
            }
            turn++;
            if (turn >= enemies.size()) {
                turn = 0;
            }
        }
    }
}
