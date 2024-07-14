package com.Inchargenext.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private EditText forEmail;
    private Button forgetpassword;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forEmail = findViewById(R.id.foremail);
        forgetpassword = findViewById(R.id.forgetpassword);

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = forEmail.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(ForgetPassword.this, "Please Enter An Email", Toast.LENGTH_SHORT).show();
                } else {
                    forgetPassword();
                }
            }
        });
    }

    private void forgetPassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgetPassword.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgetPassword.this, Login.class));
                    finish();
                } else {
                    Toast.makeText(ForgetPassword.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}