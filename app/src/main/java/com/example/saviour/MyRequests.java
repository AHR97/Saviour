package com.example.saviour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MyRequests extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout medical, event, blood, cloth, food;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        medical=(LinearLayout)findViewById(R.id.medical);
        event=(LinearLayout)findViewById(R.id.event);
        blood=(LinearLayout)findViewById(R.id.blood);
        cloth=(LinearLayout)findViewById(R.id.cloth);
        food=(LinearLayout)findViewById(R.id.food);


        medical.setOnClickListener(this);
        event.setOnClickListener(this);
        blood.setOnClickListener(this);
        cloth.setOnClickListener(this);
        food.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.medical:

                Intent a=new Intent(MyRequests.this, MyRequestedMedical.class);
                startActivity(a);
                break;

            case R.id.event:
                Intent b=new Intent(MyRequests.this, MyRequestedEvent.class);
                startActivity(b);

                break;

            case R.id.blood:
                Intent c=new Intent(MyRequests.this, MyRequestedBlood.class);
                startActivity(c);

                break;

            case R.id.cloth:
                Intent d=new Intent(MyRequests.this, MyRequestedCloth.class);
                startActivity(d);
                break;

            case R.id.food:
                Intent e=new Intent(MyRequests.this, MyRequestedFood.class);
                startActivity(e);
                break;
        }

    }
}
