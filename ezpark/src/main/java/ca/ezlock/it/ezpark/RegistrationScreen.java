package ca.ezlock.it.ezpark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.regex.Pattern;

public class RegistrationScreen extends AppCompatActivity {
    EditText e1, e2, e3, e4, e5;
    Button create;
    String Fullname, Email, Phone, Pwd;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    Registrationinfo registrationinfo;
    String myuserid;
    ImageView uppercaseblank,uppercasefilled,charblank,charfilled,specialblank,specialfilled,numberblank,numberfilled;
    int uppercaseno=0,chno=0,specialno=0,numberno=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);
        e1 = findViewById(R.id.signupname);
        e2 = findViewById(R.id.signupemail);
        e3 = findViewById(R.id.signupnumber);
        e4 = findViewById(R.id.signuptPassword);
        e5 = findViewById(R.id.esignupverifyPassword);
        mAuth=FirebaseAuth.getInstance();
        uppercaseblank=findViewById(R.id.uppercaseblank);
        uppercasefilled=findViewById(R.id.uppercasefilled);
        charblank=findViewById(R.id.charblank);
        charfilled=findViewById(R.id.charfilled);
        specialblank=findViewById(R.id.specialblank);
        specialfilled=findViewById(R.id.specialfilled);
        numberblank=findViewById(R.id.numberblank);
        numberfilled=findViewById(R.id.numberfilled);

        uppercasefilled.setVisibility(View.INVISIBLE);
        charfilled.setVisibility(View.INVISIBLE);
        specialfilled.setVisibility(View.INVISIBLE);
        numberfilled.setVisibility(View.INVISIBLE);


        Fullname = e1.getText().toString();
        Email = e2.getText().toString();
        Phone = e3.getText().toString();
        Pwd = e4.getText().toString();
        e4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                passwordvalidate();
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


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
                }
                else if(uppercaseno==0 || specialno==0|| chno==0|| numberno==0)
                {
                    Toast.makeText(RegistrationScreen.this, "CHeck password", Toast.LENGTH_SHORT).show();
                }
                else if (e5.getText().toString().equals("")) {
                    Toast.makeText(RegistrationScreen.this, "Enter Your Password again", Toast.LENGTH_SHORT).show();
                }
                else if (e4.getText().toString().equals(e5.getText().toString())) {
                    addDataToFirebase((e1.getText().toString()), (e2.getText().toString()), (e3.getText().toString()), (e4.getText().toString()));
                } else {
                    Toast.makeText(RegistrationScreen.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void addDataToFirebase(String fullname, String email, String phone, String pwd) {
        registrationinfo.setFullname(fullname);
        registrationinfo.setemail(email);
        registrationinfo.setphone(phone);
        registrationinfo.setpwd(pwd);


        mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    reference = firebaseDatabase.getReference("Users");
                    myuserid=mAuth.getUid();
                    reference.child(myuserid).setValue(registrationinfo);
                    Toast.makeText(RegistrationScreen.this, "Account Created", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegistrationScreen.this,LoginScreen.class);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Toast.makeText(RegistrationScreen.this, "Email already Registered", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void passwordvalidate()
    {
        Pattern AZ=Pattern.compile("[A-Z]");
        Pattern special=Pattern.compile("[$&+,:;=?@#|'<>.-^*()%!]");
        Pattern number=Pattern.compile("[0-9]");
        if(AZ.matcher(e4.getText().toString()).find())
        {
            uppercasefilled.setVisibility(View.VISIBLE);
            uppercaseno=1;
        }
        if(special.matcher(e4.getText().toString()).find())
        {
            specialfilled.setVisibility(View.VISIBLE);
            specialno=1;
        }
        if(e4.getText().toString().length()>=6)
        {
            charfilled.setVisibility(View.VISIBLE);
            chno=1;
        }
        if(number.matcher(e4.getText().toString()).find())
        {
            numberfilled.setVisibility(View.VISIBLE);
            numberno=1;
        }
    }
}