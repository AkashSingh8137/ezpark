package ca.ezlock.it.ezpark;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ca.ezlock.it.ezpark.databinding.FragmentPaymentdeniedBinding;
import ca.ezlock.it.ezpark.ui.settings.SettingsFragment;

public class PaymentDenied extends Fragment {
    private FragmentPaymentdeniedBinding binding;
    Button confirm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentdeniedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        confirm=root.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsFragment settingsFragment=new SettingsFragment();
                FragmentTransaction fr=getParentFragmentManager().beginTransaction();
                fr.replace(R.id.paymentdenied,settingsFragment).commit();
            }
        });



        return root;
    }
}