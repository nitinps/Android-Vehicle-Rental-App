package com.example.carbookingappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
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
import java.lang.Object;
import java.util.regex.Matcher;
import java.util.regex.Matcher.*;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

public class Payment extends AppCompatActivity {
    TextView total;
    EditText carddetails;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    public String userId,credits,total1;
    int crd,tot,realtot,cred;
    Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        total=findViewById(R.id.textView8);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        pay=findViewById(R.id.paybtn);
        userId = fAuth.getCurrentUser().getUid();
        //total1 = "0";

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                total1 = (documentSnapshot.getString("price")) ;
                credits =(documentSnapshot.getString("credits"));
                tot = Integer.parseInt(total1);
                tot=tot+1000;
                total.setText(Integer.toString(tot));
                crd = Integer.parseInt(credits);
                cred = crd;

                realtot=tot;
                CheckBox repeatChkBx = ( CheckBox ) findViewById( R.id.checkBox );
                repeatChkBx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        if ( isChecked )
                        {   int flag=0;
                            if(crd==0){
                                Toast.makeText(Payment.this, "No credits in account", Toast.LENGTH_SHORT).show();}
                            else{
                                tot = realtot;
                                tot = tot-crd;
                                total.setText(Integer.toString(tot));
                                crd = 0;
                            }

                        }
                        else if(!isChecked)
                        {
                            total.setText(Integer.toString(realtot));
                            crd=cred;
                        }

                    }
                });

            }

        });

        carddetails=findViewById(R.id.card);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern p = Pattern.compile("[0-9]+");
                Matcher m=p.matcher(carddetails.getText());
                if(carddetails.getText()==null){
                    Toast.makeText(Payment.this, "Please enter card details" , Toast.LENGTH_SHORT).show();
                }
                else if(!m.find()){
                    Toast.makeText(Payment.this, "Please enter numbers only" , Toast.LENGTH_SHORT).show();
                }
                else if(carddetails.getText().length()!=16){
                    Toast.makeText(Payment.this, "Please enter 16 digit card number" , Toast.LENGTH_SHORT).show();
                }
                else {
                    DocumentReference documentReference1 = fStore.collection("users").document(userId);
                    Map<String, Object> user = new HashMap<>();
                    user.put("credits", Integer.toString(crd + 50));
                    user.put("carddetails", carddetails.getText().toString());
                    documentReference1.update(user);
                    Intent intent = new Intent(getApplicationContext(), OrderConfirmation.class);
                    startActivity(intent);
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



