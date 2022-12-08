package ca.ezlock.it.ezpark;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.ezlock.it.ezpark.databinding.FragmentConfirmationBinding;
import ca.ezlock.it.ezpark.databinding.FragmentProfilefragmentBinding;

public class ConfirmationFragment extends Fragment {
    FragmentConfirmationBinding binding;
    TextView parkspot,parkname,parkemail,parkphone,parkplate,parkmake,parkmodel,parkto,parkfrom;
    DatabaseReference reference,reference1;
    FirebaseAuth mAuth;
    String myuserid;
    String spotlocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentConfirmationBinding.inflate(inflater,container,false);
        View root=binding.getRoot();

        parkspot=root.findViewById(R.id.parkspot);
        parkname=root.findViewById(R.id.confirmname);
        parkemail=root.findViewById(R.id.confirmemail);
        parkphone=root.findViewById(R.id.confirmphone);
        parkplate=root.findViewById(R.id.confirmplate);
        parkmake=root.findViewById(R.id.confirmmake);
        parkmodel=root.findViewById(R.id.confirmmodel);
        parkfrom=root.findViewById(R.id.timefrom);
        parkto=root.findViewById(R.id.timeto);



                reference= FirebaseDatabase.getInstance().getReference("Spots");
        mAuth= FirebaseAuth.getInstance();
        myuserid=mAuth.getUid();
        reference1=FirebaseDatabase.getInstance().getReference("Users");
        getdatafromfirebase();

        return root;
    }
    public void getdatafromfirebase()
    {
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                spotlocation=snapshot.child(myuserid).child("Spot").getValue(String.class);
                if(spotlocation=="") {
                }
                else {
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String dbname = snapshot.child(spotlocation).child("fullname").getValue(String.class);
                            String dbemail = snapshot.child(spotlocation).child("email").getValue(String.class);
                            String dbphone = snapshot.child(spotlocation).child("phone").getValue(String.class);
                            String dbplate = snapshot.child(spotlocation).child("plate number").getValue(String.class);
                            String dbmake = snapshot.child(spotlocation).child("make").getValue(String.class);
                            String dbmodel = snapshot.child(spotlocation).child("model").getValue(String.class);
                            String dbtimeto = snapshot.child(spotlocation).child("Timeto").getValue(String.class);
                            String dbtimefrom = snapshot.child(spotlocation).child("Timefrom").getValue(String.class);

                            parkspot.setText(spotlocation);
                            parkname.setText(dbname);
                            parkemail.setText(dbemail);
                            parkphone.setText(dbphone);
                            parkphone.setText(dbphone);
                            parkplate.setText(dbplate);
                            parkmake.setText(dbmake);
                            parkmodel.setText(dbmodel);
                            parkfrom.setText(dbtimefrom);
                            parkto.setText(dbtimeto);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        parkspot.setText(spotlocation);
        /*reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dbname=snapshot.child("4").child("fullname").getValue(String.class);
                String dbemail=snapshot.child(spotlocation).child("email").getValue(String.class);
                String dbphone=snapshot.child(spotlocation).child("phone").getValue(String.class);
                String dbplate=snapshot.child(spotlocation).child("plate number").getValue(String.class);
                String dbmake=snapshot.child(spotlocation).child("make").getValue(String.class);
                String dbmodel=snapshot.child(spotlocation).child("model").getValue(String.class);
                String dbtimeto=snapshot.child(spotlocation).child("Timeto").getValue(String.class);
                String dbtimefrom=snapshot.child(spotlocation).child("Timefrom").getValue(String.class);

                parkspot.setText(spotlocation);
                parkname.setText(dbname);
                parkemail.setText(dbemail);
                parkphone.setText(dbphone);
                parkphone.setText(dbphone);
                parkplate.setText(dbplate);
                parkmake.setText(dbmake);
                parkmodel.setText(dbmodel);
                parkfrom.setText(dbtimefrom);
                parkto.setText(dbtimeto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }
}