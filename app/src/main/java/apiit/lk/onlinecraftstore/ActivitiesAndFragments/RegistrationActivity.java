package apiit.lk.onlinecraftstore.ActivitiesAndFragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;

import apiit.lk.onlinecraftstore.DTOs.UserDTO;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.AuthApis;
import apiit.lk.onlinecraftstore.R;
import apiit.lk.onlinecraftstore.SupportClasses.SaveSharedPreferenceInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {
    private EditText name_et;
    private EditText email_et;
    private EditText password_et;
    private EditText confirmpass_et;

    private RadioGroup radioGroup;

    private AuthApis authApis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.name_et=findViewById(R.id.unameET);
        this.email_et=findViewById(R.id.emailET);
        this.password_et=findViewById(R.id.passET);
        this.confirmpass_et=findViewById(R.id.confirmPassET);
        this.radioGroup=findViewById(R.id.radioGroup);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authApis=retrofit.create(AuthApis.class);
    }

    public void signupBtn_onClick(View view) {
        String name = name_et.getText().toString();
        String email = email_et.getText().toString();
        String password = password_et.getText().toString();
        String confirm_password = confirmpass_et.getText().toString();

        int selectedId=radioGroup.getCheckedRadioButtonId();
        RadioButton selectedButton=findViewById(selectedId);

        String userType=selectedButton!=null ? selectedButton.getText().toString(): "Regular User";
        String userTypeCaps="";
        if(userType.equals("Regular User")|| selectedButton.equals(null)){
            userTypeCaps="CUSTOMER";
        }
        else{
            userTypeCaps="CREATOR";
        }

        UserDTO userDTO=new UserDTO(name,password,confirm_password,email,userTypeCaps);

        Call<ResponseBody> userDTOCall=authApis.registerUser(userDTO);

        userDTOCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    Log.d("responseCode", String.valueOf(response.code()));
                }

                ResponseBody responseBody=response.body();
                try {
                    String strResponse=String.valueOf(responseBody.string());
                    Log.d("responsed",strResponse );

                   showToast("Message: "+strResponse+"",getApplicationContext());

                   if(strResponse.equals("Successful registration")){
                       //send user back to login
                       Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                       //to clear the back stack
                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       startActivity(intent);
                       finish();
                   }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void showToast(String message,Context context){
        Toast.makeText(context, message,Toast.LENGTH_LONG).show();
    }
}
