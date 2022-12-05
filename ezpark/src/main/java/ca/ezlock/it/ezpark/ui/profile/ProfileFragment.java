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

import com.google.firebase.auth.FirebaseAuth;
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
    FirebaseAuth mAuth;
    String myuserid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= FragmentProfilefragmentBinding.inflate(inflater,container,false);
        View root=binding.getRoot();
        mAuth=FirebaseAuth.getInstance();
        myuserid=mAuth.getUid();


        profilename=root.findViewById(R.id.profilename);
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
        reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namefromdb=snapshot.child(myuserid).child("fullname").getValue(String.class);
                String emailfromdb=snapshot.child(myuserid).child("email").getValue(String.class);
                String phonefromdb=snapshot.child(myuserid).child("phone").getValue(String.class);
                String platefromdb=snapshot.child(myuserid).child("plate number").getValue(String.class);
                String makefromdb=snapshot.child(myuserid).child("make").getValue(String.class);
                String modelfromdb=snapshot.child(myuserid).child("model").getValue(String.class);

                profilename.setText(namefromdb);
                profileemail.setText(emailfromdb);
                profilephone.setText(phonefromdb);
                carplate.setText(platefromdb);
                carmake.setText(makefromdb);
                carmodel.setText(modelfromdb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void updatedata()
    {
        reference.child(myuserid).child("fullname").setValue(profilename.getText().toString());
        reference.child(myuserid).child("email").setValue(profileemail.getText().toString());
        reference.child(myuserid).child("phone").setValue(profilephone.getText().toString());
        reference.child(myuserid).child("plate number").setValue(carplate.getText().toString());
        reference.child(myuserid).child("make").setValue(carmake.getText().toString());
        reference.child(myuserid).child("model").setValue(carmodel.getText().toString());

        Toast.makeText(getContext(),"Profile Updated",Toast.LENGTH_SHORT).show();


    }

}