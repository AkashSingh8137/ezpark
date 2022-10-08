package ca.ezlock.it.ezpark.ui.InformationsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ca.ezlock.it.ezpark.databinding.FragmentInformationsBinding;


public class InformationsFragment extends Fragment {

    private FragmentInformationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InformationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(InformationsViewModel.class);

        binding = FragmentInformationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textInformations;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}