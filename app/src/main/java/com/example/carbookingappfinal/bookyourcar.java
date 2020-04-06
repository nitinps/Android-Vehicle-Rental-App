package com.example.carbookingappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class bookyourcar extends AppCompatActivity {
    Button b1,b2,b3,b4,b5;
    String userId;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookyourcar);
        b1= findViewById(R.id.button1);
        b2= findViewById(R.id.button2);
        b3= findViewById(R.id.button3);
        b4= findViewById(R.id.button4);
        b5= findViewById(R.id.button5);
        userId = fAuth.getCurrentUser().getUid();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = fStore.collection("users").document(userId);
                Map<String,Object> user = new HashMap<>();
                user.put("price", "3000");
                user.put("car", "Bentley Continental");
                documentReference.update(user);
                Intent intent2=new Intent(getApplicationContext(), Payment.class);
                startActivity(intent2);}});

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = fStore.collection("users").document(userId);
                Map<String,Object> user = new HashMap<>();
                user.put("price", "1300");
                user.put("car", "Dodge Charger");
                documentReference.update(user);
                Intent intent2=new Intent(getApplicationContext(), Payment.class);
                startActivity(intent2);}});

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = fStore.collection("users").document(userId);
                Map<String,Object> user = new HashMap<>();
                user.put("price", "400");
                user.put("car", "Ford Fiesta");
                documentReference.update(user);
                Intent intent2=new Intent(getApplicationContext(), Payment.class);
                startActivity(intent2);}});

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = fStore.collection("users").document(userId);
                Map<String,Object> user = new HashMap<>();
                user.put("price", "800");
                user.put("car", "BMW X3");
                documentReference.update(user);
                Intent intent2=new Intent(getApplicationContext(), Payment.class);
                startActivity(intent2);}});

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = fStore.collection("users").document(userId);
                Map<String,Object> user = new HashMap<>();
                user.put("price", "1500");
                user.put("car", "Range Rover Evoque");
                documentReference.update(user);
                Intent intent2=new Intent(getApplicationContext(), Payment.class);
                startActivity(intent2);}});
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
