package com.example.saviour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MyDonation extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout medical, event, blood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        medical=(LinearLayout)findViewById(R.id.medical);
        event=(LinearLayout)findViewById(R.id.event);
        blood=(LinearLayout)findViewById(R.id.blood);


        medical.setOnClickListener(this);
        event.setOnClickListener(this);
        blood.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.medical:
                Intent intent=new Intent(MyDonation.this, MyDonatedMedical.class);
                startActivity(intent);
                break;

            case R.id.event:
                Intent intent1=new Intent(MyDonation.this, MyDonatedEvent.class);
                startActivity(intent1);
                break;

            case R.id.blood:
                Intent intent2=new Intent(MyDonation.this, MyDonatedBlood.class);
                startActivity(intent2);
                break;
        }

    }
}
