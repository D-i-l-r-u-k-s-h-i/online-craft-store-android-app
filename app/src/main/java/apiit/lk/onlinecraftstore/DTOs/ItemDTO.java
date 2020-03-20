package apiit.lk.onlinecraftstore.DTOs;


import java.io.Serializable;

public class ItemDTO implements Serializable {

    private Long craftId;

    private String ciName;

    private Boolean availabilityStatus;

    private Double ciPrice;

    private String imgFile;

//    private String img;

    private Integer itemQuantity;

    private String shortDescription;

    private String longDescription;

    private String category;

    private String type; //ready made or craft kit

    private CraftCreator creator;

    private Integer noOfPages;

    public ItemDTO() {
    }

    public ItemDTO(Long craftId, String ciName, Boolean availabilityStatus, Double ciPrice, String imgFile, Integer itemQuantity, String shortDescription, String longDescription, String category, String type, CraftCreator creator, Integer noOfPages) {
        this.craftId = craftId;
        this.ciName = ciName;
        this.availabilityStatus = availabilityStatus;
        this.ciPrice = ciPrice;
        this.imgFile = imgFile;
        this.itemQuantity = itemQuantity;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.category = category;
        this.type = type;
        this.creator = creator;
        this.noOfPages = noOfPages;
    }

    public Long getCraftId() {
        return craftId;
    }

    public void setCraftId(Long craftId) {
        this.craftId = craftId;
    }

    public String getCiName() {
        return ciName;
    }

    public void setCiName(String ciName) {
        this.ciName = ciName;
    }

    public Boolean getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(Boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public Double getCiPrice() {
        return ciPrice;
    }

    public void setCiPrice(Double ciPrice) {
        this.ciPrice = ciPrice;
    }

    public String getImgFile() {
        return imgFile;
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
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

    public CraftCreator getCreator() {
        return creator;
    }

    public void setCreator(CraftCreator creator) {
        this.creator = creator;
    }

    public Integer getNoOfPages() {
        return noOfPages;
    }

    public void setNoOfPages(Integer noOfPages) {
        this.noOfPages = noOfPages;
    }
}
