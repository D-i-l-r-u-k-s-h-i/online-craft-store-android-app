package apiit.lk.onlinecraftstore.DTOs;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class NotificationsDTO implements Serializable,Parcelable {

    private long notificationId;

    private String notification;

    private String datetime;


    protected NotificationsDTO(Parcel in) {
        notificationId = in.readLong();
        notification = in.readString();
        datetime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(notificationId);
        dest.writeString(notification);
        dest.writeString(datetime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotificationsDTO> CREATOR = new Creator<NotificationsDTO>() {
        @Override
        public NotificationsDTO createFromParcel(Parcel in) {
            return new NotificationsDTO(in);
        }

        @Override
        public NotificationsDTO[] newArray(int size) {
            return new NotificationsDTO[size];
        }
    };

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
