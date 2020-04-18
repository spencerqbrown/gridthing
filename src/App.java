import java.util.HashMap;
import java.util.Scanner;

public class App {

    private Map map;
    private Person pc;
    private HashMap<String, String> controls;

    public HashMap<String, String> getControls() {
        return controls;
    }

    public static void main(String[] args) {
        App app = new App();
        app.initializeWorld("TEST3");
        Scanner input = new Scanner(System.in);
        String inString;
        boolean keepRunning = true;
        app.controls = new HashMap<>();
        app.getControls().put("quit", "q");
        // directionals don't actually work or do anything
        app.getControls().put("east", "e");
        app.getControls().put("west", "w");
        app.getControls().put("north", "n");
        app.getControls().put("south", "s");
        app.getControls().put("look", "l");
        app.getControls().put("menu", "m");

        while (keepRunning) {
            inString = input.nextLine();
            if (inString.equals(app.getControls().get("quit"))) {
                keepRunning = false;
            } else if (inString.equals(app.getControls().get("look"))) {
                app.getPc().look();
            } else if (inString.equals(app.getControls().get("menu"))) {
                app.openMenu();
            } else if (inString.length() == 0) {
                continue;
            } else {
                app.movePerson(app.getPc(), inString.charAt(0));
            }
        }
    }

    private void openMenu() {
        Scanner menuScanner = new Scanner(System.in);
        // just inventory for now
        String menuString = "Menu:\n0. Inventory" + " " + " 1. Close menu";
        String inString;
        boolean keepMenuOpen = true;
        // maybe make this more generalizable later
        while (keepMenuOpen) {
            System.out.println(menuString);
            inString = menuScanner.nextLine();
            if (inString.equals("0")) {
                this.openInventory();
            } else if (inString.equals("1")) {
                keepMenuOpen = false;
            } else {
                System.out.println("Invalid input");
            }
        }
        System.out.println(this.getMap().cameraRender(7, 5));
    }

