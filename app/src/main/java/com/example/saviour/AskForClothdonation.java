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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AskForClothdonation extends AppCompatActivity implements View.OnClickListener {

    private Button btn_ok, btn_cancel;

    private EditText cDonationCity, cDonationArea, cDonationContactNumber,  cDonationPlace;
    private Spinner  cDonationType;

    private TextView prograssBarText;

    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase,userDatabase;
    private DatabaseReference databaseReference, userRef,donationRef;

    String[] clothType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_for_clothdonation);

        btn_ok=(Button) findViewById(R.id.cloth_button_ok);
        btn_cancel=(Button) findViewById(R.id.cloth_button_cancel);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Cloth Donation Requests");

        progressBar=findViewById(R.id.progressbar);
        prograssBarText=(TextView) findViewById(R.id.progressBarText);

        cDonationCity=(EditText)findViewById(R.id.askedClothDonationCity);
        cDonationArea=(EditText)findViewById(R.id.askedClothDonationArea);
        cDonationContactNumber=(EditText)findViewById(R.id.askedClothDonationContactNumber);
        cDonationPlace=(EditText)findViewById(R.id.askedClothDonationPlace);
        cDonationType=(Spinner)findViewById(R.id.askedClothType);

        clothType=getResources().getStringArray(R.array.Cloths);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.sample_blood_group_items,R.id.sampleBloodGroup,clothType);
        cDonationType.setAdapter(adapter);

        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {

            case R.id.cloth_button_ok:
                clothDonation();
                break;

            case R.id.cloth_button_cancel:
                Intent b=new Intent(AskForClothdonation.this,AskForADonation.class);
                startActivity(b);
                break;
        }

    }

    private void clothDonation() {



        final String cityName= cDonationCity.getText().toString().trim();
        final String areaName= cDonationArea.getText().toString().trim();
        final String ContactNumber= cDonationContactNumber.getText().toString().trim();
        final String place= cDonationPlace.getText().toString().trim();
        final String type= cDonationType.getSelectedItem().toString();
        final String flag="0";

        if(cityName.isEmpty())
        {
            cDonationCity.setError("City Name is required");
            cDonationCity.requestFocus();
            return;
        }

        if(areaName.isEmpty())
        {
            cDonationArea.setError("Area Name is required");
            cDonationArea.requestFocus();
            return;
        }

        if(ContactNumber.isEmpty())
        {
            cDonationContactNumber.setError("Contact Number is required");
            cDonationContactNumber.requestFocus();
            return;
        }

        if(ContactNumber.length()<11|| ContactNumber.length()>11)
        {
            cDonationContactNumber.setError("Contact Number must be of 11 digits");
            cDonationContactNumber.requestFocus();
            return;
        }
        

        if(type.length()>6)
        {
            Toast.makeText(getApplicationContext(),"Select Cloth Type",Toast.LENGTH_SHORT).show();
            cDonationType.requestFocus();
            return;
        }


        if(place.isEmpty())
        {
            cDonationPlace.setError("Place Name is required");
            cDonationPlace.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        prograssBarText.setVisibility(View.VISIBLE);



        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        userDatabase = FirebaseDatabase.getInstance();
        userRef=userDatabase.getReference("Users").child(uid);

        String id=databaseReference.push().getKey();

       // String dId=firebaseDatabase.getReference("My Requests").child(uid).child("Cloth Requests").push().getKey();

        donationRef=firebaseDatabase.getReference("My Requests").child(uid).child("Cloth Requests").child(id);




        final DatabaseReference currentCDonationRequest = databaseReference.child(id);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String userName=dataSnapshot.child("UserName").getValue().toString();
                Map newRequest=new HashMap();
                newRequest.put("City", cityName);
                newRequest.put("Area", areaName);
                newRequest.put("ContactNumber", ContactNumber);
                newRequest.put("Place",place);
                newRequest.put("Category", type+" Cloths");
                newRequest.put("UserName", userName);
                newRequest.put("Uid", uid);
                newRequest.put("Flag", flag);

                currentCDonationRequest.setValue(newRequest);
                donationRef.setValue(newRequest);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        progressBar.setVisibility(View.GONE);
        prograssBarText.setVisibility(View.GONE);

        Intent intent=new Intent(AskForClothdonation.this, AskForADonation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Request is Send", Toast.LENGTH_SHORT).show();
    }
}

