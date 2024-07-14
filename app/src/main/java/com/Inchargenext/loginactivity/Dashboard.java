package com.Inchargenext.loginactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity {
    TextView tv1;
    ImageView imageView, maps;
    FirebaseAuth fauth;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        fauth = FirebaseAuth.getInstance();
        tv1 = findViewById(R.id.HelloText);
        imageView = findViewById(R.id.imageView);

//        tv1.setText(task.getResult().getString("uname"));
    }

    //     if (fauth.getCurrentUser()!=null)
//    {
//        startActivity(new Intent(getApplicationContext(),Dashboard.class));
//        finish();
//    }
    public void callIntent(View view) {
        Intent intent = new Intent(getApplicationContext(), EditProfile1.class);
        startActivity(intent);
    }

    public void callIntent2(View view) {
        Intent intent = new Intent(Dashboard.this, MapsActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        fauth.signOut();
        Intent intent = new Intent(Dashboard.this, Login.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(Dashboard.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    public void callIntent3(View view) {
        Intent intent = new Intent(getApplicationContext(), ShowPlaces.class);
        intent.putExtra("Category","Hospitals");
        startActivity(intent);
    }

    public void GetURLFROMINtent(View view) {
        String url2 = "https://www.google.com/maps/@18.5620845,73.8301434,15z/data=!10m2!1e2!2e13";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url2));
        startActivity(i);
    }

    public void selectevact(View view) {
//        String url3="https://www.google.com/maps/search/swappable+battery+station";
//        Intent i=new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(url3));
//        startActivity(i);
        Intent i = new Intent(Dashboard.this, SelectEvActivity.class);
        startActivity(i);
    }

    public void callnews(View view) {
        String url4 = "https://www.google.com/search?q=news+about+electric+vehicles&source=lnms&tbm=nws&sa=X&ved=2ahUKEwjA4q6J0-_3AhXCQPUHHb9dCTQQ_AUoAXoECAEQAw&biw=1536&bih=714&dpr=1.25\n";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url4));
        startActivity(i);
    }
}

