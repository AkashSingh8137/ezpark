package ca.ezlock.it.ezpark;

import static ca.ezlock.it.ezpark.R.layout.activity_login_screen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.car.ui.toolbar.Tab;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

public class LoginScreen extends AppCompatActivity {
    private Button btn,register;
    private EditText e1,e2;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;
    CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login_screen);
        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
        if(checkbox.equals("true"))
        {
            Intent intent=new Intent(LoginScreen.this,MainActivity.class);
            startActivity(intent);
        }


        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        signInButton=findViewById(R.id.google_btn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        signIn();


            }
        });
        btn=findViewById(R.id.logbutton);
        e1=findViewById(R.id.email);
        e2=findViewById(R.id.Password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e1.getText().toString().equals("admin") && e2.getText().toString().equals("admin"))
                {
                    Intent i = new Intent(LoginScreen.this, MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(LoginScreen.this, "Username or Password incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
        remember= findViewById(R.id.rememberme);
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                }
                else if(!compoundButton.isChecked())
                {
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                }
            }
        });
        register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toregistrationscreen();
            }
        });



    }

    void toregistrationscreen()
    {
        Intent intent=new Intent(LoginScreen.this,RegistrationScreen.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
    }
    void signIn()
    {
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000)
        {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);


        }
    }
    public void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try {
            GoogleSignInAccount account=completedTask.getResult(ApiException.class);

        }
        catch (ApiException e)
        {
            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
        }
    }
}