package apiit.lk.onlinecraftstore.JsonPlaceholderAPIs;

import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.DTOs.ItemDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;

public interface CraftItemApis {

    @GET("craft/getall")
    Call<List<ItemDTO>> getAllCraft(@HeaderMap Map<String,String> headers);

    @GET("craft/mostrecent")
    Call<List<ItemDTO>> getMostRecentCraft(@HeaderMap Map<String,String> headers);

    @GET("craft/filtercategory/{category}")
    Call<List<ItemDTO>> getItemsByCategory(@HeaderMap Map<String,String> headers,@Path("category") String category);

    @GET("craft/search/{value}")
    Call<List<ItemDTO>> searchCrafts(@HeaderMap Map<String,String> headers,@Path("value") String value);

    @GET("craft/creator/{id}/{page}")
    Call<List<ItemDTO>> getCraftItemsOfCreator(@HeaderMap Map<String,String> headers,@Path("id") long id,@Path("page") int page);

}
