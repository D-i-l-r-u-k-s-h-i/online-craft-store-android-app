package apiit.lk.onlinecraftstore.JsonPlaceholderAPIs;

import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.DTOs.ReviewDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;

public interface RatingsReviewsApis {

    @GET("ratingsreviews/craftreview/{id}")
    Call<List<ReviewDTO>> getCraftReviews(@HeaderMap Map<String,String> headers, @Path("id") long id);
}
