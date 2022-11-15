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

import ca.ezlock.it.ezpark.ui.InformationsFragment.InformationsFragment;

public class SpotActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RadioButton location;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);

        Spinner spot=findViewById(R.id.spinner3);
        spot.setVisibility(View.VISIBLE);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(SpotActivity.this,R.array.spots,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spot.setAdapter(adapter);
        spot.setOnItemSelectedListener(this);
        spot.setVisibility(View.INVISIBLE);
        location=findViewById(R.id.location);
        location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(location.isChecked())
                {
                    spot.setVisibility(View.VISIBLE);
                    Toast.makeText(SpotActivity.this,"Humber College",Toast.LENGTH_SHORT).show();
                }
            }
        });
        button=findViewById(R.id.continuelocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setVisibility(View.GONE);
                InformationsFragment informationsFragment=new InformationsFragment();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.spot,informationsFragment).commit();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}