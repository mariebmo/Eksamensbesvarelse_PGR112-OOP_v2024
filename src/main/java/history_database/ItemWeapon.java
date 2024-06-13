package history_database;

public class ItemWeapon extends FoundItem
{
    //# Fields
    private String weaponType;
    private String material;
    private int weight;

    //# Constructor
    // When there is NOT a museum_id
    public ItemWeapon(int id, String placeDiscovered, int finder_id, String dateFound, int expectedYearOfCreation, String type, String weaponType, String material, int weight) {
        super(id, placeDiscovered, finder_id, dateFound, expectedYearOfCreation, type);
        this.weaponType = weaponType;
        this.material = material;
        this.weight = weight;
    }
    // When there is a museum_id
    public ItemWeapon(int id, String placeDiscovered, int finder_id, String dateFound, int expectedYearOfCreation, int museum_id, String type, String weaponType, String material, int weight) {
        super(id, placeDiscovered, finder_id, dateFound, expectedYearOfCreation, museum_id, type);
        this.weaponType = weaponType;
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

    @Override
    public String toString() {
        return "Weapon{" +
                "weaponType='" + weaponType + '\'' +
                ", material='" + material + '\'' +
                ", weight=" + weight +
                ", id=" + id +
                ", placeDiscovered='" + placeDiscovered + '\'' +
                ", finder_id=" + finder_id +
                ", dateFound='" + dateFound + '\'' +
                ", expectedYearOfCreation=" + expectedYearOfCreation +
                ", museum_id=" + museum_id +
                ", type='" + type + '\'' +
                '}';
    }
}
