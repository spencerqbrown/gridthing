import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Person {

    private int xpLow;
    private int xpHigh;

    protected Enemy(int x, int y, String name, int hp, int xpLow, int xpHigh) {
        super(x, y, name, false, hp);
        this.xpLow = xpLow;
        this.xpHigh = xpHigh;
    }

    protected int takeXP() {
        Random xpRand = new Random();
        int xp = xpRand.nextInt((this.xpHigh - this.xpLow) + 1) + this.xpLow;
        System.out.println(xp);
        return xp;
    }

}
