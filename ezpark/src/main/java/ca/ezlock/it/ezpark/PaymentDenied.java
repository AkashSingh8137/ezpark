package ca.ezlock.it.ezpark;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import ca.ezlock.it.ezpark.databinding.FragmentPaymentdeniedBinding;
import ca.ezlock.it.ezpark.ui.settings.SettingsFragment;

public class PaymentDenied extends Fragment {
    private FragmentPaymentdeniedBinding binding;
    Button confirm;
    FirebaseAuth mAuth;
    DatabaseReference storageReference;
    String myuserid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentdeniedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        confirm=root.findViewById(R.id.confirm);
        mAuth=FirebaseAuth.getInstance();
        myuserid=mAuth.getUid();
        storageReference= FirebaseDatabase.getInstance().getReference();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm.setVisibility(View.INVISIBLE);
                storageReference.child(myuserid).child("Settings").child("access").setValue(false);
                SettingsFragment settingsFragment=new SettingsFragment();
                FragmentTransaction fr=getParentFragmentManager().beginTransaction();
                fr.replace(R.id.paymentdenied,settingsFragment).commit();
            }
        });



        return root;
    }
}