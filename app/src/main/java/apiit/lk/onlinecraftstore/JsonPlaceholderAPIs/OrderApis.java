package apiit.lk.onlinecraftstore.JsonPlaceholderAPIs;

import java.util.Map;

import apiit.lk.onlinecraftstore.DTOs.OrderDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderApis {

    @POST("order/buyItem")
    Call<ResponseBody> buyItem(@HeaderMap Map<String,String> headers, @Body OrderDTO orderDTO);

    @POST("order/addtocart/{itemid}")
    Call<ResponseBody> addToCart(@HeaderMap Map<String,String> headers, @Path("itemid") long itemid);

}
