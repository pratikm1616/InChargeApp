package com.Inchargenext.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class EditProfile1 extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView1;
    TextView tv1;
    TextView nameEt, emailEt, contactEt, usernameEt, addressEt;
    ImageButton editpage2, backbtndash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile1);
        imageView1 = findViewById(R.id.imageView_EP);
        nameEt = findViewById(R.id.textview_name);
        usernameEt = findViewById(R.id.textview_username);
        emailEt = findViewById(R.id.textview_email);
        contactEt = findViewById(R.id.textView_contact);
        addressEt = findViewById(R.id.textview_address);
        /*vehicleinfoEt=(TextView)findViewById(R.id.et_vehicleinfo_f1);*/
        tv1 = findViewById(R.id.HelloText);
        editpage2 = findViewById(R.id.editpage2);
        backbtndash = findViewById(R.id.backbtndash);
 /*   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_edit_profile1,container,false);
        return view;
    }*/
        backbtndash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
        editpage2.setOnClickListener(this);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editpage2:
                Intent intent = new Intent(getApplicationContext(), updateprofile.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String current_User_id = user.getUid();
        DocumentReference reference;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        reference = firestore.collection("user").document(current_User_id);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()) {
                    /*String nameResult=task.getResult().getString("name");
                 //   String usernameResult=task.getResult().getString("uname");
                    String emailResult=task.getResult().getString("Email");
                    String AddressResult=task.getResult().getString("Address");
                    String urlResult=task.getResult().getString("url");*/

                    Picasso.get().load(task.getResult().getString("url")).into(imageView1);
                    nameEt.setText(task.getResult().getString("name"));
                    usernameEt.setText(task.getResult().getString("uname"));
                    emailEt.setText(task.getResult().getString("Email"));
                    contactEt.setText(task.getResult().getString("phone"));
                    addressEt.setText(task.getResult().getString("Address"));
                    //  tv1.setText(usernameEt.getText().toString());
//                    tv1.setText(task.getResult().getString("uname"));

                } else {
                    Intent intent = new Intent(getApplicationContext(), Create_Profile.class);
                    startActivity(intent);

                }

            }
        });
    }
}
//  private ImageView profileimgview;
// private DatabaseReference databaseReference;
// private FirebaseAuth fAuth;
// private ImageButton editbtn;
// private TextView name;
// @Override
// protected void onCreate(Bundle savedInstanceState) {
//     super.onCreate(savedInstanceState);
//     setContentView(R.layout.activity_edit_profile1);
//     editbtn=findViewById(R.id.ib_edit_f1);
//     profileimgview=findViewById(R.id.profiledpformyprofile);
//     name=findViewById(R.id.tv_name_f1);
//     fAuth= FirebaseAuth.getInstance();
//     databaseReference= FirebaseDatabase.getInstance().getReference().child("com.Inchargenext.loginactivity.User");
//     getUserinfo();
//
//     profileimgview.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View view) {
//             Intent intent = new Intent(EditProfile1.this,ChangeDP.class);
//             startActivity(intent);
//             finish();
//         }
//     });
// }
// private void getUserinfo() {
//     databaseReference.child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//         @Override
//         public void onDataChange(@NonNull DataSnapshot snapshot) {
//             if (snapshot.exists()&&snapshot.getChildrenCount()>0)
//             {
//                 String name1=snapshot.child("name").getValue().toString();
//                 name.setText(getText(Integer.parseInt(name1)));
//
//                 if(snapshot.hasChild("image"))
//                 {
//                     String image=snapshot.child("image").getValue().toString();
//                     Picasso.get().load(image).into(profileimgview);
//                 }
//             }
//         }
//
//         @Override
//         public void onCancelled(@NonNull DatabaseError error) {
//
//         }
//     });
//
// }
//
//
// public void ChangeDP(View view) {
//     Intent intent = new Intent(EditProfile1.this,ChangeDP.class);
//     startActivity(intent);
//     finish();
// }