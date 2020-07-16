package com.example.saviour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyDonatedMedical extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseReference;
    private ListView listView;

    FloatingActionButton fabMain, fabOne, fabTwo, fabThree, fabFour;
    Float translationY = 100f;

    OvershootInterpolator interpolator = new OvershootInterpolator();


    Boolean isMenuOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donated_medical);
        listView=(ListView) findViewById(R.id.medicalListViewId);

        initFabMenu();

        databaseReference= FirebaseDatabase.getInstance().getReference();
        final ArrayList<DonatedMedicalListItem> mDonationList = new ArrayList<>();
        final ArrayList<DonatedMedicalListItem> mDonationListMax = new ArrayList<>();

        final ArrayList<String> parentList=new ArrayList<>();

        final DonatedMedicalAdapter adapter=new DonatedMedicalAdapter(this, R.layout.donated_medical_listview,mDonationList);

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot contactSnapshot=dataSnapshot.child("My Donations").child(uid).child("Medical Donations");

                Iterable<DataSnapshot> contactChildren=contactSnapshot.getChildren();





                for(DataSnapshot contact : contactChildren){
                    String parent=contact.getKey();
                    DonatedMedicalListItem item= contact.getValue(DonatedMedicalListItem.class);




                    String name=item.getName();
                    String details=item.getDetails();
                    String user=item.getUserName();
                    String total=item.getTotalAmount();
                    String collected=item.getGivenAmount();

                    int amount= Integer.parseInt(total);
                    int value=Integer.parseInt(collected);

                    if(value<amount) {


                        DonatedMedicalListItem ob=new DonatedMedicalListItem(name, details,total,user,collected);

                        mDonationListMax.add(item);

                        parentList.add(parent);

                        mDonationList.add(ob);

                    }

                }
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                DonatedMedicalListItem b=mDonationListMax.get(position);

//                String parent=parentList.get(position);
//
//                String userName=b.getUserName();
//                String  name=b.getName();
//                String total=b.getTotalAmount();
//                String number=b.getContactNumber();
//                String bKash=b.getbKashNumber();
//                String details=b.getDetails();
//                String collected=b.getCollectedAmount();
//                String uid=b.getUid();
//
//
//                Bundle bundle=new Bundle();
//
//                bundle.putString("Name", name);
//                bundle.putString("Total", total);
//                bundle.putString("UserName", userName);
//                bundle.putString("Contact",number);
//                bundle.putString("bKash", bKash);
//                bundle.putString("Details", details);
//                bundle.putString("Collected", collected);
//                bundle.putString("Parent", parent);
//                bundle.putString("Uid",uid);
//
//
//
//                Intent intent=new Intent(GiveAMedicalDonation.this, DonateMedical.class);
//                intent.putExtras(bundle);
//                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//
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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fabMain:
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.fabOne:
                Intent a=new Intent(MyDonatedMedical.this,MainActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                break;

            case R.id.fabTwo:
                Intent b=new Intent(MyDonatedMedical.this,GiveADonation.class);
                b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(b);
                break;

            case R.id.fabThree:
                Intent c=new Intent(MyDonatedMedical.this,AskForADonation.class);
                c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(c);
                break;

            case R.id.fabFour:
                Intent d=new Intent(MyDonatedMedical.this,UserProfile.class);
                d.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(d);
                break;

        }

    }
}
