package com.example.saviour;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UserProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private DrawerLayout drawer;
    private TextView profileName, profileEmail, profileNumber, profileUserName, profileBlood, profileAddress, myReqNumber, myDonNumber, userRatings;
    private CardView myDonations, myRequests;


    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, userRef;

    ImageView profileImage;


    int myReqCount=0, myDonCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        @Nullable androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawer,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();


        profileName=(TextView)findViewById(R.id.userProfileName);
        profileEmail=(TextView)findViewById(R.id.userProfileEmail);
        profileNumber=(TextView)findViewById(R.id.userProfileNumber);
        profileUserName=(TextView)findViewById(R.id.profileUserName);
        profileAddress=(TextView)findViewById(R.id.userProfileAddress);
        profileBlood=(TextView)findViewById(R.id.userProfileBlood);
        myReqNumber=(TextView)findViewById(R.id.myRequestsNumber);
        myDonNumber=(TextView)findViewById(R.id.myDonationNumber);
        userRatings=(TextView)findViewById(R.id.profileUserRatings);


        myDonations=(CardView)findViewById(R.id.myDonation);
        myRequests=(CardView)findViewById(R.id.myRequests);

        profileImage=(ImageView)findViewById(R.id.userProfileImage);


        showUserInfo();

        showDonations();


    }

    private void showDonations() {

        myDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a;
                a=new Intent(UserProfile.this,MyDonation.class);
                startActivity(a);
            }
        });

        myRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b;
                b=new Intent(UserProfile.this,MyRequests.class);
                startActivity(b);
            }
        });
    }

    private void showUserInfo() {
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");



        final String userId = user.getUid();
        userRef = databaseReference.child(userId);


        DatabaseReference regCountRef=firebaseDatabase.getReference().child("My Requests").child(userId);


        regCountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count1=(int)dataSnapshot.child("Food Requests").getChildrenCount();
                int count2=(int)dataSnapshot.child("Cloth Requests").getChildrenCount();
                int count3=(int)dataSnapshot.child("Medical Requests").getChildrenCount();
                int count4=(int)dataSnapshot.child("Event Requests").getChildrenCount();
                int count5=(int)dataSnapshot.child("Blood Requests").getChildrenCount();

                myReqCount=count1+count2+count3+count4+count5;

                String count=Integer.toString(myReqCount);
                myReqNumber.setText(count);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference donCountRef=firebaseDatabase.getReference().child("My Donations").child(userId);


        donCountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count1=(int)dataSnapshot.child("Food Donations").getChildrenCount();
                int count2=(int)dataSnapshot.child("Cloth Donations").getChildrenCount();
                int count3=(int)dataSnapshot.child("Medical Donations").getChildrenCount();
                int count4=(int)dataSnapshot.child("Event Donations").getChildrenCount();
                int count5=(int)dataSnapshot.child("Blood Donations").getChildrenCount();

                myDonCount=count1+count2+count3+count4+count5;

                double val=myDonCount;
                double rattings=val/5.0;

                String valRat=Double.toString(rattings);

                String count=Integer.toString(myDonCount);
                myDonNumber.setText(count);
                userRatings.setText(valRat);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String username=dataSnapshot.child("UserName").getValue().toString();
                String name=dataSnapshot.child("FullName").getValue().toString();
                String email=dataSnapshot.child("Email").getValue().toString();
                String number=dataSnapshot.child("PhoneNumber").getValue().toString();
                String bloodGroup=dataSnapshot.child("BloodGroup").getValue().toString();
                String address=dataSnapshot.child("Address").getValue().toString();
                String gender=dataSnapshot.child("Gender").getValue().toString();

                if(gender=="Female")
                {
                    profileImage.setImageResource(R.drawable.mother);
                }else{
                    profileImage.setImageResource(R.drawable.son);
                }

                profileUserName.setText(username);
                profileName.setText(name);
                profileEmail.setText(email);
                profileNumber.setText(number);
                profileBlood.setText(bloodGroup);
                profileAddress.setText(address);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId())
        {

            case R.id.make_a_donation:
                Intent b = new Intent(UserProfile.this, GiveADonation.class);
                b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(b);
                break;

            case R.id.get_a_donation:
                Intent c = new Intent(UserProfile.this, AskForADonation.class);
                c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(c);
                break;

            case R.id.edit_profile:
                Intent d = new Intent(UserProfile.this, EditYourProfile.class);
                d.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(d);
                break;

            case R.id.user_logout:
                Intent e = new Intent(UserProfile.this, MainActivity.class);
                e.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(e);
                break;
        }

        return true;
    }



    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_charity);
            builder.setTitle("Logout");

            builder.setCancelable(false);


            builder.setMessage("Are you sure you want to Logout and Exit")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(UserProfile.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
    }
}
