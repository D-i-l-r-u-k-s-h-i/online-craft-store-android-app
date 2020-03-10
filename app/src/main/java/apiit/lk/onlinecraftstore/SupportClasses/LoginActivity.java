package apiit.lk.onlinecraftstore.SupportClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import apiit.lk.onlinecraftstore.R;

public class LoginActivity extends AppCompatActivity {
    private EditText name_et;
    private EditText password_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.name_et=findViewById(R.id.usernameET);
        this.password_et=findViewById(R.id.passwordET);
    }

    //onClickSignUp Text view
    public void signup_onClick(View view) {
        startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
//        finish();
    }


    //onClickLogin
    public void login_onClick(View view) {
        String name=name_et.getText().toString();
        String password=password_et.getText().toString();

        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        finish();
    }
}
