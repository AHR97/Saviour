package com.example.saviour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfirmedClothRequest extends AppCompatActivity implements View.OnClickListener {

    String parent;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference, myReqRef, donRef;


    Button confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_cloth_request);
        confirm=(Button)findViewById(R.id.button);




        Bundle bundle= getIntent().getExtras();


        parent=bundle.getString("Parent");


        confirm.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button:
                confirmReq();
                Intent intent=new Intent(ConfirmedClothRequest.this,MyRequests.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

        }

    }

    private void confirmReq() {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseDatabase=FirebaseDatabase.getInstance();



        databaseReference=firebaseDatabase.getReference("Food Donation Requests").child(parent);

        myReqRef=firebaseDatabase.getReference().child("My Requests").child(uid).child("Food Requests");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String flag="1";

                myReqRef.child(parent).child("Flag").setValue(flag);
                databaseReference.child("Flag").setValue(flag);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
