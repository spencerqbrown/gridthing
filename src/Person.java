public class Person {

    private int x;
    private int y;
    private String name;
    private boolean pc;
    private Chunk chunk;

    protected Person(int x, int y, String name, boolean pc) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.pc = pc;
        this.chunk = null;
    }

    protected int getX() {
        return this.x;
    }

    protected int getY() {
        return this.y;
    }

    protected String getName() {
        return this.name;
    }

    protected boolean getPC() {
        return this.pc;
    }

    protected Chunk getChunk() {
        return this.chunk;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setPC(boolean isPC) {
        this.pc = isPC;
    }

    protected void setChunk(Chunk c) {
        this.chunk = c;
    }

    protected boolean removeChunk() {
        if (this.getChunk() == null) {
            System.out.println("There is no chunk to remove");
            return false;
        }
        this.getChunk().removePerson(this);
        this.setChunk(null);
        return true;
    }

}
