package ca.ezlock.it.ezpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationScreen extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5;
    Button create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);
        e1=findViewById(R.id.signupname);
        e2=findViewById(R.id.signupemail);
        e3=findViewById(R.id.signupnumber);
        e4=findViewById(R.id.signuptPassword);
        e5=findViewById(R.id.esignupverifyPassword);

        create=findViewById(R.id.createnewaccount);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e1.getText().toString().equals(""))
                {
                    Toast.makeText(RegistrationScreen.this,"Enter your name",Toast.LENGTH_SHORT).show();
                }
                else if(e2.getText().toString().equals(""))
                {
                    Toast.makeText(RegistrationScreen.this,"Enter your Email Address",Toast.LENGTH_SHORT).show();
                }
                else if(e3.getText().toString().equals(""))
                {
                    Toast.makeText(RegistrationScreen.this, "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                }
                else if(e4.getText().toString().equals(""))
                {
                    Toast.makeText(RegistrationScreen.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
                }
                else if(e5.getText().toString().equals(""))
                {
                    Toast.makeText(RegistrationScreen.this, "Enter Your Password again", Toast.LENGTH_SHORT).show();
                }
                else if(e4.getText().toString().equals(e5.getText().toString()))
                {
                    Intent intent=new Intent(RegistrationScreen.this,LoginScreen.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(RegistrationScreen.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}