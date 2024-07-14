package com.Inchargenext.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EbikeMapsActivity extends AppCompatActivity {
    ImageView backbutton;
    Button arther, bajaj, tvs, hero, yakuza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebikemaps);
        backbutton = findViewById(R.id.backbutton);
        arther = findViewById(R.id.artherbtn);
        bajaj = findViewById(R.id.bajajbtn);
        tvs = findViewById(R.id.tvsbtn);
        hero = findViewById(R.id.herobtn);
        yakuza = findViewById(R.id.yakuzabtn);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectEvActivity.class);
                startActivity(intent);
                finish();
            }
        });
        arther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Arther.class);
                startActivity(intent);
                finish();
            }
        });
        bajaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Bajaj.class);
                startActivity(intent);
                finish();
            }
        });
        tvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TVS.class);
                startActivity(intent);
            }
        });
        hero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Hero.class);
                startActivity(intent);
            }
        });
        yakuza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Yakuza.class);
                startActivity(intent);
            }
        });
    }
}

