package com.Inchargenext.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SelectFuelActivity extends AppCompatActivity {
    private ImageButton button, button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectfuel);

        button = findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity2();
            }
        });
        button1 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity3();
            }
        });
    }

    public void openMainActivity2() {
        Intent intent = new Intent(this, CngMapsActivity.class);
        startActivity(intent);
    }

    public void openMainActivity3() {
        Intent intent1 = new Intent(this, SelectEvActivity.class);
        startActivity(intent1);
    }
}


