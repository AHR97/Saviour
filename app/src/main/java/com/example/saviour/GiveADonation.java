package com.example.saviour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GiveADonation extends AppCompatActivity implements View.OnClickListener {

    private CardView medical, event, blood, cloth, food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_adonation);

        medical = (CardView) findViewById(R.id.give_medical_donation);
        event = (CardView) findViewById(R.id.give_event_donation);
        blood = (CardView) findViewById(R.id.give_blood_donation);
        cloth = (CardView) findViewById(R.id.give_cloth_donation);
        food = (CardView) findViewById(R.id.give_food_donation);


        medical.setOnClickListener(this);
        event.setOnClickListener(this);
        blood.setOnClickListener(this);
        cloth.setOnClickListener(this);
        food.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.give_medical_donation:
                Intent a =new Intent(GiveADonation.this, GiveAMedicalDonation.class);
                startActivity(a);
                break;

            case R.id.give_event_donation:
                Intent b =new Intent(GiveADonation.this, GiveAnEventDonation.class);
                startActivity(b);
                break;

            case R.id.give_blood_donation:
                Intent c =new Intent(GiveADonation.this, GiveABloodDonation.class);
                startActivity(c);
                break;

            case R.id.give_cloth_donation:
                Intent d =new Intent(GiveADonation.this, GiveAClothDonation.class);
                startActivity(d);
                break;

            case R.id.give_food_donation:
                Intent e =new Intent(GiveADonation.this, GiveAFoodDonation.class);
                startActivity(e);
                break;


        }

    }

}