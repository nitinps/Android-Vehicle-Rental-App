package com.example.carbookingappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                if(a1.getText().toString().length()<1){
                    Toast.makeText(Address2.this, "Please enter address", Toast.LENGTH_SHORT).show();
                }
                else {
                    address = a1.getText().toString();
                    DocumentReference documentReference = fStore.collection("users").document(userId);
                    Map<String, Object> user = new HashMap<>();
                    user.put("address", address);
                    documentReference.update(user);
                    Intent intent1 = new Intent(getApplicationContext(), bookyourcar.class);
                    startActivity(intent1);
                }

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            case R.id.home_menu:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            case R.id.contact_us:
                startActivity(new Intent(getApplicationContext(),ContactUs.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
