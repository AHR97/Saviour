package com.example.saviour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AskForADonation extends AppCompatActivity implements View.OnClickListener {

    private CardView medical, event, blood, cloth, food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_for_adonation);

        medical = (CardView) findViewById(R.id.ask_medical_donation);
        event = (CardView) findViewById(R.id.ask_event_donation);
        blood = (CardView) findViewById(R.id.ask_blood_donation);
        cloth = (CardView) findViewById(R.id.ask_cloth_donation);
        food = (CardView) findViewById(R.id.ask_food_donation);


        medical.setOnClickListener(this);
        event.setOnClickListener(this);
        blood.setOnClickListener(this);
        cloth.setOnClickListener(this);
        food.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.ask_medical_donation:
                Intent a =new Intent(AskForADonation.this, AskForAMedicalDonation.class);
                startActivity(a);
                break;

            case R.id.ask_event_donation:
                Intent b =new Intent(AskForADonation.this, AskForEventDonation.class);
                startActivity(b);
                break;

            case R.id.ask_blood_donation:
                Intent c =new Intent(AskForADonation.this, AskForBlooodDonation.class);
                startActivity(c);
                break;

            case R.id.ask_cloth_donation:
                Intent d =new Intent(AskForADonation.this, AskForClothdonation.class);
                startActivity(d);
                break;

            case R.id.ask_food_donation:
                Intent e =new Intent(AskForADonation.this,AskForAFoodDonation.class);
                startActivity(e);
                break;


        }

    }
}
