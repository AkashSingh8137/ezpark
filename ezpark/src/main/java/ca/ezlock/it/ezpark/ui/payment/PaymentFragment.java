package ca.ezlock.it.ezpark.ui.payment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import ca.ezlock.it.ezpark.ConfirmationFragment;
import ca.ezlock.it.ezpark.LoginScreen;
import ca.ezlock.it.ezpark.R;
import ca.ezlock.it.ezpark.RegistrationScreen;
import ca.ezlock.it.ezpark.Registrationinfo;
import ca.ezlock.it.ezpark.ReviewFragment;
import ca.ezlock.it.ezpark.databinding.FragmentPaymentBinding;

public class PaymentFragment extends Fragment {

    private FragmentPaymentBinding binding;
    private int STORAGE_PERMISSION_CODE = 1;
    Button paymentbtn;
    EditText paymentname,paymentnumber,paymentexpdate,paymentcvv;
    CheckBox savetouse,usesaved;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    Registrationinfo registrationinfo;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        paymentname=root.findViewById(R.id.paymentname);
        paymentnumber=root.findViewById(R.id.paymentcardnumber);
        paymentexpdate=root.findViewById(R.id.paymentexpirydate);
        paymentcvv=root.findViewById(R.id.paymentcvv);
        savetouse=root.findViewById(R.id.savetouse);
        usesaved=root.findViewById(R.id.usesaved);
        registrationinfo=new Registrationinfo();

        savetouse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(savetouse.isChecked())
                {
                    writeData(paymentname.getText().toString(),paymentnumber.getText().toString(),paymentexpdate.getText().toString(),paymentcvv.getText().toString());
                }
            }
        });
        usesaved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                readData();
            }
        });


        paymentbtn = (Button) root.findViewById(R.id.pay);
        paymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                ConfirmationFragment confirmationFragment=new ConfirmationFragment();
                ReviewFragment reviewFragment=new ReviewFragment();
                FragmentTransaction fr=getParentFragmentManager().beginTransaction();
                paymentbtn.setVisibility(View.GONE);
                fr.replace(R.id.payment,reviewFragment).commit();
            }
        });
        return root;
    }
    public void writeData(String paymentname, String paymentnumber, String paymentexpdate, String paymentcvv)
    {
        registrationinfo.setPaymentname(paymentname);
        registrationinfo.setPaymentnumber(paymentnumber);
        registrationinfo.setPaymentexpdate(paymentexpdate);
        registrationinfo.setPaymentcvv(paymentcvv);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Registrationinfo");

        reference.child("imakash").child("Carddetails").setValue(registrationinfo);
        Toast.makeText(getContext(), "Payment Card Saved", Toast.LENGTH_SHORT).show();
    }
    public void readData()
    {
        reference= FirebaseDatabase.getInstance().getReference("Registrationinfo");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namefromdb=snapshot.child("imakash").child("Carddetails").child("paymetname").getValue(String.class);
                String numberfromdb=snapshot.child("imakash").child("Carddetails").child("paymentnumber").getValue(String.class);
                String expdatefromdb=snapshot.child("imakash").child("Carddetails").child("paymentexpdate").getValue(String.class);
                String cvvfromdb=snapshot.child("imakash").child("Carddetails").child("paymentcvv").getValue(String.class);

                paymentname.setText(namefromdb);
                paymentnumber.setText(numberfromdb);
                paymentexpdate.setText(expdatefromdb);
                paymentcvv.setText(cvvfromdb);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}

}
