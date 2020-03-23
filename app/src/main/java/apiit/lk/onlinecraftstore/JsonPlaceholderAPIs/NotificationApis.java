package apiit.lk.onlinecraftstore.JsonPlaceholderAPIs;

import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.DTOs.NotificationsDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

public interface NotificationApis {

    @GET("notifications/getallofuser")
    Call<List<NotificationsDTO>> getNotifications(@HeaderMap Map<String,String> headers);
}
