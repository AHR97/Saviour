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
import java.util.Timer;
import java.util.TimerTask;

public class AskForAMedicalDonation extends AppCompatActivity implements View.OnClickListener {

    private Button btn_ok, btn_cancel;

    private EditText mPatientName, mDesieaseName, mContactNumber, mbKashNumber, mTotalAmount;

    private TextView prograssBarText;

    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase,userDatabase;
    private DatabaseReference databaseReference, userRef, donationRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_for_amedical_donation);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Medical Donation Requests");

        progressBar=findViewById(R.id.progressbar);
        prograssBarText=(TextView) findViewById(R.id.progressBarText);

        btn_ok=(Button) findViewById(R.id.medical_button_ok);
        btn_cancel=(Button) findViewById(R.id.medical_button_cancel);

        mPatientName=(EditText)findViewById(R.id.askedMedicalPatientName);
        mDesieaseName=(EditText)findViewById(R.id.askedMedicalDiseaseName);
        mContactNumber=(EditText)findViewById(R.id.askedMedicalPatientContactNumber);
        mbKashNumber=(EditText)findViewById(R.id.askedMedicalPatientbKashNumber);
        mTotalAmount=(EditText)findViewById(R.id.askedMedicalPatientTotalAmount);


        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {

            case R.id.medical_button_ok:
                medicalDonation();
                break;

            case R.id.medical_button_cancel:
                Intent b=new Intent(AskForAMedicalDonation.this,AskForADonation.class);
                startActivity(b);
                break;
        }

    }

    private void medicalDonation() {



        final String patientName= mPatientName.getText().toString().trim();
        final String DiseaseName= mDesieaseName.getText().toString().trim();
        final String ContactNumber= mContactNumber.getText().toString().trim();
        final String bKashAccount= mbKashNumber.getText().toString().trim();
        final String totalAmount= mTotalAmount.getText().toString().trim();

        if(patientName.isEmpty())
        {
            mPatientName.setError("Patient Name is required");
            mPatientName.requestFocus();
            return;
        }

        if(DiseaseName.isEmpty())
        {
            mDesieaseName.setError("Disease Name is required");
            mDesieaseName.requestFocus();
            return;
        }

        if(ContactNumber.isEmpty())
        {
            mContactNumber.setError("Contact Number is required");
            mContactNumber.requestFocus();
            return;
        }

        if(ContactNumber.length()<11|| ContactNumber.length()>11)
        {
            mContactNumber.setError("Contact Number must be of 11 digits");
            mContactNumber.requestFocus();
            return;
        }

        if(bKashAccount.isEmpty())
        {
            mbKashNumber.setError("bKash Account Number is required");
            mbKashNumber.requestFocus();
            return;
        }

        if(bKashAccount.length()<11 || bKashAccount.length()>11)
        {
            mbKashNumber.setError("bKash Account Number must be of 11 digits");
            mbKashNumber.requestFocus();
            return;
        }

        if(totalAmount.isEmpty())
        {
            mTotalAmount.setError("Total Amount is required");
            mTotalAmount.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        prograssBarText.setVisibility(View.VISIBLE);


        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        userDatabase = FirebaseDatabase.getInstance();
        userRef=userDatabase.getReference("Users").child(uid);
        String id=databaseReference.push().getKey();

        //String dId=firebaseDatabase.getReference("My Requests").child(uid).child("Medical Requests").push().getKey();

        donationRef=firebaseDatabase.getReference("My Requests").child(uid).child("Medical Requests").child(id);



        final DatabaseReference currentEDonationRequest = databaseReference.child(id);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String userName=dataSnapshot.child("UserName").getValue().toString();
                String collectedAmount="0";
                Map newRequest=new HashMap();
                newRequest.put("Name", patientName);
                newRequest.put("Details", DiseaseName);
                newRequest.put("ContactNumber", ContactNumber);
                newRequest.put("BKashNumber",bKashAccount);
                newRequest.put("TotalAmount", totalAmount);
                newRequest.put("UserName", userName);
                newRequest.put("CollectedAmount", collectedAmount);
                newRequest.put("Uid", uid);
                donationRef.setValue(newRequest);
                currentEDonationRequest.setValue(newRequest);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
                progressBar.setVisibility(View.GONE);
                prograssBarText.setVisibility(View.GONE);

                Intent intent=new Intent(AskForAMedicalDonation.this, AskForADonation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Request is Send", Toast.LENGTH_SHORT).show();


            }



}


