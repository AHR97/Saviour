package com.example.saviour;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SendMedicalMoney extends AppCompatActivity implements View.OnClickListener {
    ImageView send;
    private String collected, parent, newVal, user, name, details, contact, toatal, bKash, uid;
    EditText givenAMount;


    FloatingActionButton fabMain, fabOne, fabTwo, fabThree, fabFour;
    Float translationY = 100f;

    OvershootInterpolator interpolator = new OvershootInterpolator();

    Boolean isMenuOpen = false;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference, donationRef, myReqRef,userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_medical_money);

        Bundle bundle= getIntent().getExtras();



        parent=bundle.getString("Parent");
        collected=bundle.getString("Money");
        toatal=bundle.getString("Total");
        user=bundle.getString("User");
        name=bundle.getString("Name");
        contact=bundle.getString("Contact");
        bKash=bundle.getString("bKash");
        details=bundle.getString("Details");
        uid=bundle.getString("Uid");


        send=(ImageView)findViewById(R.id.send);
        send.setOnClickListener(this);

        givenAMount=(EditText)findViewById(R.id.moneyAmount);
        
        initFabMenu();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.send:
                Intent i=new Intent(SendMedicalMoney.this, TransitionProgress.class);


                ChangeAmount();
                DonatedMedical();

                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

            case R.id.fabMain:
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.fabOne:
                Intent a=new Intent(SendMedicalMoney.this,MainActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                break;

            case R.id.fabTwo:
                Intent b=new Intent(SendMedicalMoney.this,GiveADonation.class);
                b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(b);
                break;

            case R.id.fabThree:
                Intent c=new Intent(SendMedicalMoney.this,AskForADonation.class);
                c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(c);
                break;

            case R.id.fabFour:
                Intent d=new Intent(SendMedicalMoney.this,UserProfile.class);
                d.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(d);
                break;

        }

    }

    private void DonatedMedical() {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userRef=FirebaseDatabase.getInstance().getReference("Users").child(uid);

        final String dId=firebaseDatabase.getReference("My Donations").child(uid).child("Medical Donations").push().getKey();

        donationRef=firebaseDatabase.getReference("My Donations").child(uid).child("Medical Donations");

        final String amount=givenAMount.getText().toString();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map newRequest=new HashMap();
                newRequest.put("Name", name);
                newRequest.put("Details", details);
                newRequest.put("ContactNumber", contact);
                newRequest.put("BKashNumber",bKash);
                newRequest.put("TotalAmount", toatal);
                newRequest.put("UserName", user);
                newRequest.put("GivenAmount", amount);
                donationRef.child(dId).setValue(newRequest);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void ChangeAmount() {



        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Medical Donation Requests").child(parent);

        myReqRef=firebaseDatabase.getReference().child("My Requests").child(uid).child("Medical Requests");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String total=dataSnapshot.child("TotalAmount").getValue().toString();

                int val= Integer.parseInt(givenAMount.getText().toString());

                int oldtotal=Integer.parseInt(collected);
                int newtotal=oldtotal+val;

                int amount = Integer.parseInt(total);


                newVal=Integer.toString(newtotal);

                databaseReference.child("CollectedAmount").setValue(newVal);

                myReqRef.child(parent).child("CollectedAmount").setValue(newVal);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initFabMenu() {
        fabMain = findViewById(R.id.fabMain);
        fabOne = findViewById(R.id.fabOne);
        fabTwo = findViewById(R.id.fabTwo);
        fabThree = findViewById(R.id.fabThree);
        fabFour = findViewById(R.id.fabFour);


        fabOne.setAlpha(0f);
        fabTwo.setAlpha(0f);
        fabThree.setAlpha(0f);
        fabFour.setAlpha(0f);

        fabOne.setTranslationY(translationY);
        fabTwo.setTranslationY(translationY);
        fabThree.setTranslationY(translationY);
        fabFour.setTranslationY(translationY);

        fabMain.setOnClickListener(this);
        fabOne.setOnClickListener(this);
        fabTwo.setOnClickListener(this);
        fabThree.setOnClickListener(this);
        fabFour.setOnClickListener(this);
    }

    private void openMenu() {
        isMenuOpen = !isMenuOpen;

        fabMain.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();

        fabOne.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fabTwo.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fabThree.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fabFour.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();


    }


    private void closeMenu() {
        isMenuOpen = !isMenuOpen;

        fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();

        fabOne.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fabTwo.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fabThree.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fabFour.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();


    }




}
