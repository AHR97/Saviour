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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DonateMedical extends AppCompatActivity implements View.OnClickListener{
    private static final int REQUEST_CALL = 1;
    private String contact, collected, parent, user, total, name, details, bkash,uid;


    TextView musername, mpatientname, mdisease, mtotal, mcollected, mcontact, mbKash;

    ImageView home, call, sendmoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_medical);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        home=(ImageView) findViewById(R.id.HomeButton);
        call=(ImageView) findViewById(R.id.callImage);
        sendmoney=(ImageView) findViewById(R.id.sendMoneyImage);


        home.setOnClickListener(this);
        call.setOnClickListener(this);
        sendmoney.setOnClickListener(this);

        musername=(TextView)findViewById(R.id.TextView1);
        mpatientname=(TextView)findViewById(R.id.TextView2);
        mdisease=(TextView)findViewById(R.id.TextView3);
        mtotal=(TextView)findViewById(R.id.TextView4);
        mcollected=(TextView)findViewById(R.id.TextView5);
        mcontact=(TextView)findViewById(R.id.TextView6);
        mbKash=(TextView)findViewById(R.id.TextView7);

        Bundle bundle=getIntent().getExtras();

        user=bundle.getString("UserName");
        name=bundle.getString("Name");
        total=bundle.getString("Total");
        collected=bundle.getString("Collected");
        details=bundle.getString("Details");
        contact=bundle.getString("Contact");
        bkash=bundle.getString("bKash");
        parent=bundle.getString("Parent");
        uid=bundle.getString("Uid");

        mbKash.setText(bkash);
        mcontact.setText(contact);
        mcollected.setText(collected);
        mtotal.setText(total);
        musername.setText(user);
        mpatientname.setText(name);
        mdisease.setText(details);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.HomeButton:
                Intent a=new Intent(DonateMedical.this,GiveADonation.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                break;

            case R.id.callImage:
                makePhoneCall();
                break;

            case R.id.sendMoneyImage:

                Bundle val=new Bundle();
                val.putString("Money", collected);
                val.putString("Parent", parent);
                val.putString("Total", total);
                val.putString("Contact", contact);
                val.putString("bKash", bkash);
                val.putString("User", user);
                val.putString("Name", name);
                val.putString("Details", details);
                val.putString("Uid",uid);

                Intent c=new Intent(DonateMedical.this, SendMedicalMoney.class);
                //c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                c.putExtras(val);
                startActivity(c);
                break;
        }
    }

    private void makePhoneCall() {
        String number = contact;
        if (number.length() == 11) {

            if (ContextCompat.checkSelfPermission(DonateMedical.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DonateMedical.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" +number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(DonateMedical.this, "Contact Number Is Invalid", Toast.LENGTH_SHORT).show();
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
