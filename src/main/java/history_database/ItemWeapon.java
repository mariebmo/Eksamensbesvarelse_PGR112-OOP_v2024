package history_database;

public class ItemWeapon extends FoundItem
{
    //# Fields
    private String material;
    private int weight;

    //# Constructor
    public ItemWeapon(int id, String placeDiscovered, int finder_id, String dateFound, int expectedYearOfCreation, int museum_id, String type, String material, int weight) {
        super(id, placeDiscovered, finder_id, dateFound, expectedYearOfCreation, museum_id, type);
        this.material = material;
        this.weight = weight;
    }

    //# Getters
    public String getMaterial() {
        return material;
    }

    public int getWeight() {
        return weight;
    }
}
