package com.example.carbookingappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class OrderConfirmation extends AppCompatActivity {
    TextView startd,startt,endd,endt,add,car;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId,sd,st,ed,et,a,c;
    Button home,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        startd=findViewById(R.id.startdate);
        startt=findViewById(R.id.starttime);
        endd=findViewById(R.id.enddate);
        endt=findViewById(R.id.endtime);
        add=findViewById(R.id.address);
        car=findViewById(R.id.car);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        home=findViewById(R.id.homebtn);
        cancel=findViewById(R.id.cancelbtn);
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                sd=(documentSnapshot.getString("startdate"));
                st=(documentSnapshot.getString("starttime"));
                ed=(documentSnapshot.getString("enddate"));
                et=(documentSnapshot.getString("endtime"));
                a=(documentSnapshot.getString("address"));
                c=(documentSnapshot.getString("car"));
                startd.setText(startd.getText()+sd);
                startt.setText(startt.getText()+st);
                endd.setText(endd.getText()+ed);
                endt.setText(endt.getText()+et);
                add.setText(add.getText()+a);
                car.setText(car.getText()+c);
            }
        });
        //Toast.makeText(OrderConfirmation.this,"Credits worth Rs.50 added to your account",Toast.LENGTH_SHORT).show();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(), ContactUs.class);
                startActivity(intent1);
            }
        });

        CreditsDialog exampleDialog = new CreditsDialog();
        exampleDialog.show(getSupportFragmentManager(), "Dialog");
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
        }
        return super.onOptionsItemSelected(item);
    }
}
