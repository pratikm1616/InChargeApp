package com.Inchargenext.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button signupbtn, loginbtn;
    EditText email_var, password_var;
    TextView bforgetpassword;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Full screen display
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);

        signupbtn = findViewById(R.id.logintosignupbtn);
        loginbtn = findViewById(R.id.loginloginbtn);

        email_var = findViewById(R.id.email_text_field_design);
        password_var = findViewById(R.id.password_input_field);
        bforgetpassword = findViewById(R.id.forgetpassword1);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        checkBox = findViewById(R.id.logincheckbox);
        //Checkbox
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    password_var.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password_var.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        //forget button
        bforgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "error to open forgetpassword", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
                startActivity(intent);
                finish();
            }
        });
        //signupbtn click
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_ = email_var.getText().toString();
                String password_ = password_var.getText().toString();

                if (TextUtils.isEmpty(email_)) {
                    email_var.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password_)) {
                    password_var.setError("Password is Required");
                    return;
                }
                if (!TextUtils.isEmpty(email_) || !TextUtils.isEmpty(password_)) {
                    progressBar.setVisibility(View.VISIBLE);
                    //Authenticate The com.Inchargenext.loginactivity.User
                    fAuth.signInWithEmailAndPassword(email_, password_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Logged In Successfully.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                            } else {
                                Toast.makeText(Login.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
            finish();
        }
    }
}