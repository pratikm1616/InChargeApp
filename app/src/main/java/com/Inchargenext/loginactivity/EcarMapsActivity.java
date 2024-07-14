package com.Inchargenext.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EcarMapsActivity extends AppCompatActivity {
    ImageView backbutton;
    Button tatamotors, hyundai, jaguar, mahindra, mercedes, mg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecarmaps);
        backbutton = findViewById(R.id.backbutton);
        tatamotors = findViewById(R.id.tatamotors);
        hyundai = findViewById(R.id.hyundaibutton);
        jaguar = findViewById(R.id.jaguarbutton);
        mahindra = findViewById(R.id.mahindrabtn);
        mercedes = findViewById(R.id.mercedesbtn);
        mg = findViewById(R.id.mgbtn);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectEvActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tatamotors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), tatamotorscars.class);
                startActivity(intent);
                finish();
            }
        });
        hyundai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Hyundaicars.class);
                startActivity(intent);
                finish();
            }
        });
        jaguar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Jaguarcar.class);
                startActivity(intent);
            }
        });
        mahindra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mahindracars.class);
                startActivity(intent);
            }
        });
        mercedes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mercedescar.class);
                startActivity(intent);
            }
        });
        mg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mgcars.class);
                startActivity(intent);
            }
        });
    }
}