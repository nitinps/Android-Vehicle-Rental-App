package com.example.carbookingappfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class StartTrip extends AppCompatActivity
{

    private TextView d1,t1;
    private Context _context=this;
    String time,userId;
    String date;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_trip);
        d1= findViewById(R.id.date1);
        t1= findViewById(R.id.time1);
        userId = fAuth.getCurrentUser().getUid();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.HH.mm");
        String cur = sdf.format(new Date());
        //Toast.makeText(StartTrip.this, cur , Toast.LENGTH_SHORT).show();
        final String[] curr = cur.split("\\.", 5);
        Log.i("myinfo", curr[0]);

        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    DatePickerDialog dialog = new DatePickerDialog(_context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            if((Integer.parseInt(curr[2])==year && Integer.parseInt(curr[1])< month+1) ||
                                    (Integer.parseInt(curr[2])==year && Integer.parseInt(curr[1])== month+1 && Integer.parseInt(curr[0])<dayOfMonth )){
                                date = dayOfMonth + "/" + (month + 1) + "/" + year;
                                d1.setText(date);
                            }
                            else{
                                Toast.makeText(StartTrip.this, "Select a date which is after 12 am today! " , Toast.LENGTH_SHORT).show();
                            }
                            //Toast.makeText(StartTrip.this, String.valueOf(year) , Toast.LENGTH_SHORT).show();
                            /*if(Integer.parseInt(curr[2])>year){
                                Toast.makeText(StartTrip.this, "Don't select a date in the past" , Toast.LENGTH_SHORT).show();
                            }
                            else if(Integer.parseInt(curr[1])>month ){
                                Toast.makeText(StartTrip.this, "Don't select a date in the past" , Toast.LENGTH_SHORT).show();
                            }
                            else if (Integer.parseInt(curr[0])>dayOfMonth ){
                                Toast.makeText(StartTrip.this, "Don't select a date in the past" , Toast.LENGTH_SHORT).show();
                            }
                            else {
                                date = dayOfMonth + "/" + (month + 1) + "/" + year;
                                d1.setText(date);
                            }*/
                        }
                    }, Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                    dialog.show();
                }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    TimePickerDialog tdialog = new TimePickerDialog(_context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            if(minute>=10){
                                time=hourOfDay+":"+minute;}
                            else{ time= hourOfDay+":0"+minute; }
                            t1.setText(time);
                        }
                    },Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                            Calendar.getInstance().get(Calendar.MINUTE),
                            true);
                    tdialog.show();
                }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(time==null || date ==null){
                    Toast.makeText(StartTrip.this, "Please enter all fields" , Toast.LENGTH_SHORT).show();
                }
                else {
                    DocumentReference documentReference = fStore.collection("users").document(userId);
                    Map<String, Object> user = new HashMap<>();
                    user.put("startdate", date);
                    user.put("starttime", time);
                    documentReference.update(user);

                    Intent intent = new Intent(getApplicationContext(), EndTrip.class);
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