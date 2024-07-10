package history_database.model;

public class ItemJewelry extends FoundItem
{
    //# Fields
    private String jewelryType;
    private int valueEstimate;
    private String imageFilename;

    public ItemJewelry(int id, String placeDiscovered, int finder_id, String dateFound,
                       int expectedYearOfCreation, int museum_id, String type, String jewelryType,
                       int valueEstimate, String imageFilename) {
        super(id, placeDiscovered, finder_id, dateFound, expectedYearOfCreation, museum_id, type);
        this.jewelryType = jewelryType;
        this.valueEstimate = valueEstimate;
        this.imageFilename = imageFilename;
    }

    //# Getters
    public String getJewelryType() {
        return jewelryType;
    }

    public int getValueEstimate() {
        return valueEstimate;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    @Override
    public String toString() {
        return "Jewelry{" +
                "jewelryType='" + jewelryType + '\'' +
                ", valueEstimate=" + valueEstimate +
                ", imageFilename='" + imageFilename + '\'' +
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
