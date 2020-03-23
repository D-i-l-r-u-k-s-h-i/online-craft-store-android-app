package apiit.lk.onlinecraftstore.DTOs;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ItemDTO implements Serializable,Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
