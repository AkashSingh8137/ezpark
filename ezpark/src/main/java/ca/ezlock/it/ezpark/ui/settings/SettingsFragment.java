package ca.ezlock.it.ezpark.ui.settings;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.ezlock.it.ezpark.LoginScreen;
import ca.ezlock.it.ezpark.R;
import ca.ezlock.it.ezpark.databinding.FragmentSettingBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingBinding binding;
    Button signoutbutton;
    private int STORAGE_PERMISSION_CODE = 1;
    FirebaseAuth mAuth;
    String myuserid;
    Switch accesstophotos,potrait,darkmode,location;
    DatabaseReference reference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        mAuth=FirebaseAuth.getInstance();
        myuserid=mAuth.getUid();
        accesstophotos=root.findViewById(R.id.accesstophotos);
        potrait=root.findViewById(R.id.potrait);
        darkmode=root.findViewById(R.id.darkmode);
        location=root.findViewById(R.id.location);
        reference= FirebaseDatabase.getInstance().getReference("Users");

        accesstophotos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(accesstophotos.isChecked())
                {
                    Toast.makeText(getContext(),"Permission Required",Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        signoutbutton =(Button)root.findViewById(R.id.signoutbutton);
        mAuth=FirebaseAuth.getInstance();
        signoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences=getContext().getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("remember","false");
                editor.apply();
                mAuth.signOut();
                Intent intent=new Intent(getContext().getApplicationContext(),LoginScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        getdatafromfirebase();

        potrait.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(potrait.isChecked())
                {
                    reference.child(myuserid).child("Settings").child("Potrait").setValue(true);
                }
                else
                {
                    reference.child(myuserid).child("Settings").child("Potrait").setValue(false);
                }
            }
        });
        darkmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(darkmode.isChecked())
                {
                    reference.child(myuserid).child("Settings").child("darkmode").setValue(true);
                }
                else
                {
                    reference.child(myuserid).child("Settings").child("darkmode").setValue(false);
                }
            }
        });
        location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(location.isChecked())
                {
                    reference.child(myuserid).child("Settings").child("location").setValue(true);

                }
                else
                {
                    reference.child(myuserid).child("Settings").child("location").setValue(false);
                }
            }
        });
        accesstophotos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(accesstophotos.isChecked())
                {
                    permission();

                }
                else
                {
                    reference.child(myuserid).child("Settings").child("access").setValue(false);
                }
            }
        });

        final TextView textView = binding.textSetting;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
    public void permission()
    {
        signoutbutton.setVisibility(View.GONE);
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission DENIED", Toast.LENGTH_SHORT).show();
                reference.child(myuserid).child("Settings").child("location").setValue(false);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void getdatafromfirebase() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean checkp = snapshot.child(myuserid).child("Settings").child("Potrait").getValue(Boolean.class);
                Boolean checka = snapshot.child(myuserid).child("Settings").child("access").getValue(Boolean.class);
                Boolean checkd = snapshot.child(myuserid).child("Settings").child("darkmode").getValue(Boolean.class);
                Boolean checkl = snapshot.child(myuserid).child("Settings").child("location").getValue(Boolean.class);

                potrait.setChecked(checkp);
                darkmode.setChecked(checkd);
                location.setChecked(checkl);
                accesstophotos.setChecked(checka);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void basicdatatofirebase()
    {
        reference.child(myuserid).child("Settings").child("Potrait").setValue(false);
        reference.child(myuserid).child("Settings").child("darkmode").setValue(false);
        reference.child(myuserid).child("Settings").child("location").setValue(false);
        reference.child(myuserid).child("Settings").child("access").setValue(false);
    }

}
