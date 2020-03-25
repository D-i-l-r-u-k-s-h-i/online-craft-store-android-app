package apiit.lk.onlinecraftstore.DTOs;

public class RatingsDTO {

    private long creatorId;

    private float rating;

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
