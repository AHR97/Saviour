package com.example.saviour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class AskForAFoodDonation extends AppCompatActivity implements View.OnClickListener {

    private Button btn_ok, btn_cancel;
    private EditText fDonationCity, fDonationArea, fDonationContactNumber, fDonationNumberOfPeople, fDonationPlace, fDonationReason;

    private TextView prograssBarText;

    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase,userDatabase;
    int nCount;


    private DatabaseReference databaseReference, userRef,donationRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_for_afood_donation);

        btn_ok=(Button) findViewById(R.id.food_button_ok);
        btn_cancel=(Button) findViewById(R.id.food_button_cancel);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Food Donation Requests");




        progressBar=findViewById(R.id.progressbar);
        prograssBarText=(TextView) findViewById(R.id.progressBarText);

        fDonationCity=(EditText)findViewById(R.id.askedFoodDonationCity);
        fDonationArea=(EditText)findViewById(R.id.askedFoodDonationArea);
        fDonationContactNumber=(EditText)findViewById(R.id.askedFoodDonationContactNumber);
        fDonationNumberOfPeople=(EditText)findViewById(R.id.askedFoodDonationPeopleNumber);
        fDonationPlace=(EditText)findViewById(R.id.askedFoodDonationPlace);
        fDonationReason=(EditText)findViewById(R.id.askedFoodDonationReason);

        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {

            case R.id.food_button_ok:
                foodDonation();
                break;

            case R.id.food_button_cancel:
                Intent b=new Intent(AskForAFoodDonation.this,AskForADonation.class);
                startActivity(b);
                break;
        }

    }

    private void foodDonation() {




        final String cityName= fDonationCity.getText().toString().trim();
        final String areaName= fDonationArea.getText().toString().trim();
        final String ContactNumber= fDonationContactNumber.getText().toString().trim();
        final String numberOfPeople= fDonationNumberOfPeople.getText().toString().trim();
        final String place= fDonationPlace.getText().toString().trim();
        final String reason= fDonationReason.getText().toString().trim();
        final String flag="0";

        if(cityName.isEmpty())
        {
            fDonationCity.setError("City Name is required");
            fDonationCity.requestFocus();
            return;
        }

        if(areaName.isEmpty())
        {
            fDonationArea.setError("Area Name is required");
            fDonationArea.requestFocus();
            return;
        }

        if(ContactNumber.isEmpty())
        {
            fDonationContactNumber.setError("Contact Number is required");
            fDonationContactNumber.requestFocus();
            return;
        }

        if(ContactNumber.length()<11|| ContactNumber.length()>11)
        {
            fDonationContactNumber.setError("Contact Number must be of 11 digits");
            fDonationContactNumber.requestFocus();
            return;
        }

        if(numberOfPeople.isEmpty())
        {
            fDonationNumberOfPeople.setError("Number of people is required");
            fDonationNumberOfPeople.requestFocus();
            return;
        }

        if(reason.isEmpty())
        {
            fDonationReason.setError("This field cannot be empty");
            fDonationReason.requestFocus();
            return;
        }


        if(place.isEmpty())
        {
            fDonationPlace.setError("Place Name is required");
            fDonationPlace.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        prograssBarText.setVisibility(View.VISIBLE);


        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        userDatabase = FirebaseDatabase.getInstance();
        userRef=userDatabase.getReference("Users").child(uid);




        String id=databaseReference.push().getKey();

        final DatabaseReference currentFDonationRequest = databaseReference.child(id);

        //String dId=firebaseDatabase.getReference("My Requests").child(uid).child("Food Requests").push().getKey();

        donationRef=firebaseDatabase.getReference("My Requests").child(uid).child("Food Requests").child(id);


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String userName=dataSnapshot.child("UserName").getValue().toString();


                Map newRequest=new HashMap();
                newRequest.put("City", cityName);
                newRequest.put("Area", areaName);
                newRequest.put("ContactNumber", ContactNumber);
                newRequest.put("NumberOfPeople", numberOfPeople);
                newRequest.put("Reason", reason);
                newRequest.put("Place",place);
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

        Intent intent=new Intent(AskForAFoodDonation.this, AskForADonation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Request is Send", Toast.LENGTH_SHORT).show();

    }
}


