package com.Inchargenext.loginactivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Create_Profile extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    EditText etname, etemail, etAddress, etuname, etphone;
    Button button;
    ProgressBar progressBar;
    ImageView imageView, backbtncp;
    TextView gotovehicles;
    UploadTask uploadTask;
    StorageReference storageReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    Uri imageUri;
    All_user_member member;
    String current_User_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        member = new All_user_member();
        imageView = findViewById(R.id.iv_cp);
        etname = findViewById(R.id.et_name_cp);
        etemail = findViewById(R.id.et_email_cp);
        etAddress = findViewById(R.id.et_address_cp);
        button = findViewById(R.id.btn_cp);
        backbtncp = findViewById(R.id.backbtncp);
//        gotovehicles = findViewById(R.id.gotovehicles);
        progressBar = findViewById(R.id.progressBar_cp);
        etuname = findViewById(R.id.et_username_cp);
        etphone = findViewById(R.id.et_phone_cp);

        backbtncp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                finish();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            current_User_id = user.getUid();
            storageReference = FirebaseStorage.getInstance().getReference("Profile images");
            documentReference = db.collection("user").document(current_User_id);

        }

        //change incase
        databaseReference = database.getReference("datauser1");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploaddata();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivity(intent);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });
        gotovehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Intent intent = new Intent(getApplicationContext(), SelectEvActivity.class);
                    startActivity(intent);
                }

            }
        });
     /*   mapsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE || resultCode == RESULT_OK || data != null || data.getData() != null) {
                imageUri = data.getData();
                Picasso.get().load(imageUri).into(imageView);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error" + e, Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getMimeTypeFromExtension((contentResolver.getType(uri)));
    }

    private void uploaddata() {
        String name = etname.getText().toString();
        String Email = etemail.getText().toString();
        String address = etAddress.getText().toString();
        String uname = etuname.getText().toString();
        String phone = etphone.getText().toString();

        if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(Email) || !TextUtils.isEmpty(address) || imageUri != null) {
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getFileExt(imageUri));
            uploadTask = reference.putFile(imageUri);

            Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloaduri = task.getResult();

                        Map<String, String> profile = new HashMap<>();
                        profile.put("name", name);
                        profile.put("Email", Email);
                        profile.put("Address", address);
                        profile.put("url", downloaduri.toString());
                        profile.put("uid", current_User_id);
                        profile.put("uname", uname);
                        profile.put("phone", phone);

                        member.setName(name);
                        member.setAddress(address);
                        member.setEmail(Email);
                        member.setUid(current_User_id);
                        member.setUrl(downloaduri.toString());
                        member.setUname(uname);
                        member.setPhone(phone);

                        databaseReference.child(current_User_id).setValue(member);
                        documentReference.set(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Create_Profile.this, "Profile Created!", Toast.LENGTH_SHORT).show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(Create_Profile.this, EditProfile1.class);
                                        startActivity(intent);
                                    }
                                }, 2000);
                            }
                        });
                    }
                }
            });
        } else {
            Toast.makeText(this, "Please fill the all fields", Toast.LENGTH_SHORT).show();
        }
    }
}