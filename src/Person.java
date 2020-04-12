import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Person {

    private int x;
    private int y;
    private String name;
    private boolean pc;
    private Chunk chunk;
    private boolean able;
    private ArrayList<Attack> attacks;
    private ArrayList<String> effects;
    private int hp;
    private boolean alive;

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    protected Person(int x, int y, String name, boolean pc, int hp) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.pc = pc;
        this.chunk = null;
        this.able = true;
        this.hp = hp;
        this.attacks = new ArrayList<>();
        this.alive = true;
    }

    protected boolean takeDamage(int dmg) {
        // add defense, shielding, etc. later
        this.hp -= dmg;
        System.out.println(this.getName() + " takes " + dmg + " damage!");
        if (hp <= 0) {
            die();
        }
        return true;
    }

    private void die() {
        this.removeChunk();
        this.setAlive(false);
        System.out.println(this.getName() + " IS DEAD");
    }

    protected Attack chooseAttack() {
        Attack attack = null;
        if (this.getPC()) {
            Scanner input = new Scanner(System.in);
            String inString;
            // get choose attack menu
            String attacksMenu = "Choose your attack: ";
            for (int i = 0; i < this.getAttacks().size(); i++) {
                attacksMenu = attacksMenu + Integer.toString(i) + ". " + this.getAttacks().get(i).getName() + " ";
            }

            boolean choosing = true;
            while (choosing) {
                System.out.print(attacksMenu);
                try {
                    inString = input.nextLine();
                    int attackIndex = Integer.parseInt(inString);
                    attack = this.getAttacks().get(attackIndex);
                    choosing = false;
                } catch (NumberFormatException e) {
                    System.out.print("Choose a valid attack");
                }
            }
        } else {
            Random randAttack = new Random();
            int attackIndex = randAttack.nextInt(this.getAttacks().size());
            attack = this.getAttacks().get(attackIndex);
        }

        return attack;
    }

    protected Person chooseTarget(ArrayList<Person> enemies) {
        Person target = null;
        if (this.getPC()) {
            Scanner input = new Scanner(System.in);
            String inString;
            // get choose attack menu
            String enemyMenu = "Choose your target: ";
            for (int i = 0; i < enemies.size(); i++) {
                enemyMenu = enemyMenu + Integer.toString(i) + ". " + enemies.get(i).getName() + " ";
            }
            boolean choosing = true;
            while (choosing) {
                System.out.print(enemyMenu);
                try {
                    inString = input.nextLine();
                    int enemyIndex = Integer.parseInt(inString);
                    target = enemies.get(enemyIndex);
                    choosing = false;
                } catch (NumberFormatException e) {
                    System.out.print("Choose a valid enemy");
                }
            }
        } else {
            Random randTarget = new Random();
            int targetIndex = randTarget.nextInt(enemies.size());
            target = enemies.get(targetIndex);
        }
        return target;
    }

    protected boolean attack(Person p, Attack a) {
        if (this.isAble()) {
            if (this.getAttacks().contains(a)) {
                int low = a.getLow();
                int high = a.getLow();
                Random rand = new Random();
                int dmg = rand.nextInt((high - low) + 1) + low;
                System.out.println(this.getName() + " uses " + a.getName() + " on " + p.getName() + "!");
                p.takeDamage(dmg);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public boolean isPc() {
        return pc;
    }

    public boolean isAble() {
        return able;
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
