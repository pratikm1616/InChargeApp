package com.Inchargenext.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class tatamotorscars extends AppCompatActivity {
    ImageView backbutton;
    Button t1, t2, savebtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tatamotorscars);

        backbutton = findViewById(R.id.backbutton);
        t1 = findViewById(R.id.tatanexon);
        t2 = findViewById(R.id.tatatigor);
        savebtn = findViewById(R.id.savethetatacar);
        progressBar = findViewById(R.id.progressBar);


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EcarMapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(tatamotorscars.this, "Your Car is Tata Nexon ", Toast.LENGTH_SHORT).show();
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(tatamotorscars.this, "Your Car is Tata Tigor Ev ", Toast.LENGTH_SHORT).show();
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(tatamotorscars.this, "Your Car is Saved ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
