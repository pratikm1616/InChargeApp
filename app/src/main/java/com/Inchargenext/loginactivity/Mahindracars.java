package com.Inchargenext.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Mahindracars extends AppCompatActivity {
    ImageView backbutton;
    Button mah1, mah2, savebtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahindracars);

        mah1 = findViewById(R.id.mahindrae20plus);
        mah2 = findViewById(R.id.mahindraeverito);
        savebtn = findViewById(R.id.savethemahindracar);
        progressBar = findViewById(R.id.progressBar);
        backbutton = findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EcarMapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mah1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mahindracars.this, "Your Car is Mahindra e20Plus ", Toast.LENGTH_SHORT).show();
            }
        });
        mah2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mahindracars.this, "Your Car is Mahindra Everito ", Toast.LENGTH_SHORT).show();
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(Mahindracars.this, "Your Car is Saved ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}