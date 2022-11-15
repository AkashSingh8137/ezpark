package ca.ezlock.it.ezpark.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.ezlock.it.ezpark.LoginScreen;
import ca.ezlock.it.ezpark.R;
import ca.ezlock.it.ezpark.databinding.FragmentProfilefragmentBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfilefragmentBinding binding;
    EditText profilename,profileemail,profilephone,carplate,carmake,carmodel;
    TextView profileusername;
    Button update;
    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= FragmentProfilefragmentBinding.inflate(inflater,container,false);
        View root=binding.getRoot();



        profilename=root.findViewById(R.id.profilename);
        profileusername=root.findViewById(R.id.profileusername);
        profileemail=root.findViewById(R.id.profileemail);
        profilephone=root.findViewById(R.id.profilePhone);
        carplate=root.findViewById(R.id.profileplatenumber);
        carmake=root.findViewById(R.id.profilecarmake);
        carmodel=root.findViewById(R.id.profilecarmodel);

        update=root.findViewById(R.id.updateprofile);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedata();
            }
        });



        getdata();
        return root;
    }
    public void getdata()
    {
        reference= FirebaseDatabase.getInstance().getReference("Registrationinfo");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namefromdb=snapshot.child("imakash").child("fullname").getValue(String.class);
                String usernamefromdb=snapshot.child("imakash").child("username").getValue(String.class);
                String emailfromdb=snapshot.child("imakash").child("email").getValue(String.class);
                String phonefromdb=snapshot.child("imakash").child("phone").getValue(String.class);

                profilename.setText(namefromdb);
                profileusername.setText(usernamefromdb);
                profileemail.setText(emailfromdb);
                profilephone.setText(phonefromdb);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void updatedata()
    {
        reference.child("imakash").child("fullname").setValue(profilename.getText().toString());
        reference.child("imakash").child("email").setValue(profileemail.getText().toString());
        reference.child("imakash").child("phone").setValue(profilephone.getText().toString());
        reference.child("imakash").child("vehicle").child("plate number").setValue(carplate.getText().toString());
        reference.child("imakash").child("vehicle").child("make").setValue(carmake.getText().toString());
        reference.child("imakash").child("vehicle").child("model").setValue(carmodel.getText().toString());

        Toast.makeText(getContext(),"Profile Updated",Toast.LENGTH_SHORT).show();


    }

}