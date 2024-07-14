package com.Inchargenext.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Mercedescar extends AppCompatActivity {
    ImageView backbutton;
    Button m1, savebtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercedescar);

        backbutton = findViewById(R.id.backbutton);
        m1 = findViewById(R.id.benzeqc);
        savebtn = findViewById(R.id.savethebenzcar);
        progressBar = findViewById(R.id.progressBar);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EcarMapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mercedescar.this, "Your Car is Mercedes Benz-EQC", Toast.LENGTH_SHORT).show();
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(Mercedescar.this, "Your Car is Saved ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}