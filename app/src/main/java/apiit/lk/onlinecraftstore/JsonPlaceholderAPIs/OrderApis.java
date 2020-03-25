package apiit.lk.onlinecraftstore.JsonPlaceholderAPIs;

import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.DTOs.CraftCreator;
import apiit.lk.onlinecraftstore.DTOs.CraftItem;
import apiit.lk.onlinecraftstore.DTOs.OrderDTO;
import apiit.lk.onlinecraftstore.DTOs.UserOrdersDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderApis {

    @POST("order/buyItem")
    Call<ResponseBody> buyItem(@HeaderMap Map<String,String> headers, @Body OrderDTO orderDTO);

    @POST("order/addtocart/{itemid}")
    Call<ResponseBody> addToCart(@HeaderMap Map<String,String> headers, @Path("itemid") long itemid);

    @GET("order/cart")
    Call<List<OrderDTO>> getCartItems(@HeaderMap Map<String,String> headers);

    @GET("order/ordertotal")
    Call<ResponseBody> getOrderTotal(@HeaderMap Map<String,String> headers);

    @POST("order/itemquantity")
    Call<ResponseBody> changeQuantity(@HeaderMap Map<String,String> headers, @Body OrderDTO orderDTO);

    @POST("order/removefromcart/{itemid}")
    Call<ResponseBody> removeFromCart(@HeaderMap Map<String,String> headers, @Path("itemid") long itemid);

    @POST("order/buy")
    Call<ResponseBody> buyCartOrder(@HeaderMap Map<String,String> headers);

    @GET("order/pastOrders")
    Call<List<UserOrdersDTO>> getPastOrders(@HeaderMap Map<String,String> headers);

    @GET("order/pastCraftItems")
    Call<List<CraftItem>> alreadyPurchasedCraftItems(@HeaderMap Map<String,String> headers);

    @GET("order/pastCraftCreators")
    Call<List<CraftCreator>> creatorsTheUserPurchasedFrom(@HeaderMap Map<String,String> headers);

}
