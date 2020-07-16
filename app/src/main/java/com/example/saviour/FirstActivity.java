package com.example.saviour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class FirstActivity extends AppCompatActivity {

    TextView name;
    Typeface typeface;

    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        name=(TextView)findViewById(R.id.textView);

        typeface=Typeface.createFromAsset(getAssets(),"font/Sofia_Regular.otf");
        name.setTypeface(typeface);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent_timer=new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent_timer);
                finish();

            }
        }, 3000);
    }

}
