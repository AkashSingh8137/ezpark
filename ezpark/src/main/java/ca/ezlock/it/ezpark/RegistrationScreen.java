package ca.ezlock.it.ezpark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationScreen extends AppCompatActivity {
    EditText e1, e2, e3, e4, e5,e6;
    Button create;
    String Fullname, Email, Phone, Pwd;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    Registrationinfo registrationinfo;
    ReadWriteUserDetails readWriteUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);
        e1 = findViewById(R.id.signupname);
        e2 = findViewById(R.id.signupemail);
        e3 = findViewById(R.id.signupnumber);
        e4 = findViewById(R.id.signuptPassword);
        e5 = findViewById(R.id.esignupverifyPassword);
        e6=findViewById(R.id.signupusername);

        Fullname = e1.getText().toString();
        Email = e2.getText().toString();
        Phone = e3.getText().toString();
        Pwd = e4.getText().toString();


        registrationinfo = new Registrationinfo();

        create = findViewById(R.id.createnewaccount);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (e1.getText().toString().equals("")) {
                    Toast.makeText(RegistrationScreen.this, "Enter your name", Toast.LENGTH_SHORT).show();
                } else if (e2.getText().toString().equals("")) {
                    Toast.makeText(RegistrationScreen.this, "Enter your Email Address", Toast.LENGTH_SHORT).show();
                } else if (e3.getText().toString().equals("")) {
                    Toast.makeText(RegistrationScreen.this, "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                } else if (e4.getText().toString().equals("")) {
                    Toast.makeText(RegistrationScreen.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
                } else if (e5.getText().toString().equals("")) {
                    Toast.makeText(RegistrationScreen.this, "Enter Your Password again", Toast.LENGTH_SHORT).show();
                }else if(e6.getText().toString().equals(""))
                {
                    Toast.makeText(RegistrationScreen.this, "Enter Your Username", Toast.LENGTH_SHORT).show();
                }
                else if (e4.getText().toString().equals(e5.getText().toString())) {
                    addDataToFirebase((e1.getText().toString()), (e2.getText().toString()), (e3.getText().toString()), (e4.getText().toString()),(e6.getText().toString()));
                } else {
                    Toast.makeText(RegistrationScreen.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void addDataToFirebase(String fullname, String email, String phone, String pwd,String username) {
        registrationinfo.setFullname(fullname);
        registrationinfo.setemail(email);
        registrationinfo.setphone(phone);
        registrationinfo.setpwd(pwd);
        registrationinfo.setusername(username);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Registrationinfo");

        reference.child(username).setValue(registrationinfo);
        Toast.makeText(RegistrationScreen.this, "Account Created", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(RegistrationScreen.this,LoginScreen.class);
                startActivity(intent);
                finish();


    }
}