public class Item {

    private String name;
    private String category;

    public Item(String name) {
        this.name = name;
        this.category = null;
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
