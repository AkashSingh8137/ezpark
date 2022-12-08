package ca.ezlock.it.ezpark.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import ca.ezlock.it.ezpark.LoginScreen;
import ca.ezlock.it.ezpark.R;
import ca.ezlock.it.ezpark.databinding.FragmentProfilefragmentBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfilefragmentBinding binding;
    EditText profilename,profileemail,profilephone,carplate,carmake,carmodel;
    TextView profileusername;
    Button update;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    String myuserid;
    ImageView profile;
    ImageButton profilebtn;
    StorageReference storageReference;
    String profilefirebasename;
    StorageReference fileref;
    Uri imageuri;
    Boolean checka;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= FragmentProfilefragmentBinding.inflate(inflater,container,false);
        View root=binding.getRoot();
        mAuth=FirebaseAuth.getInstance();
        myuserid=mAuth.getUid();
        storageReference= FirebaseStorage.getInstance().getReference();
        profilefirebasename=myuserid+"profile.jpg";
        fileref=storageReference.child(profilefirebasename);

        profilename=root.findViewById(R.id.profilename);
        profileemail=root.findViewById(R.id.profileemail);
        profilephone=root.findViewById(R.id.profilePhone);
        carplate=root.findViewById(R.id.profileplatenumber);
        carmake=root.findViewById(R.id.profilecarmake);
        carmodel=root.findViewById(R.id.profilecarmodel);
        profile=root.findViewById(R.id.profilepic);
        profilebtn=root.findViewById(R.id.profilechange);

        reference= FirebaseDatabase.getInstance().getReference("Users");


        update=root.findViewById(R.id.updateprofile);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedata();
            }
        });


        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        checka = snapshot.child(myuserid).child("Settings").child("access").getValue(Boolean.class);
                        if(checka==true) {
                            Intent openGellery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(openGellery, 1000);
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Please allow access to photos in settings",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        });
        getdata();
        return root;
    }
    public void getdata()
    {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namefromdb=snapshot.child(myuserid).child("fullname").getValue(String.class);
                String emailfromdb=snapshot.child(myuserid).child("email").getValue(String.class);
                String phonefromdb=snapshot.child(myuserid).child("phone").getValue(String.class);
                String platefromdb=snapshot.child(myuserid).child("plate number").getValue(String.class);
                String makefromdb=snapshot.child(myuserid).child("make").getValue(String.class);
                String modelfromdb=snapshot.child(myuserid).child("model").getValue(String.class);

                profilename.setText(namefromdb);
                profileemail.setText(emailfromdb);
                profilephone.setText(phonefromdb);
                carplate.setText(platefromdb);
                carmake.setText(makefromdb);
                carmodel.setText(modelfromdb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void updatedata()
    {
        reference.child(myuserid).child("fullname").setValue(profilename.getText().toString());
        reference.child(myuserid).child("email").setValue(profileemail.getText().toString());
        reference.child(myuserid).child("phone").setValue(profilephone.getText().toString());
        reference.child(myuserid).child("Vehicles").child("plate number").setValue(carplate.getText().toString());
        reference.child(myuserid).child("Vehicles").child("make").setValue(carmake.getText().toString());
        reference.child(myuserid).child("Vehicles").child("model").setValue(carmodel.getText().toString());

        Toast.makeText(getContext(),"Profile Updated",Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                imageuri=data.getData();
                profile.setImageURI(imageuri);
                uploadimagetofirebase(imageuri);
            }
        }
    }
    private  void uploadimagetofirebase(Uri imageuri)
    {
        fileref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(),"Profile pic changed",Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Profile pic not changed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}