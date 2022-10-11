package ca.ezlock.it.ezpark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ca.ezlock.it.ezpark.databinding.FragmentPaymentBinding;
import ca.ezlock.it.ezpark.databinding.FragmentPaymentdeniedBinding;
import ca.ezlock.it.ezpark.databinding.FragmentSettingBinding;
import ca.ezlock.it.ezpark.ui.payment.PaymentFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link paymentdenied#newInstance} factory method to
 * create an instance of this fragment.
 */
public class paymentdenied extends Fragment {
    private FragmentPaymentdeniedBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentdeniedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }
}