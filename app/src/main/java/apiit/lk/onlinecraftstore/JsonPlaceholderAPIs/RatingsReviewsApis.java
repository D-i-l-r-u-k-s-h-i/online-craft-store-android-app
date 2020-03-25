package apiit.lk.onlinecraftstore.JsonPlaceholderAPIs;

import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.DTOs.RatingsDTO;
import apiit.lk.onlinecraftstore.DTOs.ReviewDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RatingsReviewsApis {

    @GET("ratingsreviews/craftreview/{id}")
    Call<List<ReviewDTO>> getCraftReviews(@HeaderMap Map<String,String> headers, @Path("id") long id);

    @POST("ratingsreviews/addreview")
    Call<ResponseBody> addReviewForCraft(@HeaderMap Map<String,String> headers, @Body ReviewDTO reviewDTO);

    @POST("ratingsreviews/addrating")
    Call<ResponseBody> addRatingForCreator(@HeaderMap Map<String,String> headers, @Body RatingsDTO ratingsDTO);
}
