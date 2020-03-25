package apiit.lk.onlinecraftstore.DTOs;

public class OrderCraftItem {

    private long id;

//    private Order order;

    private CraftItem craftItem;

    private int quantity;

    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }

    public CraftItem getCraftItem() {
        return craftItem;
    }

    public void setCraftItem(CraftItem craftItem) {
        this.craftItem = craftItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
