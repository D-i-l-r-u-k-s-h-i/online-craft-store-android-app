package apiit.lk.onlinecraftstore.DTOs;

import java.util.Date;

public class OrderDTO {
    private Long craftId;

    private Integer quantity;

    private Date purchasedDate;

    private CraftItem craftItem;

    public Long getCraftId() {
        return craftId;
    }

    public void setCraftId(Long craftId) {
        this.craftId = craftId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public CraftItem getCraftItem() {
        return craftItem;
    }

    public void setCraftItem(CraftItem craftItem) {
        this.craftItem = craftItem;
    }
}
