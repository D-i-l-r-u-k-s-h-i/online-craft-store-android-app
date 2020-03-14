package apiit.lk.onlinecraftstore.JsonPlaceholderAPIs;

import apiit.lk.onlinecraftstore.DTOs.JwtAuthenticationResponse;
import apiit.lk.onlinecraftstore.DTOs.LoginRequest;
import apiit.lk.onlinecraftstore.DTOs.UserDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApis {

    @POST("login")
    Call<JwtAuthenticationResponse> signInUser(@Body LoginRequest loginRequest);

    @POST("register")
    Call<ResponseBody> registerUser(@Body UserDTO dto);

}
