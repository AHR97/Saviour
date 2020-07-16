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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GiveAClothDonation extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private DatabaseReference databaseReference;

    FloatingActionButton fabMain, fabOne, fabTwo, fabThree, fabFour;
    Float translationY = 100f;

    OvershootInterpolator interpolator = new OvershootInterpolator();


    Boolean isMenuOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_acloth_donation);

        listView= (ListView) findViewById(R.id.clothListViewId);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        final ArrayList<ClothDonationListItem> cDonationList = new ArrayList<>();
        final ArrayList<ClothDonationListItem> cDonationListMax = new ArrayList<>();

        final ClothDonationAdapter adapter=new ClothDonationAdapter(this, R.layout.cloth_donation_listview,cDonationList);

        initFabMenu();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot contactSnapshot=dataSnapshot.child("Cloth Donation Requests");
                Iterable<DataSnapshot> contactChildren=contactSnapshot.getChildren();



                for(DataSnapshot contact : contactChildren){
                    ClothDonationListItem item= contact.getValue(ClothDonationListItem.class);
                    cDonationListMax.add(item);

                    String city=item.getCity();
                    String area=item.getArea();
                    String number=item.getContactNumber();
                    String type=item.getCategory();
                    String userName=item.getUserName();

                    String flag=item.getFlag();

                    if(flag.equals("0")) {

                        ClothDonationListItem ob = new ClothDonationListItem(city, area, number, type, userName);

                        cDonationList.add(ob);
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
                ClothDonationListItem b=cDonationListMax.get(position);

                String city=b.getCity();
                String area=b.getArea();
                String contactNumber=b.getContactNumber();
                String type=b.getCategory();
                String place=b.getPlace();
                String userName=b.getUserName();
                String uid=b.getUid();

                Bundle bundle=new Bundle();

                bundle.putString("City",city);
                bundle.putString("Area",area);
                bundle.putString("Number",contactNumber);
                bundle.putString("Type",type);
                bundle.putString("Place",place);
                bundle.putString("User",userName);
                bundle.putString("Uid",uid);


                Intent intent=new Intent(GiveAClothDonation.this, DonateCloth.class);
                intent.putExtras(bundle);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

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
                Intent a=new Intent(GiveAClothDonation.this,MainActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                break;

            case R.id.fabTwo:
                Intent b=new Intent(GiveAClothDonation.this,GiveADonation.class);
                b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(b);
                break;

            case R.id.fabThree:
                Intent c=new Intent(GiveAClothDonation.this,AskForADonation.class);
                c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(c);
                break;

            case R.id.fabFour:
                Intent d=new Intent(GiveAClothDonation.this,UserProfile.class);
                d.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(d);
                break;

        }
    }
}
