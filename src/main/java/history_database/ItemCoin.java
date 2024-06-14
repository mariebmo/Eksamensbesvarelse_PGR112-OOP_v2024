package history_database;

public class ItemCoin extends FoundItem
{
    //# Fields
    private int diameter;
    private String metal;

    //# Constructor
    public ItemCoin(int id, String placeDiscovered, int finder_id, String dateFound,
                    int expectedYearOfCreation, String type, int diameter, String metal) {
        super(id, placeDiscovered, finder_id, dateFound, expectedYearOfCreation, type);
        this.diameter = diameter;
        this.metal = metal;
    }
    public ItemCoin(int id, String placeDiscovered, int finder_id, String dateFound,
                    int expectedYearOfCreation, int museum_id, String type, int diameter, String metal) {
        super(id, placeDiscovered, finder_id, dateFound, expectedYearOfCreation, museum_id, type);
        this.diameter = diameter;
        this.metal = metal;
    }

    //# Getters
    public int getDiameter() {
        return diameter;
    }

    public String getMetal() {
        return metal;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "diameter=" + diameter +
                ", metal='" + metal + '\'' +
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
