package ca.ezlock.it.ezpark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.ezlock.it.ezpark.ui.InformationsFragment.InformationsFragment;

public class SpotActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RadioButton location;
    Button button;
    Spinner spot;
    FirebaseAuth mAuth;
    String myuserid;
    DatabaseReference reference;
    Registrationinfo registrationinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);

        registrationinfo=new Registrationinfo();
        mAuth= FirebaseAuth.getInstance();
        myuserid=mAuth.getUid();
        reference= FirebaseDatabase.getInstance().getReference("Spots");
        spot = findViewById(R.id.spinner3);
        spot.setVisibility(View.VISIBLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(SpotActivity.this, R.array.spots, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spot.setAdapter(adapter);
        spot.setOnItemSelectedListener(this);
        spot.setVisibility(View.INVISIBLE);
        location = findViewById(R.id.location);
        location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (location.isChecked()) {
                    spot.setVisibility(View.VISIBLE);
                    Toast.makeText(SpotActivity.this, "Humber College", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button = findViewById(R.id.continuelocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void validation() {
        if (!location.isChecked()) {
            Toast.makeText(SpotActivity.this, "Please select location", Toast.LENGTH_SHORT).show();
        } else {
            String spotlocation=spot.getSelectedItem().toString();
            storetofirebase(spotlocation);
            button.setVisibility(View.GONE);
            Bundle bundle=new Bundle();
            bundle.putString("spotlocation",spotlocation);
            InformationsFragment informationsFragment = new InformationsFragment();
            ConfirmationFragment confirmationFragment=new ConfirmationFragment();
            informationsFragment.setArguments(bundle);
            confirmationFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.spot, informationsFragment).commit();
        }

    }
    public void storetofirebase(String spotlocation)
    {

        reference.setValue(spotlocation);
    }
}
