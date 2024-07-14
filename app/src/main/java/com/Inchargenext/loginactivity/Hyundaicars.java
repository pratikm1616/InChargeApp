package com.Inchargenext.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Hyundaicars extends AppCompatActivity {
    ImageView backbutton;
    Button h1, savebtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyundaicars);
        h1 = findViewById(R.id.hyundaikona);
        savebtn = findViewById(R.id.savethehyundaicar);
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
        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Hyundaicars.this, "Your Car is Hyundai-Kona ", Toast.LENGTH_SHORT).show();
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(Hyundaicars.this, "Your Car is Saved ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
