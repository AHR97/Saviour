package com.example.saviour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditYourProfile extends AppCompatActivity  {

    private Button save_info;
    EditText editProfileFullName, editProfileEmail, editProfileNumber, editProfileUserName, editProfileAddress, editProfileAge, editProfilebKashNumber,editProfilePassword ;

    Spinner editProfileBlood , editProfileGender;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, userRef;

    String[] allBloodGroups, allGenders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_your_profile);

        editProfileFullName=(EditText)findViewById(R.id.editFullNameBox);
        editProfileNumber=(EditText)findViewById(R.id.editPhoneNumberBox);
        editProfileUserName=(EditText)findViewById(R.id.editUserNameBox);
        editProfileAddress=(EditText)findViewById(R.id.editAddressBox);
        editProfileBlood=(Spinner)findViewById(R.id.editBloodGroupBox);
        editProfileGender=(Spinner)findViewById(R.id.GenderBox);
        editProfileAge=(EditText)findViewById(R.id.editAgeBox);
        editProfilebKashNumber=(EditText)findViewById(R.id.editbKashAccountBox);

        editProfileEmail=(EditText)findViewById(R.id.editEmailBox);
        
        save_info=(Button) findViewById(R.id.save_information);

        save_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeInfo();
            }
        });


        allBloodGroups=getResources().getStringArray(R.array.Blood_group);
        allGenders=getResources().getStringArray(R.array.Gender);

        ArrayAdapter<String> genderAdapter=new ArrayAdapter<String>(this,R.layout.sample_blood_group_items,R.id.sampleBloodGroup,allGenders);
        ArrayAdapter<String> bloodAdapter=new ArrayAdapter<String>(this,R.layout.sample_blood_group_items,R.id.sampleBloodGroup,allBloodGroups);

        editProfileGender.setAdapter(genderAdapter);
        editProfileBlood.setAdapter(bloodAdapter);


        showUserInfo();


    }

    private void showUserInfo() {
        
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");

        String userId = user.getUid();
        userRef = databaseReference.child(userId);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String username=dataSnapshot.child("UserName").getValue().toString();
                String name=dataSnapshot.child("FullName").getValue().toString();
                String email=dataSnapshot.child("Email").getValue().toString();
                String number=dataSnapshot.child("PhoneNumber").getValue().toString();
                String address=dataSnapshot.child("Address").getValue().toString();
                String age=dataSnapshot.child("Age").getValue().toString();
                String bkashAccount=dataSnapshot.child("bKashNumber").getValue().toString();



                editProfileUserName.setText(username);
                editProfileFullName.setText(name);
                editProfileEmail.setText(email);
                editProfileNumber.setText(number);
                editProfileAddress.setText(address);
                editProfileAge.setText(age);
                editProfilebKashNumber.setText(bkashAccount);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    private void ChangeInfo() {
        String newusername=editProfileUserName.getText().toString().trim();
        String newname=editProfileFullName.getText().toString().trim();
        String newemail=editProfileEmail.getText().toString().trim();
        String newnumber=editProfileNumber.getText().toString().trim();
        String newbloodGroup=editProfileBlood.getSelectedItem().toString();
        String newGender=editProfileGender.getSelectedItem().toString();
        String newaddress=editProfileAddress.getText().toString().trim();
        String newage=editProfileAge.getText().toString().trim();
        String newbkashAccount=editProfilebKashNumber.getText().toString().trim();





        userRef.child("UserName").setValue(newusername);
        userRef.child("FullName").setValue(newname);
        userRef.child("PhoneNumber").setValue(newnumber);
        userRef.child("BloodGroup").setValue(newbloodGroup);
        userRef.child("Address").setValue(newaddress);
        userRef.child("Age").setValue(newage);
        userRef.child("bKashNumber").setValue(newbkashAccount);
        userRef.child("Gender").setValue(newGender);

        user.updateEmail(newemail) .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Email Updated",Toast.LENGTH_SHORT).show();
                }
            }
        });




        Intent i=new Intent(EditYourProfile.this,UserProfile.class);
        startActivity(i);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
    }
}
