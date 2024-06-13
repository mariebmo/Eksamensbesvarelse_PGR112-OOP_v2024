package history_database;

public class ItemJewelry extends FoundItem
{
    //# Fields
    private int valueEstimate;
    private String imageFilename;

    //# Constructor
    public ItemJewelry(int id, String placeDiscovered, int finder_id, String dateFound, int expectedYearOfCreation, int museum_id, String type, int valueEstimate, String imageFilename) {
        super(id, placeDiscovered, finder_id, dateFound, expectedYearOfCreation, museum_id, type);
        this.valueEstimate = valueEstimate;
        this.imageFilename = imageFilename;
    }

    //# Getters
    public int getValueEstimate() {
        return valueEstimate;
    }

    public String getImageFilename() {
        return imageFilename;
    }
}
