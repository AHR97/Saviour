package com.example.saviour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DonateBlood extends AppCompatActivity implements View.OnClickListener{
    private static final int REQUEST_CALL = 1;
    private String contact;


    FloatingActionButton fabMain, fabOne, fabTwo, fabThree, fabFour;
    Float translationY = 100f;

    OvershootInterpolator interpolator = new OvershootInterpolator();


    Boolean isMenuOpen = false;

    TextView bUserName, bPhoneNumber, bHospitalName, bBloodGroup, bBags, bCityNAme, bAreaName;
    ImageView callImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_blood);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        callImage=(ImageView) findViewById(R.id.callImage);


        bUserName=(TextView)findViewById(R.id.TextView1);
        bPhoneNumber=(TextView)findViewById(R.id.TextView7);
        bHospitalName=(TextView)findViewById(R.id.TextView6);
        bBloodGroup=(TextView)findViewById(R.id.TextView2);
        bBags=(TextView)findViewById(R.id.TextView3);
        bCityNAme=(TextView)findViewById(R.id.TextView4);
        bAreaName=(TextView)findViewById(R.id.TextView5);


        callImage.setOnClickListener(this);

        Bundle bundle=getIntent().getExtras();

        String hospitalname=bundle.getString("Hospital");
        String username=bundle.getString("User");
        contact=bundle.getString("Number");
        String areaname=bundle.getString("Area");
        String cityname=bundle.getString("City");
        String bags=bundle.getString("Bags");
        String group=bundle.getString("Group");

        bHospitalName.setText(hospitalname);
        bUserName.setText(username);
        bPhoneNumber.setText(contact);
        bBags.setText(bags);
        bBloodGroup.setText(group);
        bCityNAme.setText(cityname);
        bAreaName.setText(areaname);



        initFabMenu();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.callImage:
                makePhoneCall();
                break;

            case R.id.fabMain:
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.fabOne:
                Intent a=new Intent(DonateBlood.this,MainActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                break;

            case R.id.fabTwo:
                Intent b=new Intent(DonateBlood.this,GiveADonation.class);
                b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(b);
                break;

            case R.id.fabThree:
                Intent c=new Intent(DonateBlood.this,AskForADonation.class);
                c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(c);
                break;

            case R.id.fabFour:
                Intent d=new Intent(DonateBlood.this,UserProfile.class);
                d.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(d);
                break;

        }
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





    private void makePhoneCall() {
        String number = contact;
        if (number.length() == 11) {

            if (ContextCompat.checkSelfPermission(DonateBlood.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DonateBlood.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" +number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(DonateBlood.this, "Contact Number Is Invalid", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
