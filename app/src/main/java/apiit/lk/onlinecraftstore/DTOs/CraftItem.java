package apiit.lk.onlinecraftstore.DTOs;

public class CraftItem {
    private long craftId;

    private String ciName;

    private boolean availabilityStatus;

    private double ciPrice;

    private String imgFile;

    private int itemQuantity;

    private String shortDescription;

    private String longDescription;

    private String category;

    private String type;


    public long getCraftId() {
        return craftId;
    }

    public void setCraftId(long craftId) {
        this.craftId = craftId;
    }

    public String getCiName() {
        return ciName;
    }

    public void setCiName(String ciName) {
        this.ciName = ciName;
    }

    public boolean isAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public double getCiPrice() {
        return ciPrice;
    }

    public void setCiPrice(double ciPrice) {
        this.ciPrice = ciPrice;
    }

    public String getImgFile() {
        return imgFile;
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
