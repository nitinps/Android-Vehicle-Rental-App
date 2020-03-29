package com.example.carbookingappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Address2 extends AppCompatActivity {

    EditText a1;
    String address;
    Button b1;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address2);
        a1= findViewById(R.id.addtext);
        b1= findViewById(R.id.cc_btn);
        userId = fAuth.getCurrentUser().getUid();
            b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = a1.getText().toString();
                DocumentReference documentReference = fStore.collection("users").document(userId);
                Map<String,Object> user = new HashMap<>();
                user.put("address", address);
                documentReference.update(user);

            }
        });

    }
}
