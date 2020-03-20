package apiit.lk.onlinecraftstore.DTOs;

public class ReviewDTO {


    private long craftId;

    private String reviewDescription;

    private CraftItem craftItem; //for display

    private AllUsers user;

    private String date;


    public long getCraftId() {
        return craftId;
    }

    public void setCraftId(long craftId) {
        this.craftId = craftId;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public CraftItem getCraftItem() {
        return craftItem;
    }

    public void setCraftItem(CraftItem craftItem) {
        this.craftItem = craftItem;
    }

    public AllUsers getUser() {
        return user;
    }

    public void setUser(AllUsers user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
