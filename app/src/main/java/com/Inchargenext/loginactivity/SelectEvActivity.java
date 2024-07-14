package com.Inchargenext.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectEvActivity extends AppCompatActivity {
    ImageView backbutton;
    private ImageButton buttonb, buttonc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectev);
        backbutton = findViewById(R.id.backbuttonselectev);
        buttonb = findViewById(R.id.buttonbike);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        buttonb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EbikeMapsActivity.class);
                startActivity(intent);
            }
        });
        buttonc = findViewById(R.id.buttoncar);
        buttonc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EcarMapsActivity.class);
                startActivity(intent);
            }
        });
    }
}