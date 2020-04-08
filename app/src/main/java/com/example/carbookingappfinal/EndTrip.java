package com.example.carbookingappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class EndTrip extends AppCompatActivity{

    private TextView d2,t2;
    private Context _context=this;
    String time,date,userId,cur;
    String[] curr;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_trip);
        d2=findViewById(R.id.date2);
        t2=findViewById(R.id.time2);
        userId = fAuth.getCurrentUser().getUid();

        /*StartTrip s=new StartTrip();
        final String[] curr=s.date.split("/");*/

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                cur = (documentSnapshot.getString("startdate"));
                curr=cur.split("/");
            }
        });

        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final DatePickerDialog dialog = new DatePickerDialog(_context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            if ((Integer.parseInt(curr[2])==year && Integer.parseInt(curr[1])< month+1) ||
                                    (Integer.parseInt(curr[2])==year && Integer.parseInt(curr[1])== month+1 && Integer.parseInt(curr[0])<dayOfMonth )){
                                date = dayOfMonth + "/" + (month + 1) + "/" + year;
                                d2.setText(date);
                            }
                            else {
                                Toast.makeText(EndTrip.this, "Select a day which is after start day" , Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                    //datePickerDialog.show();
                    dialog.show();
                }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    TimePickerDialog tdialog = new TimePickerDialog(_context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            if (minute >= 10) {
                                time = hourOfDay + ":" + minute;
                            } else {
                                time = hourOfDay + ":0" + minute;
                            }
                            t2.setText(time);
                        }
                    }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                            Calendar.getInstance().get(Calendar.MINUTE),
                            true);
                    tdialog.show();
                }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(time==null || date==null){
                    Toast.makeText(EndTrip.this, "Please enter all fields" , Toast.LENGTH_SHORT).show();
                }
                else {
                    DocumentReference documentReference = fStore.collection("users").document(userId);
                    Map<String, Object> user = new HashMap<>();
                    user.put("enddate", date);
                    user.put("endtime", time);
                    documentReference.update(user);

                    Intent intent1 = new Intent(getApplicationContext(), Address2.class);
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