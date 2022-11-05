package ca.ezlock.it.ezpark;

import static ca.ezlock.it.ezpark.R.layout.activity_login_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {
    private Button btn;
    private EditText e1,e2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login_screen);
        btn=findViewById(R.id.logbutton);
        e1=findViewById(R.id.email);
        e2=findViewById(R.id.Password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e1.getText().toString().equals("admin") && e2.getText().toString().equals("admin"))
                {
                    Intent i = new Intent(LoginScreen.this, MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(LoginScreen.this, "Username or Password incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}