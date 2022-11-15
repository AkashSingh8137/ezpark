package ca.ezlock.it.ezpark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.ezlock.it.ezpark.databinding.FragmentConfirmationBinding;

public class ConfirmationFragment extends Fragment {
    FragmentConfirmationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentConfirmationBinding.inflate(inflater,container,false);
        View root=binding.getRoot();

        return root;
    }
}