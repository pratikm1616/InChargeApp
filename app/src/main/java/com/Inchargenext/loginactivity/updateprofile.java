package com.Inchargenext.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

public class updateprofile extends AppCompatActivity {
    EditText etname, etemail, etAddress, etuname, etphone;
    Button button;
    FirebaseDatabase Database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    DocumentReference documentReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageButton backbtnup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();
        documentReference = db.collection("user").document(currentuid);
        etname = findViewById(R.id.et_name_up);
        etemail = findViewById(R.id.et_email_up);
        etAddress = findViewById(R.id.et_address_up);
        etuname = findViewById(R.id.et_username_up);
        etphone = findViewById(R.id.et_phone_up);
        button = findViewById(R.id.btn_up);
        backbtnup = findViewById(R.id.backbtnup);
        backbtnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfile1.class);
                startActivity(intent);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()) {
                    etname.setText(task.getResult().getString("name"));
                    etuname.setText(task.getResult().getString("uname"));
                    etemail.setText(task.getResult().getString("Email"));
                    etphone.setText(task.getResult().getString("phone"));
                    etAddress.setText(task.getResult().getString("Address"));
                } else {
                    Toast.makeText(updateprofile.this, "No Profile Exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateProfile() {
        String name = etname.getText().toString();
        String uname = etuname.getText().toString();
        String Email = etemail.getText().toString();
        String phone = etphone.getText().toString();
        String Address = etAddress.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();

        final DocumentReference sDoc = db.collection("user").document(currentuid);
        db.runTransaction(new Transaction.Function<Void>() {
                    @Override
                    public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                        transaction.update(sDoc, "name", name);
                        transaction.update(sDoc, "Email", Email);
                        transaction.update(sDoc, "Address", Address);
                        transaction.update(sDoc, "uname", uname);
                        transaction.update(sDoc, "phone", phone);
                        // Success
                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(updateprofile.this, "Updated", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(updateprofile.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}