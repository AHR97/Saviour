package com.example.saviour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class MyRequestedCloth extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private DatabaseReference databaseReference;

    FloatingActionButton fabMain, fabOne, fabTwo, fabThree, fabFour;
    Float translationY = 100f;

    OvershootInterpolator interpolator = new OvershootInterpolator();


    Boolean isMenuOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requested_cloth);

        initFabMenu();

        listView= (ListView) findViewById(R.id.clothListViewId);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        final ArrayList<RequestedClothDonationListItem> cDonationList = new ArrayList<>();
        final ArrayList<RequestedClothDonationListItem> cDonationListMax = new ArrayList<>();


        final ArrayList<String> parentList=new ArrayList<>();

        final RequestedClothDonationAdapter adapter=new RequestedClothDonationAdapter(this, R.layout.requested_cloth_listview,cDonationList);

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot contactSnapshot=dataSnapshot.child("My Requests").child(uid).child("Cloth Requests");
                Iterable<DataSnapshot> contactChildren=contactSnapshot.getChildren();



                for(DataSnapshot contact : contactChildren){

                    String parent=contact.getKey();
                    RequestedClothDonationListItem item= contact.getValue(RequestedClothDonationListItem.class);
                    cDonationListMax.add(item);

                    String city=item.getCity();
                    String area=item.getArea();
                    String type=item.getCategory();
                    String place=item.getPlace();


                    RequestedClothDonationListItem ob=new RequestedClothDonationListItem(city, area,type, place);

                    cDonationList.add(ob);
                    parentList.add(parent);


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
                RequestedClothDonationListItem b = cDonationListMax.get(position);
                String flag = b.getFlag();

                if (flag.equals("1")) {
                    showDialog();
                }
                else {
                    String parent = parentList.get(position);

                    Bundle bundle = new Bundle();

                    bundle.putString("Parent", parent);

                    Intent intent = new Intent(MyRequestedCloth.this, ConfirmedFoodRequest.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
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


    public void showDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setCancelable(false);
        builder.setTitle("Deleted Request");
        builder.setIcon(R.drawable.ic_charity);


        builder.setMessage("This request is already deleted")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(MyRequestedCloth.this, MyRequestedCloth.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }
                );

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        {
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
                    Intent a=new Intent(MyRequestedCloth.this,MainActivity.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                    break;

                case R.id.fabTwo:
                    Intent b=new Intent(MyRequestedCloth.this,GiveADonation.class);
                    b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(b);
                    break;

                case R.id.fabThree:
                    Intent c=new Intent(MyRequestedCloth.this,AskForADonation.class);
                    c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(c);
                    break;

                case R.id.fabFour:
                    Intent d=new Intent(MyRequestedCloth.this,UserProfile.class);
                    d.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(d);
                    break;

            }
        }

        }
}
