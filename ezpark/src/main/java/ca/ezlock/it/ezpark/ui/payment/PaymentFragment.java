package ca.ezlock.it.ezpark.ui.payment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import ca.ezlock.it.ezpark.ConfirmationFragment;
import ca.ezlock.it.ezpark.MainActivity;
import ca.ezlock.it.ezpark.R;
import ca.ezlock.it.ezpark.ReviewFragment;
import ca.ezlock.it.ezpark.databinding.FragmentPaymentBinding;

public class PaymentFragment extends Fragment {

    private FragmentPaymentBinding binding;
    private int STORAGE_PERMISSION_CODE = 1;
    Button paymentbtn;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        paymentbtn = (Button) root.findViewById(R.id.pay);
        paymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ConfirmationFragment confirmationFragment=new ConfirmationFragment();
                ReviewFragment reviewFragment=new ReviewFragment();
                FragmentTransaction fr=getParentFragmentManager().beginTransaction();
                paymentbtn.setVisibility(View.GONE);
                fr.replace(R.id.payment,reviewFragment).commit();
            }
        });
        /*paymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getActivity(),"Recipt Saved in Photos",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    requestStoragePermission();
                    ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE);

                }
            }
        });


        return root;
    }
    private void requestStoragePermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Recipt")
                .setIcon(R.drawable.receipt)
                .setMessage("Do you save the Recipt?")
                .setIcon(R.drawable.ic_action_alert)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        paymentbtn.setVisibility(View.GONE);
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
        return root;
    }
}