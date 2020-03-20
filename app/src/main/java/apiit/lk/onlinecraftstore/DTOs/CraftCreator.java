package apiit.lk.onlinecraftstore.DTOs;

public class CraftCreator {

    private Long creatorId;

    private String creatorName;

    private String creatorEmail;

//    private AllUsers user;

    private Double overallRating;

    public CraftCreator(Long creatorId, String creatorName, String creatorEmail, Double overallRating) {
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.creatorEmail = creatorEmail;
        this.overallRating = overallRating;
    }

    public CraftCreator() {
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public Double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(Double overallRating) {
        this.overallRating = overallRating;
    }
}
