package apiit.lk.onlinecraftstore.ActivitiesAndFragments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import apiit.lk.onlinecraftstore.DTOs.JwtAuthenticationResponse;
import apiit.lk.onlinecraftstore.DTOs.LoginRequest;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.AuthApis;
import apiit.lk.onlinecraftstore.R;
import apiit.lk.onlinecraftstore.SupportClasses.ApiClient;
import apiit.lk.onlinecraftstore.SupportClasses.DecodeToken;
import apiit.lk.onlinecraftstore.SupportClasses.SaveSharedPreferenceInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText name_et;
    private EditText password_et;

    private AuthApis authApis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.name_et=findViewById(R.id.usernameET);
        this.password_et=findViewById(R.id.passwordET);

        authApis= ApiClient.getClient().create(AuthApis.class);
    }

    //onClickSignUp Text view
    public void signup_onClick(View view) {
        startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
    }


    //onClickLogin
    public void login_onClick(View view) {
        String name=name_et.getText().toString();
        String password=password_et.getText().toString();

        LoginRequest loginRequest=new LoginRequest(name,password);

        Call<JwtAuthenticationResponse> call= authApis.signInUser(loginRequest);

        //enqueue over execute-to run it asynchronously
        call.enqueue(new Callback<JwtAuthenticationResponse>() {
            @Override
            public void onResponse(Call<JwtAuthenticationResponse> call, Response<JwtAuthenticationResponse> response) {
                if(!response.isSuccessful()){
                    Log.d("responseCode", String.valueOf(response.code()));
                }

                JwtAuthenticationResponse loginResponse=response.body();

                Log.d("accessToken", loginResponse.getAccessToken());
                String accessToken=loginResponse.getAccessToken();

                try {
                    String decoded = DecodeToken.decoded(accessToken);
                    Log.d("decoded",decoded);

                    JSONObject jsonObject=new JSONObject(decoded);
                    Log.d("jsondecoded", String.valueOf(jsonObject));

                    String user=jsonObject.getString("username");

                    JSONObject roleObj=jsonObject.getJSONObject("role");
                    String role=roleObj.getString("roleName");

                    String userId=jsonObject.getString("userId");

                    SaveSharedPreferenceInstance.setUsername(getApplicationContext(),user);
                    SaveSharedPreferenceInstance.setRole(getApplicationContext(),role);
                    SaveSharedPreferenceInstance.setUserId(getApplicationContext(), userId);
                    SaveSharedPreferenceInstance.setAuthToken(getApplicationContext(),accessToken);

                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    finish();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JwtAuthenticationResponse> call, Throwable t) {
                System.out.println(t.getStackTrace());
            }
        });

    }
}
