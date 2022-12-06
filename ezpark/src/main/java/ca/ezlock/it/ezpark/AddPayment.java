package ca.ezlock.it.ezpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPayment extends AppCompatActivity {
    Button savecard;
    EditText paymentname,paymentnumber,paymentexpdate,paymentcvv;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    String myuserid;
    Registrationinfo registrationinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        savecard=findViewById(R.id.savecard);
        mAuth= FirebaseAuth.getInstance();
        myuserid=mAuth.getUid();
        paymentname=findViewById(R.id.paymentname1);
        paymentnumber=findViewById(R.id.paymentcardnumber1);
        paymentexpdate=findViewById(R.id.paymentexpirydate1);
        paymentcvv=findViewById(R.id.paymentcvv1);
        registrationinfo=new Registrationinfo();


        savecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeData(paymentname.getText().toString(),paymentnumber.getText().toString(),paymentexpdate.getText().toString(),paymentcvv.getText().toString());
                Intent intent=new Intent(AddPayment.this,MainActivity.class);
            }
        });
    }


    public void writeData(String paymentname, String paymentnumber, String paymentexpdate, String paymentcvv)
    {
        registrationinfo.setPaymentname(paymentname);
        registrationinfo.setPaymentnumber(paymentnumber);
        registrationinfo.setPaymentexpdate(paymentexpdate);
        registrationinfo.setPaymentcvv(paymentcvv);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Users");

        reference.child(myuserid).child("Carddetails").setValue(registrationinfo);
        Toast.makeText(AddPayment.this, "Payment Card Saved", Toast.LENGTH_SHORT).show();
    }
}