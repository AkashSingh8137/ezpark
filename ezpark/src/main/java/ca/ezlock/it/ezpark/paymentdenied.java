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
public class paymentdenied extends Fragment {
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
                ConfirmationFragment confirmationFragment=new ConfirmationFragment();
                FragmentTransaction fr=getParentFragmentManager().beginTransaction();
                confirm.setVisibility(View.GONE);
                fr.replace(R.id.paymentdenied,confirmationFragment);

            }
        });



        return root;
    }
}