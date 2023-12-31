package apiit.lk.onlinecraftstore.ActivitiesAndFragments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import apiit.lk.onlinecraftstore.SupportClasses.SaveSharedPreferenceInstance;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //to check whether the user is logged out or not
        if(SaveSharedPreferenceInstance.getAuthToken(SplashActivity.this).length() == 0)
        {
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            finish();
        }
        else
        {
            startActivity(new Intent(SplashActivity.this,HomeActivity.class));
            finish();
        }

    }
}
