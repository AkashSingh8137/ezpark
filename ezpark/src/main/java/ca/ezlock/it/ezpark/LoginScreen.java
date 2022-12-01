package ca.ezlock.it.ezpark;

import static ca.ezlock.it.ezpark.R.layout.activity_login_screen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import ca.ezlock.it.ezpark.ui.profile.ProfileFragment;

public class LoginScreen extends AppCompatActivity {
    private Button btn,register;
    private EditText e1,e2;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;
    CheckBox remember;
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN=100;
    public String usernamefromdb;
    String username;

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


        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        signInButton=findViewById(R.id.google_btn);
        mAuth=FirebaseAuth.getInstance();
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
                if(validateusername()==true && validateusername() ==true)
                {
                    isUser();
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
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
            GoogleSignInAccount account= completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);

        }
        catch (ApiException e)
        {
            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount account)
    {
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginScreen.this,user.getEmail(),Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        }
                        else
                        {
                            Toast.makeText(LoginScreen.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user)
    {
        Intent intent=new Intent(LoginScreen.this,MainActivity.class);
        startActivity(intent);
    }
    private Boolean validateusername()
    {
        if(e1.getText().toString().equals(""))
        {
            Toast.makeText(LoginScreen.this,"Please Enter Username",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }

    }
    private Boolean validatepassword()
    {
        if(e2.getText().toString().equals(""))
        {
            Toast.makeText(LoginScreen.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }

    }
    private void isUser()
    {
        mAuth.signInWithEmailAndPassword(e1.getText().toString(),e2.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent intent=new Intent(LoginScreen.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginScreen.this,"Login Failed,Check Username or password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public String username()
    {
        return usernamefromdb;
    }
}