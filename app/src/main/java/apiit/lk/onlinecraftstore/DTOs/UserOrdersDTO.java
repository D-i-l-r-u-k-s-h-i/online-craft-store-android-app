package apiit.lk.onlinecraftstore.DTOs;

import java.util.List;

public class UserOrdersDTO {
    private List<OrderCraftItem> orderItemsList;

    private double orderTotal;

    private String purchaseDate;

    private String orderStatus;

    public List<OrderCraftItem> getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(List<OrderCraftItem> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
