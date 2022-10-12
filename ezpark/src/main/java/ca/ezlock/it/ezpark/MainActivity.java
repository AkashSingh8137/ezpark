package ca.ezlock.it.ezpark;
// Akashdeep Singh n01458137 0NC

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ca.ezlock.it.ezpark.databinding.ActivityMainBinding;
import ca.ezlock.it.ezpark.ui.payment.PaymentFragment;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    Button btn;
    View layout;
    ImageView imageView8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        layout=findViewById(R.id.nav_host_fragment_activity_main);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_payment, R.id.navigation_information,R.id.navigation_settings)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


 /*
        imageView8 = findViewById(R.id.imageView8); // rotate the image
        imageView8.setRotation(90);

        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RotateAnimation rotateAnimation = new RotateAnimation(0,90,RotateAnimation.RELATIVE_TO_SELF,
                        .5f,RotateAnimation.RELATIVE_TO_SELF,.5f);
                rotateAnimation.setDuration(1);
                imageView8.startAnimation(rotateAnimation);

            }
        }); */


        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            Snackbar.make(layout,"Permission Granted",Snackbar.LENGTH_SHORT).show();
        }
        else
        {
            Snackbar.make(layout,"Permission Denied",Snackbar.LENGTH_SHORT).show();
            paymentdenied paymentdenied = new paymentdenied();
            FragmentTransaction fr=getSupportFragmentManager().beginTransaction();
            fr.replace(R.id.paymentdenied1,paymentdenied);
            fr.commit();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.topbar_menu,menu);
        return true;
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
}