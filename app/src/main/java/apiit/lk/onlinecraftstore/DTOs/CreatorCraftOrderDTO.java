package apiit.lk.onlinecraftstore.DTOs;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class CreatorCraftOrderDTO implements Serializable,Parcelable {
    private long orderCraftItemId;

    private String username;

    private String craftName;

    private int quantity;

    private double orderTotal;

    private String purchaseDate;

    public CreatorCraftOrderDTO(long orderCraftItemId, String username, String craftName, int quantity, double orderTotal, String purchaseDate) {
        this.orderCraftItemId = orderCraftItemId;
        this.username = username;
        this.craftName = craftName;
        this.quantity = quantity;
        this.orderTotal = orderTotal;
        this.purchaseDate = purchaseDate;
    }

    public CreatorCraftOrderDTO() {
    }

    public long getOrderCraftItemId() {
        return orderCraftItemId;
    }

    public void setOrderCraftItemId(long orderCraftItemId) {
        this.orderCraftItemId = orderCraftItemId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCraftName() {
        return craftName;
    }

    public void setCraftName(String craftName) {
        this.craftName = craftName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quantity);
        dest.writeString(username);
        dest.writeString(craftName);
        dest.writeString(purchaseDate);
        dest.writeLong(orderCraftItemId);
        dest.writeDouble(orderTotal);
    }

    public CreatorCraftOrderDTO(Parcel in) {
        quantity = in.readInt();
        username = in.readString();
        craftName = in.readString();
        purchaseDate = in.readString();
        orderCraftItemId = in.readLong();
        orderTotal=in.readDouble();
    }

    public static final Parcelable.Creator<CreatorCraftOrderDTO> CREATOR = new Parcelable.Creator<CreatorCraftOrderDTO>() {
        public CreatorCraftOrderDTO createFromParcel(Parcel in) {
            return new CreatorCraftOrderDTO(in);
        }

        public CreatorCraftOrderDTO[] newArray(int size) {
            return new CreatorCraftOrderDTO[size];
        }
    };
}
