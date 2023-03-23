package ca.ezlock.it.ezpark.ui.InformationsFragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import ca.ezlock.it.ezpark.ConfirmationFragment;
import ca.ezlock.it.ezpark.MainActivity;
import ca.ezlock.it.ezpark.R;
import ca.ezlock.it.ezpark.RegistrationScreen;
import ca.ezlock.it.ezpark.Registrationinfo;
import ca.ezlock.it.ezpark.SpotActivity;
import ca.ezlock.it.ezpark.databinding.FragmentInformationsBinding;
import ca.ezlock.it.ezpark.ui.payment.PaymentFragment;


public class InformationsFragment extends Fragment {

    private FragmentInformationsBinding binding;

    CheckBox saveprofile;
    EditText bookname,bookemail,bookphone,bookcarplate,bookcarmake,bookcarmodel,timefrom,timeto;
    DatabaseReference reference,reference1;
    Button proceedtopay;
    FirebaseAuth mAuth;
    String myuserid;
    Registrationinfo registrationinfo;
    String spotlocation;
    TimePickerDialog timePickerDialog;
    int to,from;
    String pr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InformationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(InformationsViewModel.class);

        binding = FragmentInformationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth=FirebaseAuth.getInstance();
        myuserid=mAuth.getUid();
        spotlocation=getArguments().getString("spotlocation");
        bookname=root.findViewById(R.id.bookname);
        bookemail=root.findViewById(R.id.bookemail);
        bookphone=root.findViewById(R.id.bookphone);
        bookcarmake=root.findViewById(R.id.bookcarmake);
        bookcarplate=root.findViewById(R.id.bookcarplate);
        bookcarmodel=root.findViewById(R.id.bookcarmodel);
        timefrom=root.findViewById(R.id.timefrom);
        timeto=root.findViewById(R.id.timeto);
        proceedtopay=root.findViewById(R.id.proceedtopay);
        registrationinfo=new Registrationinfo();
        timefrom.setFocusableInTouchMode(false);
        timeto.setFocusableInTouchMode(false);


        proceedtopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        saveprofile=root.findViewById(R.id.savedProfile);
        saveprofile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(saveprofile.isChecked())
                {
                    writedata();
                }
            }
        });

        timefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timefrom();
            }
        });
        timeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeto();
                price();
            }
        });


        return root;
    }
    public void writedata()
    {
        reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namefromdb=snapshot.child(myuserid).child("fullname").getValue(String.class);
                String emailfromdb=snapshot.child(myuserid).child("email").getValue(String.class);
                String phonefromdb=snapshot.child(myuserid).child("phone").getValue(String.class);
                String carplatefromdb=snapshot.child(myuserid).child("Vehicles").child("plate number").getValue(String.class);
                String carmakefromdb=snapshot.child(myuserid).child("Vehicles").child("make").getValue(String.class);
                String carmodelfromdb=snapshot.child(myuserid).child("Vehicles").child("model").getValue(String.class);


                bookname.setText(namefromdb);
                bookemail.setText(emailfromdb);
                bookphone.setText(phonefromdb);
                bookcarplate.setText(carplatefromdb);
                bookcarmake.setText(carmakefromdb);
                bookcarmodel.setText(carmodelfromdb);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void validation() {
        if (bookname.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter your name", Toast.LENGTH_SHORT).show();
        } else if (bookemail.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter your Email Address", Toast.LENGTH_SHORT).show();
        } else if (bookphone.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
        } else if (bookcarplate.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter Your Plate No.", Toast.LENGTH_SHORT).show();
        } else if (bookcarmake.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter Car Make", Toast.LENGTH_SHORT).show();
        } else if (bookcarmodel.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter Car Model", Toast.LENGTH_SHORT).show();
        } else if (timeto.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter Time", Toast.LENGTH_SHORT).show();
        } else if (timefrom.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter Time", Toast.LENGTH_SHORT).show();
        } else if (bookcarmodel.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter Your Username", Toast.LENGTH_SHORT).show();
        } else {
            adddatatofirebase(bookname.getText().toString(),bookemail.getText().toString(),bookphone.getText().toString(),bookcarplate.getText().toString(),bookcarmake.getText().toString(),bookcarmodel.getText().toString(),timeto.getText().toString(),timefrom.getText().toString());
            gotopaymentscreen();
        }
    }

    public void gotopaymentscreen()
    {
        Bundle bundle=new Bundle();
        bundle.putString("spotlocation",spotlocation);
        PaymentFragment paymentFragment=new PaymentFragment();
        paymentFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction=getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.spot,paymentFragment).commit();
        proceedtopay.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void adddatatofirebase(String bookname,String bookmail,String bookphone,String bookcarplate,String bookcarmake,String bookcarmodel,String timeto,String timefrom)
    {

        reference1= FirebaseDatabase.getInstance().getReference("Spots");
        reference.child(myuserid).child("Spot").setValue(spotlocation);
        reference1.child(spotlocation).child("fullname").setValue(bookname);
        reference1.child(spotlocation).child("email").setValue(bookmail);
        reference1.child(spotlocation).child("phone").setValue(bookphone);
        reference1.child(spotlocation).child("plate number").setValue(bookcarplate);
        reference1.child(spotlocation).child("make").setValue(bookcarmake);
        reference1.child(spotlocation).child("model").setValue(bookcarmodel);
        reference1.child(spotlocation).child("Timeto").setValue(timeto);
        reference1.child(spotlocation).child("Timefrom").setValue(timefrom);
        reference1.child(spotlocation).child("fees").setValue(pr);
    }
    public void Timefrom()
    {
        final Calendar cldr=Calendar.getInstance();
        int hour= cldr.get(Calendar.HOUR_OF_DAY);
        int min= cldr.get(Calendar.MINUTE);
        timePickerDialog=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDayfrom, int minute) {
                timefrom.setText(hourOfDayfrom+":"+minute);
                from=hourOfDayfrom;
            }
        },hour,min,true);
        timePickerDialog.show();
    }
    public void timeto()
    {
        final Calendar cldr=Calendar.getInstance();
        int hour= cldr.get(Calendar.HOUR_OF_DAY);
        int min= cldr.get(Calendar.MINUTE);
        timePickerDialog=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDayto, int minute) {
                timeto.setText(hourOfDayto+":"+minute);
                to=hourOfDayto;
            }
        },hour,min,true);
        timePickerDialog.show();
    }
    public void price()
    {
        int time=to-from;
        if(time <= 2)
        {
            pr="$6";
        }
        else if(time > 2 && time <=6)
        {
            pr="$9";
        }
        else {
            pr="$15";
        }

    }
}