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

public class AskForEventDonation extends AppCompatActivity implements View.OnClickListener {

    private Button btn_ok, btn_cancel;

    private EditText eventName, eventDetails, eContactNumber, ebKashNumber, eTotalAmount;

    private TextView prograssBarText;

    private ProgressBar progressBar;

    private FirebaseDatabase firebaseDatabase,userDatabase;
    private DatabaseReference databaseReference, userRef, donationRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_for_event_donation);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Event Donation Requests");

        progressBar=findViewById(R.id.progressbar);
        prograssBarText=(TextView) findViewById(R.id.progressBarText);

        btn_ok=(Button) findViewById(R.id.event_button_ok);
        btn_cancel=(Button) findViewById(R.id.event_button_cancel);

        eventName=(EditText)findViewById(R.id.askedEventName);
        eventDetails=(EditText)findViewById(R.id.askedEventDetails);
        eContactNumber=(EditText)findViewById(R.id.askedEventContactNumber);
        ebKashNumber=(EditText)findViewById(R.id.askedEventbKashNumber);
        eTotalAmount=(EditText)findViewById(R.id.askedEventTotalAmount);


        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {

            case R.id.event_button_ok:
                eventDonation();
                break;

            case R.id.event_button_cancel:
                Intent b=new Intent(AskForEventDonation.this,AskForADonation.class);
                startActivity(b);
                break;
        }

    }

    private void eventDonation() {

        final String eName= eventName.getText().toString().trim();
        final String eDetails= eventDetails.getText().toString().trim();
        final String ContactNumber= eContactNumber.getText().toString().trim();
        final String bKashAccount= ebKashNumber.getText().toString().trim();
        final String totalAmount= eTotalAmount.getText().toString().trim();

        if(eName.isEmpty())
        {
            eventName.setError("Event Name is required");
            eventName.requestFocus();
            return;
        }

        if(eDetails.isEmpty())
        {
            eventDetails.setError("Event Details is required");
            eventDetails.requestFocus();
            return;
        }

        if(ContactNumber.isEmpty())
        {
            eContactNumber.setError("Contact Number is required");
            eContactNumber.requestFocus();
            return;
        }

        if(ContactNumber.length()<11|| ContactNumber.length()>11)
        {
            eContactNumber.setError("Contact Number must be of 11 digits");
            eContactNumber.requestFocus();
            return;
        }

        if(bKashAccount.isEmpty())
        {
            ebKashNumber.setError("bKash Account Number is required");
            ebKashNumber.requestFocus();
            return;
        }

        if(bKashAccount.length()<11 || bKashAccount.length()>11)
        {
            ebKashNumber.setError("bKash Account Number must be of 11 digits");
            ebKashNumber.requestFocus();
            return;
        }

        if(totalAmount.isEmpty())
        {
            eTotalAmount.setError("Total Amount is required");
            eTotalAmount.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        prograssBarText.setVisibility(View.VISIBLE);




        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        userDatabase = FirebaseDatabase.getInstance();
        userRef=userDatabase.getReference("Users").child(uid);

        String id=databaseReference.push().getKey();

        //String dId=firebaseDatabase.getReference("My Requests").child(uid).child("Event Requests").push().getKey();

        donationRef=firebaseDatabase.getReference("My Requests").child(uid).child("Event Requests").child(id);





        final DatabaseReference currentEDonationRequest = databaseReference.child(id);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String userName=dataSnapshot.child("UserName").getValue().toString();
                String collectedAmount="0";


                Map newRequest=new HashMap();
                newRequest.put("Name", eName);
                newRequest.put("Details", eDetails);
                newRequest.put("ContactNumber", ContactNumber);
                newRequest.put("BKashNumber",bKashAccount);
                newRequest.put("TotalAmount", totalAmount);
                newRequest.put("UserName", userName);
                newRequest.put("CollectedAmount", collectedAmount);
                newRequest.put("Uid", uid);

                currentEDonationRequest.setValue(newRequest);
                donationRef.setValue(newRequest);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        progressBar.setVisibility(View.GONE);
        prograssBarText.setVisibility(View.GONE);

        Intent intent=new Intent(AskForEventDonation.this, AskForADonation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Request is Send", Toast.LENGTH_SHORT).show();


    }
}
