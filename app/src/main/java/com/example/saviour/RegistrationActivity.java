package com.example.saviour;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity implements  View.OnClickListener{

    private TextView registrationSignInText, prograssBarText;;
    private Button  btn_registration;


    static int PReCode=1;
    static int REQUESCODE=1;

    private ProgressBar progressBar;
    private EditText userFullName, usedUserName, userEmailAdress, userAge, userAddress, userPhoneNumner,  userbKashAccount, userPassword;
    private Spinner userBloodGroup, userGender;
    private FirebaseAuth mAuth;

    String[] allBloodGroups, allGenders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        btn_registration= (Button) findViewById(R.id.registerButton);
        registrationSignInText=(TextView) findViewById(R.id.registerSignIn);


        btn_registration.setOnClickListener(this);
        registrationSignInText.setOnClickListener(this);

        progressBar=findViewById(R.id.progressbar);
        prograssBarText=(TextView) findViewById(R.id.progressBarText);

        userFullName=(EditText) findViewById(R.id.fullNameBox);
        usedUserName=(EditText) findViewById(R.id.userNameBox);
        userEmailAdress=(EditText) findViewById(R.id.emailBox);
        userAge=(EditText) findViewById(R.id.ageBox);
        userAddress=(EditText) findViewById(R.id.addressBox);
        userPhoneNumner=(EditText) findViewById(R.id.phoneNumberBox);
        userBloodGroup=(Spinner) findViewById(R.id.bloodGroupBox);
        userbKashAccount=(EditText) findViewById(R.id.bKashAccountBox);
        userPassword=(EditText) findViewById(R.id.passwordBox);
        userGender=(Spinner) findViewById(R.id.GenderBox);

        allBloodGroups=getResources().getStringArray(R.array.Blood_group);
        allGenders=getResources().getStringArray(R.array.Gender);

        ArrayAdapter<String> genderAdapter=new ArrayAdapter<String>(this,R.layout.sample_blood_group_items,R.id.sampleBloodGroup,allGenders);
        ArrayAdapter<String> bloodAdapter=new ArrayAdapter<String>(this,R.layout.sample_blood_group_items,R.id.sampleBloodGroup,allBloodGroups);

        userBloodGroup.setAdapter(bloodAdapter);
        userGender.setAdapter(genderAdapter);



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerButton:
                reisterUser();
                break;

            case R.id.registerSignIn:
                Intent b;
                b= new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(b);
                break;

        }

    }



//
//    private void checkAndRequestForPermission() {
//        if(ContextCompat.checkSelfPermission(RegistrationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED){
//            if(ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE))
//            {
//                Toast.makeText(RegistrationActivity.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();
//            }
//            else {
//                ActivityCompat.requestPermissions(RegistrationActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        PReCode);
//            }
//        }
//        else{
//            imageChooser();
//        }
//    }



