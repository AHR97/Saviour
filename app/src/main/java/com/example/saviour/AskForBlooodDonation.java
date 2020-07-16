package com.example.saviour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AskForBlooodDonation extends AppCompatActivity implements View.OnClickListener {

    private Button btn_ok, btn_cancel;

    private EditText bDonationCity, bDonationArea, bDonationContactNumber, bDonationNumberOfBags, bDonationHospitalName;
    private Spinner bDonationGroup;

    private TextView prograssBarText;

    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase,userDatabase;
    private DatabaseReference databaseReference, userRef, donationRef;

    String[] allBloodGroups;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_for_bloood_donation);

        btn_ok=(Button) findViewById(R.id.blood_button_ok);
        btn_cancel=(Button) findViewById(R.id.blood_button_cancel);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Blood Donation Requests");

        progressBar=findViewById(R.id.progressbar);
        prograssBarText=(TextView) findViewById(R.id.progressBarText);

        bDonationCity=(EditText)findViewById(R.id.askedBloodDonationCity);
        bDonationArea=(EditText)findViewById(R.id.askedBloodDonationArea);
        bDonationContactNumber=(EditText)findViewById(R.id.askedBloodDonationContactNumber);
        bDonationNumberOfBags=(EditText)findViewById(R.id.askedBloodDonationBags);
        bDonationHospitalName=(EditText)findViewById(R.id.askedBloodDonationHospitalName);
        bDonationGroup=(Spinner) findViewById(R.id.askedBloodDonationGroup);

        allBloodGroups=getResources().getStringArray(R.array.Blood_group);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.sample_blood_group_items,R.id.sampleBloodGroup,allBloodGroups);
        bDonationGroup.setAdapter(adapter);


        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {

            case R.id.blood_button_ok:
                bloodDonation();
                break;

            case R.id.blood_button_cancel:
                Intent b=new Intent(AskForBlooodDonation.this,AskForADonation.class);
                startActivity(b);
                break;
        }

    }

    private void bloodDonation() {




        final String cityName= bDonationCity.getText().toString().trim();
        final String areaName= bDonationArea.getText().toString().trim();
        final String ContactNumber= bDonationContactNumber.getText().toString().trim();
        final String numberOfBags= bDonationNumberOfBags.getText().toString().trim();
        final String hospitalName= bDonationHospitalName.getText().toString().trim();
        final String bloodGroup= bDonationGroup.getSelectedItem().toString();
        final String flag="0";

        if(cityName.isEmpty())
        {
            bDonationCity.setError("City Name is required");
            bDonationCity.requestFocus();
            return;
        }

        if(areaName.isEmpty())
        {
            bDonationArea.setError("Area Name is required");
            bDonationArea.requestFocus();
            return;
        }

        if(ContactNumber.isEmpty())
        {
            bDonationContactNumber.setError("Contact Number is required");
            bDonationContactNumber.requestFocus();
            return;
        }

        if(ContactNumber.length()<11|| ContactNumber.length()>11)
        {
            bDonationContactNumber.setError("Contact Number must be of 11 digits");
            bDonationContactNumber.requestFocus();
            return;
        }

        if(numberOfBags.isEmpty())
        {
            bDonationNumberOfBags.setError("Number of Bags is required");
            bDonationNumberOfBags.requestFocus();
            return;
        }

        if(bloodGroup.length()>3)
        {
            Toast.makeText(getApplicationContext(),"Select A Blood Group",Toast.LENGTH_SHORT).show();
            bDonationGroup.requestFocus();
            return;
        }


        if(hospitalName.isEmpty())
        {
            bDonationHospitalName.setError("Hospital Name is required");
            bDonationHospitalName.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        prograssBarText.setVisibility(View.VISIBLE);
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        userDatabase = FirebaseDatabase.getInstance();
        userRef=userDatabase.getReference("Users").child(uid);
        String id=databaseReference.push().getKey();

        //String dId=firebaseDatabase.getReference("My Requests").child(uid).child("Blood Requests").push().getKey();

        donationRef=firebaseDatabase.getReference("My Requests").child(uid).child("Blood Requests").child(id);





        final DatabaseReference currentFDonationRequest = databaseReference.child(id);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String userName=dataSnapshot.child("UserName").getValue().toString();
                Map newRequest=new HashMap();
                newRequest.put("City", cityName);
                newRequest.put("Area", areaName);
                newRequest.put("ContactNumber", ContactNumber);
                newRequest.put("NumberOfBags", numberOfBags);
                newRequest.put("BloodGroup", bloodGroup);
                newRequest.put("Hospital",hospitalName);
                newRequest.put("UserName", userName);
                newRequest.put("Uid", uid);
                newRequest.put("Flag", flag);

                currentFDonationRequest.setValue(newRequest);
                donationRef.setValue(newRequest);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        progressBar.setVisibility(View.GONE);
        prograssBarText.setVisibility(View.GONE);

        Intent intent=new Intent(AskForBlooodDonation.this, AskForADonation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Request is Send", Toast.LENGTH_SHORT).show();

    }



}

