package apiit.lk.onlinecraftstore.SupportClasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import apiit.lk.onlinecraftstore.R;

public class RegistrationActivity extends AppCompatActivity {
    private EditText name_et;
    private EditText email_et;
    private EditText password_et;
    private EditText confirmpass_et;

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
    }

    public void signupBtn_onClick(View view) {
        String name = name_et.getText().toString();
        String email = email_et.getText().toString();
        String password = password_et.getText().toString();
        String confirm_password = confirmpass_et.getText().toString();


    }
}