//
//    private void imageChooser() {
//        Intent galleryIntent= new Intent((Intent.ACTION_GET_CONTENT));
//        galleryIntent.setType("image/*");
//        startActivityForResult(galleryIntent,REQUESCODE);
//
//    }

    private void reisterUser() {

        final String fullname= userFullName.getText().toString().trim();
        final String username= usedUserName.getText().toString().trim();
        final String email= userEmailAdress.getText().toString().trim();
        final String age= userAge.getText().toString().trim();
        final String address= userAddress.getText().toString().trim();
        final String phoneNumber= userPhoneNumner.getText().toString().trim();
        final String bloodGroup= userBloodGroup.getSelectedItem().toString();
        final String bKashAccount= userbKashAccount.getText().toString().trim();
        final String password= userPassword.getText().toString().trim();
        final String gender = userGender.getSelectedItem().toString();

        if(fullname.isEmpty())
        {
            userFullName.setError("Full Name is required");
            userFullName.requestFocus();
            return;
        }

        if(username.isEmpty())
        {
            usedUserName.setError("User Name is required");
            usedUserName.requestFocus();
            return;
        }

        if(email.isEmpty())
        {
            userEmailAdress.setError("Email is required");
            userEmailAdress.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            userEmailAdress.setError("Email is not valid");
            userEmailAdress.requestFocus();
            return;
        }

        if(age.isEmpty())
        {
            userAge.setError("Age is required");
            userAge.requestFocus();
            return;
        }

        if(address.isEmpty())
        {
            userAddress.setError("Address is required");
            userAddress.requestFocus();
            return;
        }

        if(phoneNumber.isEmpty())
        {
            userPhoneNumner.setError("Phone Number is required");
            userPhoneNumner.requestFocus();
            return;
        }

        if(phoneNumber.length()<11|| phoneNumber.length()>11)
        {
            userPhoneNumner.setError("Phone Number must be of 11 digits");
            userPhoneNumner.requestFocus();
            return;
        }

        if(bloodGroup.length()>3)
        {
            Toast.makeText(getApplicationContext(),"Select A Blood Group",Toast.LENGTH_SHORT).show();
            userBloodGroup.requestFocus();
            return;
        }

        if(gender.length()>6)
        {
            Toast.makeText(getApplicationContext(),"Select Gender",Toast.LENGTH_SHORT).show();
            userGender.requestFocus();
            return;
        }

        if(bKashAccount.isEmpty())
        {
            userbKashAccount.setError("bKash Account Number is required");
            userbKashAccount.requestFocus();
            return;
        }

        if(bKashAccount.length()<11 || bKashAccount.length()>11)
        {
            userbKashAccount.setError("bKash Account Number must be of 11 digits");
            userbKashAccount.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            userPassword.setError("Password is required");
            userPassword.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            userPassword.setError("Password must be 6 letter");
            userPassword.requestFocus();
            return;
        }

        btn_registration.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        prograssBarText.setVisibility(View.VISIBLE);


        //User Name Query
        Query userQuery=FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("UserName").equalTo(username);
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0)
                {
                    progressBar.setVisibility(View.GONE);
                    prograssBarText.setVisibility(View.GONE);
                    btn_registration.setVisibility(View.VISIBLE);
                    Toast.makeText(RegistrationActivity.this,"User Name already registered",Toast.LENGTH_SHORT).show();
                }else
                {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            prograssBarText.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                finish();
                                Toast.makeText(getApplicationContext(), "Registration is Successful", Toast.LENGTH_SHORT).show();

                                String userId = mAuth.getCurrentUser().getUid();
                                final DatabaseReference currentUserInfo = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                                DatabaseReference currentUserRatings = FirebaseDatabase.getInstance().getReference().child("Users Ratings").child(userId);


                                final Map newUser=new HashMap();
                                newUser.put("FullName", fullname);
                                newUser.put("UserName", username);
                                newUser.put("Email", email);
                                newUser.put("Age", age);
                                newUser.put("Gender", gender);
                                newUser.put("Address", address);
                                newUser.put("PhoneNumber", phoneNumber);
                                newUser.put("BloodGroup", bloodGroup);
                                newUser.put("bKashNumber", bKashAccount);
                                newUser.put("ProfileImageUrl", null);

                                currentUserInfo.setValue(newUser);



//                                if(resultUri !=null)
//                                {
//                                    final StorageReference filePath= FirebaseStorage.getInstance().getReference().child("Profile Images").child(userId);
//
//                                    Bitmap bitmap=null;
//
//                                    try {
//                                        bitmap= MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),resultUri);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                    ByteArrayOutputStream boss=new ByteArrayOutputStream();
//                                    bitmap.compress(Bitmap.CompressFormat.JPEG,20,boss);
//                                    byte[] data= boss.toByteArray();
//                                    UploadTask uploadTask=filePath.putBytes(data);
//
//                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                                @Override
//                                                public void onSuccess(Uri uri) {
//                                                    //Map newImage = new HashMap();
//                                                    //newImage.put("ProfileImageUrl", uri.toString());
//                                                    currentUserInfo.child("ProfileImageUrl").setValue(uri.toString());
//
//                                                   // newUser.put("ProfileImageUrl",uri.toString());
//
//                                                    finish();
//                                                    return;
//                                                }
//                                            }).addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception exception) {
//                                                    finish();
//                                                    return;
//                                                }
//                                            });
//                                        }
//
//                                    });
//
//                                }



                                Intent intent=new Intent(RegistrationActivity.this, UserProfile.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            } else {

                                //Toast.makeText(getApplicationContext(), "Registration is not Successful", Toast.LENGTH_SHORT).show();
                                btn_registration.setVisibility(View.VISIBLE);

                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(getApplicationContext(), "You have already registered with this email", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getApplicationContext(), "Registration is not Successful", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode== Activity.RESULT_OK && requestCode==REQUESCODE  && data!=null)
//        {
//            final Uri pickedImageUri=data.getData();
//            resultUri=pickedImageUri;
//            profileImage.setImageURI(resultUri);
//        }
//    }



}
