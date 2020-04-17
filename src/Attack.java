public class Attack {

    private final String name;
    private final int low;
    private final int high;
    private final String effect;
    private final int effectRate;


    public String getName() {
        return name;
    }

    public int getLow() {
        return low;
    }

    public int getHigh() {
        return high;
    }

    public String getEffect() {
        return effect;
    }

    public Attack(String name, int low, int high, String effect, int effectRate) {
        this.name = name;
        this.low = low;
        this.high = high;
        this.effect = effect;
        this.effectRate = effectRate;
    }
}
