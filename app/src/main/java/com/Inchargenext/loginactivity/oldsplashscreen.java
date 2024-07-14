//package com.Inchargenext.loginactivity;
//
//import android.content.Intent;
//import android.graphics.drawable.AnimationDrawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.WindowManager;
//import android.widget.ImageView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class oldsplashscreen extends AppCompatActivity {
//    private static final int timer = 4200;
//    AnimationDrawable battery;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.oldsplashscreen);
//
//        ImageView imageView = (ImageView) findViewById(R.id.image);
//        imageView.setBackgroundResource(R.drawable.animation);
//        battery = (AnimationDrawable) imageView.getBackground();
//
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(oldsplashscreen.this, Signup.class);
//                startActivity(intent);
//                finish();
//            }
//        }, timer);
//    }
//
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        battery.start();
//
//    }
//}