package ca.ezlock.it.ezpark.ui.InformationsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.ezlock.it.ezpark.R;
import ca.ezlock.it.ezpark.databinding.FragmentInformationsBinding;
import ca.ezlock.it.ezpark.ui.payment.PaymentFragment;


public class InformationsFragment extends Fragment {

    private FragmentInformationsBinding binding;

    CheckBox saveprofile;
    EditText bookname,bookemail,bookphone,bookcarplate,bookcarmake,bookcarmodel,timefrom,timeto;
    DatabaseReference reference;
    Button proceedtopay;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InformationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(InformationsViewModel.class);

        binding = FragmentInformationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        bookname=root.findViewById(R.id.bookname);
        bookemail=root.findViewById(R.id.bookemail);
        bookphone=root.findViewById(R.id.bookphone);
        bookcarmake=root.findViewById(R.id.bookcarmake);
        bookcarplate=root.findViewById(R.id.bookcarplate);
        bookcarmodel=root.findViewById(R.id.bookcarmodel);
        timefrom=root.findViewById(R.id.timefrom);
        timeto=root.findViewById(R.id.timeto);
        proceedtopay=root.findViewById(R.id.proceedtopay);

        proceedtopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotopaymentscreen();
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

        return root;
    }
    public void writedata()
    {
        reference= FirebaseDatabase.getInstance().getReference("Registrationinfo");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namefromdb=snapshot.child("imakash").child("fullname").getValue(String.class);
                String emailfromdb=snapshot.child("imakash").child("email").getValue(String.class);
                String phonefromdb=snapshot.child("imakash").child("phone").getValue(String.class);
                String carplatefromdb=snapshot.child("imakash").child("vehicle").child("plate number").getValue(String.class);
                String carmakefromdb=snapshot.child("imakash").child("vehicle").child("make").getValue(String.class);
                String carmodelfromdb=snapshot.child("imakash").child("vehicle").child("model").getValue(String.class);


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
    public void gotopaymentscreen()
    {
        PaymentFragment paymentFragment=new PaymentFragment();
        FragmentTransaction fragmentTransaction=getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.spot,paymentFragment).commit();
        proceedtopay.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}