    private void openInventory() {
        String invString1;
        Scanner invScanner = new Scanner(System.in);
        Person pc = this.getPc();
        if (pc.getInventory().size() == 0) {
            System.out.println("Inventory is empty.");
            return;
        }
        int i;
        boolean stillInInventory = true;
        String inString;
        int selectionIndex;
        // while in base inventory menu
        while (stillInInventory) {
            i = 0;
            invString1 = "Inventory:\n";
            for (Object s:pc.getInventory().keySet().toArray()) {
                invString1 = invString1 + i + ". " + s + " ";
                i++;
            }
            invString1 = invString1 + i + ". " + "exit inventory";
            System.out.println(invString1);
            inString = invScanner.nextLine();
            try {
                selectionIndex = Integer.parseInt(inString);
                // if exit index
                if (selectionIndex == i) {
                    stillInInventory = false;
                } else if ((selectionIndex < 0) || (selectionIndex >= pc.getInventory().size())) {
                    // if invalid selection index
                    System.out.println("Invalid item category selection");
                    continue;
                } else {
                    // if valid selection index, go to category submenu
                    Object[] categories = pc.getInventory().keySet().toArray();
                    String category = (String) categories[selectionIndex];
                    this.openInventorySubmenu(category);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid item category selection");
                continue;
            }
        }

    }

    private void openInventorySubmenu(String category) {
        boolean stillInCategory = true;
        Person pc = this.getPc();
        int i;
        String invString2;
        String inString;
        Scanner submenuScanner = new Scanner(System.in);
        int selectionIndex;
        Item selectedItem;
        while (stillInCategory) {
            i = 0;
            invString2 = category + ":\n";
            for (Item item:pc.getInventory().get(category)) {
                invString2 = invString2 + i + ". " + item.getName() + " ";
                if (item.isEquipped()) {
                    invString2 = invString2 + " (equipped) ";
                }
                i++;
            }
            invString2 = invString2 + i + ". " + "exit category menu";
            System.out.println(invString2);
            inString = submenuScanner.nextLine();
            try {
                selectionIndex = Integer.parseInt(inString);
                // if exit index
                if (selectionIndex == i) {
                    stillInCategory = false;
                } else if ((selectionIndex < 0) || (selectionIndex >= pc.getInventory().get(category).size())) {
                    // if invalid selection index
                    System.out.println("Invalid item selection");
                    continue;
                } else {
                    // if valid selection index, go to item menu
                    selectedItem = pc.getInventory().get(category).get(selectionIndex);
                    this.openItemMenu(selectedItem);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid item selection");
                continue;
            }
        }
    }

    private void openItemMenu(Item item) {
        boolean stillInItemMenu = true;
        String itemStr = "";
        String itemTitleLine = "";
        itemTitleLine = itemTitleLine + "\n";
        itemStr = "0. examine 1. drop 2. ";
        String inString;
        Scanner itemScanner = new Scanner(System.in);
        while (stillInItemMenu) {
            // build menu string
            if (item.isEquipped()) {
                itemTitleLine = item.getName() + " (equipped)" + "\n";
            } else {
                itemTitleLine = item.getName() + "\n";
            }
            itemStr = "0. examine 1. drop 2. ";
            if (item.isEquippable()) {
                if (item.isEquipped()) {
                    itemStr = itemStr + "unequip ";
                } else {
                    itemStr = itemStr + "equip ";
                }
                itemStr = itemStr + "3. exit item menu";
            } else {
                itemStr = itemStr + "exit item menu";
            }
            System.out.println(itemTitleLine + itemStr);
            inString = itemScanner.nextLine();
            if (inString.equals("0")) {
               System.out.println(item.getDesc());
            } else if (inString.equals("1")) {
               this.getPc().dropItem(item);
               stillInItemMenu = false;
            } else if (inString.equals("2")) {
               if (item.isEquippable()) {
                   if (item.isEquipped()) {
                       this.getPc().unequip(item);
                   } else {
                       this.getPc().equip(item);
                   }
               } else {
                   stillInItemMenu = false;
               }
            } else if (inString.equals("3")) {
               if (item.isEquippable()) {
                   stillInItemMenu = false;
               } else {
                   System.out.println("Invalid action selection");
               }
            } else {
               System.out.println("Invalid action selection");
            }
        }
    }

    protected Map getMap() {
        return this.map;
    }

    protected Person getPc() {
        return this.pc;
    }

    protected void setMap(Map m) {
        this.map = m;
    }

    protected void setPc (Person pc) {
        this.pc = pc;
    }

    public boolean initializeWorld(String testType) {
        if ((this.getMap() == null) && (this.getPc() == null)) {
            this.setPc(new Person(0, 0, "PC", true, 10));
            Chunk core = new Chunk(null, null, null, null);
            if (testType == null) {
                // current default
                this.setMap(new Map(3, 3));
                this.getMap().placeChunk(core, 1, 1);
            } else if (testType.equals("TEST1") || testType.equals("TEST3")) {
                this.setMap(new Map(10, 10));
                this.getMap().placeChunk(core, 1, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 2, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 3, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 4, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 5, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 5, 2);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 5, 3);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 6, 3);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 6, 4);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 7, 3);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 7, 4);
            } else if (testType.equals("TEST2")) {
                this.setMap(new Map(7, 5));
                this.getMap().placeChunk(core, 1, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 2, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 3, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 4, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 5, 1);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 5, 2);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 5, 3);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 6, 3);
                this.getMap().placeChunk(new Chunk(null, null, null, null), 6, 4);
            }
            core.addPerson(this.getPc());
            if (testType == "TEST3") {
                this.getPc().getAttacks().add(new Attack("KICK", 4, 6, null, 0));
                this.getPc().getAttacks().add(new Attack("PUNCH", 1, 3, null, 0));
                Attack[] swordattacks = new Attack[2];
                swordattacks[0] = new Attack("SLASH", 2, 4, null, 0);
                swordattacks[1] = new Attack("STAB", 0, 5, null, 0);
                Weapon sword = new Weapon("SWORD", swordattacks);
                this.getMap().placeAt(sword, 7, 3);
                //this.getPc().pickup(sword);
                Enemy testEnemy = new Enemy(0, 0, "BIG DOG", 5, 50, 100);
                testEnemy.getAttacks().add(new Attack("BITE", 0, 2, null, 0));
                testEnemy.getAttacks().add(new Attack("SCRATCH", 1, 3, null, 0));
                core.getE().getE().getE().getE().getS().getS().getE().getE().getS().addPerson(testEnemy);
            }
            System.out.print(map.fullMap());
            System.out.print(map.cameraRender(7, 5));
            return true;
        }
        System.out.println("World already initialized");
        return false;
    }

    public boolean movePerson(Person p, char dir) {
        if (!this.getMap().getPeople().contains(p)) {
            System.out.println("This person is not in map");
            return false;
        }
        Chunk curChunk = p.getChunk();
        if (p.getChunk().getDir(dir) == null) {
            System.out.println("No chunk in that direction");
            return false;
        }
        Chunk chunkToEnter = curChunk.getDir(dir);
        p.removeChunk();
        chunkToEnter.addPerson(p);
        p.setDir(dir);
        System.out.print(this.getMap().cameraRender(7, 5));
        return true;
    }

}
