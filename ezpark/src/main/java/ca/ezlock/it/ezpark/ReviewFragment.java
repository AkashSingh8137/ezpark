package ca.ezlock.it.ezpark;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.ezlock.it.ezpark.databinding.FragmentReviewBinding;

public class ReviewFragment extends Fragment {
    FragmentReviewBinding binding;
    Button confirm;
    EditText ratingname, ratingphone, ratingemail, ratingcomments;
    RatingBar ratingBar;
    float getRating;
    Registrationinfo registrationinfo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ratingname = root.findViewById(R.id.ratingname);
        ratingphone = root.findViewById(R.id.ratingphoneno);
        ratingemail = root.findViewById(R.id.ratingemail);
        ratingcomments = root.findViewById(R.id.ratingcomment);
        ratingBar = root.findViewById(R.id.ratingBar);
        getRating = ratingBar.getRating();

        registrationinfo=new Registrationinfo();


        confirm = root.findViewById(R.id.submit);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkvalidation();
            }
        });


        return root;
    }

    private void addDataToFirebase(String ratingname, String ratingemail, String ratingphone, String ratingcomments, float getRating) {
        registrationinfo.setRatingname(ratingname);
        registrationinfo.setRatingemail(ratingemail);
        registrationinfo.setRatingphone(ratingphone);
        registrationinfo.setRatingcomments(ratingcomments);
        registrationinfo.setRatingstar(getRating);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Reviews");

        reference.child(ratingname).setValue(registrationinfo);
        Toast.makeText(getContext(), "Thanks! for the Review", Toast.LENGTH_SHORT).show();
        ConfirmationFragment confirmationFragment=new ConfirmationFragment();
        ReviewFragment reviewFragment=new ReviewFragment();
        FragmentTransaction fr=getParentFragmentManager().beginTransaction();
        confirm.setVisibility(View.GONE);
        fr.replace(R.id.payment,confirmationFragment).commit();



    }

    public void checkvalidation() {
        if (ratingname.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter your name", Toast.LENGTH_SHORT).show();
        } else if (ratingemail.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter your Email Address", Toast.LENGTH_SHORT).show();
        } else if (ratingphone.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
        } else if (ratingcomments.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Please enter any suggestion", Toast.LENGTH_SHORT).show();
        } else {
            addDataToFirebase((ratingname.getText().toString()), (ratingemail.getText().toString()), (ratingphone.getText().toString()), (ratingcomments.getText().toString()), ratingBar.getRating());
        }
    }

    public void gotoconfirmation() {
        ConfirmationFragment confirmationFragment = new ConfirmationFragment();
        FragmentTransaction fr = getParentFragmentManager().beginTransaction();
        confirm.setVisibility(View.INVISIBLE);
        fr.replace(R.id.spot, confirmationFragment).commit();
    }
}
