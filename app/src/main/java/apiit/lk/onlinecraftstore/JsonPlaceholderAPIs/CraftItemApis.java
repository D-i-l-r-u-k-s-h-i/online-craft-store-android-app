package apiit.lk.onlinecraftstore.JsonPlaceholderAPIs;

import android.support.annotation.Nullable;

import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.DTOs.CreatorCraftOrderDTO;
import apiit.lk.onlinecraftstore.DTOs.ItemDTO;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @POST("craft/delete/{id}")
    Call<ResponseBody> deleteItem(@HeaderMap Map<String,String> headers, @Path("id") long id);

    @Multipart
    @POST("craft/update")
    Call<ResponseBody> updateItem(@HeaderMap Map<String,String> headers,@Part("imgFile") MultipartBody imgFile, @Query("craftId") Long craftId,
                                  @Nullable @Query("ciName")String ciName, @Nullable @Query("ciPrice") Double ciPrice, @Nullable @Query("itemQuantity")Integer itemQuantity,
                                  @Nullable @Query("shortDescription") String shortDescription, @Nullable @Query("longDescription") String longDescription,
                                  @Nullable @Query("type") String type, @Nullable @Query("category") String category, @Nullable @Query(value ="availabilityStatus") Boolean availabilityStatus);

    @Multipart
    @POST("craft/add")
    Call<ResponseBody> addItem(@HeaderMap Map<String,String> headers,@Part("imgFile") MultipartBody imgFile,
                                  @Nullable @Query("ciName")String ciName, @Nullable @Query("ciPrice") Double ciPrice, @Nullable @Query("itemQuantity")Integer itemQuantity,
                                  @Nullable @Query("shortDescription") String shortDescription, @Nullable @Query("longDescription") String longDescription,
                                  @Nullable @Query("type") String type, @Nullable @Query("category") String category, @Nullable @Query(value ="availabilityStatus") Boolean availabilityStatus);

    @GET("craft/craftorders")
    Call<List<CreatorCraftOrderDTO>> getCraftOrdersForCreator(@HeaderMap Map<String,String> headers);
}
