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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
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
import ca.ezlock.it.ezpark.ui.InformationsFragment.InformationsFragment;

public class PaymentFragment extends Fragment {

    private FragmentPaymentBinding binding;
    private int STORAGE_PERMISSION_CODE = 1;
    Button paymentbtn;
    EditText paymentname,paymentnumber,paymentexpdate,paymentcvv;
    CheckBox savetouse,usesaved;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,reference1;
    FirebaseAuth mAuth;
    String myuserid;
    Registrationinfo registrationinfo;
    String spotlocation;
    TextView paymentinfo;
    String price;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        spotlocation=getArguments().getString("spotlocation");
        mAuth=FirebaseAuth.getInstance();
        myuserid=mAuth.getUid();
        paymentname=root.findViewById(R.id.paymentname);
        paymentnumber=root.findViewById(R.id.paymentcardnumber);
        paymentexpdate=root.findViewById(R.id.paymentexpirydate);
        paymentcvv=root.findViewById(R.id.paymentcvv);
        usesaved=root.findViewById(R.id.usesaved);
        paymentinfo=root.findViewById(R.id.paymentinfo);
        registrationinfo=new Registrationinfo();

        reference= FirebaseDatabase.getInstance().getReference("Spots");
        reference1= FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                price = snapshot.child(spotlocation).child("fees").getValue(String.class);
                paymentinfo.setText("Parking fees:"+price);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                Bundle bundle=new Bundle();
                bundle.putString("spotlocation",spotlocation);
                reference1.child(myuserid).child("Payment").setValue(true);
                ConfirmationFragment confirmationFragment=new ConfirmationFragment();
                confirmationFragment.setArguments(bundle);
                ReviewFragment reviewFragment=new ReviewFragment();
                FragmentTransaction fr=getParentFragmentManager().beginTransaction();
                paymentbtn.setVisibility(View.GONE);
                fr.replace(R.id.payment,reviewFragment).commit();
            }
        });
        return root;
    }
    public void readData()
    {
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namefromdb=snapshot.child(myuserid).child("Carddetails").child("paymetname").getValue(String.class);
                String numberfromdb=snapshot.child(myuserid).child("Carddetails").child("paymentnumber").getValue(String.class);
                String expdatefromdb=snapshot.child(myuserid).child("Carddetails").child("paymentexpdate").getValue(String.class);
                String cvvfromdb=snapshot.child(myuserid).child("Carddetails").child("paymentcvv").getValue(String.class);
                price = snapshot.child(spotlocation).child("fees").getValue(String.class);
                if(namefromdb=="")
                {
                    Toast.makeText(getContext(),"You have no saved Cards",Toast.LENGTH_SHORT).show();
                }

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
