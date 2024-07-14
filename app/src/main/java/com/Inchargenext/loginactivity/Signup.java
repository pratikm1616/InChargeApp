package com.Inchargenext.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    EditText email_var, password_var, confirm_pass_var;
    Button registerbutton;
    TextView t1;
    CheckBox checkBox;
    FirebaseDatabase Database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    /* FirebaseFirestore db=FirebaseFirestore.getInstance();
     All_user_member member;
     String currentUserId;
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        email_var = findViewById(R.id.email_field);
        password_var = findViewById(R.id.password_field);
        registerbutton = findViewById(R.id.registerbtn);
        t1 = findViewById(R.id.bluetxt);
        fAuth = FirebaseAuth.getInstance();
        checkBox = findViewById(R.id.signupcheckbox);
        progressBar = findViewById(R.id.progressBar);
        confirm_pass_var = findViewById(R.id.confirm_password_field);
//Checkbox
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    password_var.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirm_pass_var.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password_var.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirm_pass_var.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /* public void loginbuttononclick(View view)
     {
         Intent intent=new Intent(getApplicationContext(),Login.class);
         startActivity(intent);
         finish();
     }*/
    public void registerbuttonclick(View view) {
        String email = email_var.getText().toString();
        String password = password_var.getText().toString();
        String confirmpass = confirm_pass_var.getText().toString();

        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(confirmpass)) {
            if (password.equals(confirmpass)) {
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendtoDashboard();
                            progressBar.setVisibility(View.INVISIBLE);
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            String error = task.getException().getMessage();
                            Toast.makeText(Signup.this, "Error" + error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Password and Confirm Password Are Not Matching", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        } else {
            Toast.makeText(this, "Please fill the all fields", Toast.LENGTH_SHORT).show();
        }

        //End
        /*   {

            }
            else
            {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Password and Confirm Password not Matching", Toast.LENGTH_SHORT).show();
                confirm_pass_var.setError(null);
                confirm_pass_var.setErrorEnabled(false);
            }

                if (!email.isEmpty())
                {
                    email_var.setError(null);
                    email_var.setErrorEnabled(false);
                        if (!password.isEmpty())
                        {
                            password_var.setError(null);
                            password_var.setErrorEnabled(false);
                            if(email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"))
                            {
                                if(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"))
                                {
                                    Database=FirebaseDatabase.getInstance();
                                   databaseReference=Database.getReference("datauser1");

                                    String emails_=email_var.getEditText().getText().toString();
                                    String passwords_=password_var.getEditText().getText().toString();
                                    StoringData storinData=new StoringData(emails_,passwords_);

                                 databaseReference.child(currentUserId).setValue(member);

                                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(getApplicationContext(),Create_Profile.class);
                                    startActivity(intent);
                                    finish();

                                }else
                                {
                                    password_var.setError("Invalid Password");
                                    return;
                                }

                            }else
                            {
                                email_var.setError("Invalid Email");
                                return;
                            }


                        }else
                        {
                            password_var.setError("Please Enter The Password");
                            return;
                        }

                {
                    email_var.setError("Please Enter The Email");
                    return;

                }
            }
        //registers user in firebase

        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Signup.this, "com.Inchargenext.loginactivity.User Created.", Toast.LENGTH_SHORT).show();
                    //FireStore
                    String userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference=db.collection("users").document(userID);
                    databaseReference=Database.getReference("All Users");
                    Map<String,Object> user=new HashMap<>();
                    user.put("email",email);
                    user.put("password",password);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("TAG", "onSuccess: com.Inchargenext.loginactivity.User Profile is created For "+userID);
                        }

                    });
                    startActivity(new Intent(getApplicationContext(),Dashboard.class));
                }
                else
                {
                    Toast.makeText(Signup.this, "Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        */
        if (password.length() < 6) {
            password_var.setError("Please Enter Password More Than 6 Characters");
            return;
        }
    }


    private void sendtoDashboard() {
        Intent intent = new Intent(Signup.this, Dashboard.class);
        startActivity(intent);
        finish();
    }

    private void uploadData() {
    }

    //user is already registerd
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            sendtoDashboard();
        }
    }
}
