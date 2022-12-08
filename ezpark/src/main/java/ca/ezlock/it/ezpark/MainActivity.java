package ca.ezlock.it.ezpark;
// Akashdeep Singh n01458137 0NC

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ca.ezlock.it.ezpark.databinding.ActivityMainBinding;
import ca.ezlock.it.ezpark.ui.settings.SettingsViewModel;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    Button map,book;
    View layout;
    ImageView imageView8;
    Button goback;
    FirebaseAuth mAuth;
    DatabaseReference storageReference;
    String myuserid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        layout = findViewById(R.id.nav_host_fragment_activity_main);


        map=findViewById(R.id.map);
        mAuth= FirebaseAuth.getInstance();
        myuserid=mAuth.getUid();
        storageReference= FirebaseDatabase.getInstance().getReference();


        map.setVisibility(View.VISIBLE);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_confirmation, R.id.navigation_profile, R.id.navigation_settings)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        goback=findViewById(R.id.confirm);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(layout, "Permission Granted", Snackbar.LENGTH_SHORT).show();
            storageReference.child(myuserid).child("Settings").child("access").setValue(true);
        } else {
            Snackbar.make(layout, "Permission Denied", Snackbar.LENGTH_SHORT).show();
            storageReference.child(myuserid).child("Settings").child("access").setValue(false);
            PaymentDenied paymentDenied=new PaymentDenied();
            FragmentTransaction fr=getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.setting,paymentDenied).commit();

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Alert")
                .setIcon(R.drawable.ic_action_alert)
                .setMessage("Are you sure you want to EXIT!!")
                .setIcon(R.drawable.ic_action_alert)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.super.onBackPressed();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.topbar_menu,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId())
        {
            case R.id.Contact:

                Intent callIntent=new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:6476150058"));
                startActivity(callIntent);
                return true;
            case R.id.Help:
                Intent intent=new Intent(MainActivity.this,HelpActivity.class);
                startActivity(intent);
                return true;
            case R.id.addpayment:
                Intent intent1=new Intent(MainActivity.this,AddPayment.class);
                startActivity(intent1);
                return true;
            case R.id.save:

                ReviewFragment reviewFragment=new ReviewFragment();
                FragmentTransaction fr=getSupportFragmentManager().beginTransaction();

                map.setVisibility(View.INVISIBLE);
                fr.replace(R.id.home,reviewFragment).commit();



        }
        return super.onOptionsItemSelected(item);
    }
}