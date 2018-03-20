package com.example.jnstar.urqproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Notification extends AppCompatActivity {

    Switch sw_alarm , sw_notification;
    TextView tv_notification_setting;
    public int temp   = 0;
    public int temp_1   = 0;

    Spinner sp;
    String format[] = {"ทุกคิว", "จำนวนที่กำหนด", "ก่อนระยะเวลาที่กำหนด"};
    ArrayAdapter<String> adapter;

    Spinner sp_2;
    String format_2[] = {"5 นาที", "10 นาที" , "20 นาที", "30 นาที" ,"60 นาที"};
    ArrayAdapter<String> adapter_time;

    EditText ed_add_num;
    TextView tv_detail_1 , tv_detail_2;

    String location;
    String num_text;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    TextView tot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sw_alarm = (Switch)findViewById(R.id.sw_alarm);
        sw_notification =(Switch)findViewById(R.id.sw_notification);

        tv_notification_setting =(TextView)findViewById(R.id.tv_notification_setting);
        tv_notification_setting.setTextColor(Color.parseColor("#cac8ca"));

        sp = (Spinner) findViewById(R.id.sp_setting);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, format);
        sp.setAdapter(adapter);
        sp.setEnabled(false);

        ed_add_num = (EditText)findViewById(R.id.ed_add_num);
        ed_add_num.setVisibility(View.INVISIBLE);

        tv_detail_1 =(TextView)findViewById(R.id.tv_detail_1);
        tv_detail_1.setVisibility(View.INVISIBLE);

        tv_detail_2 =(TextView)findViewById(R.id.tv_detail_2);
        tv_detail_2.setVisibility(View.INVISIBLE);

        sp_2 = (Spinner) findViewById(R.id.sp_time);
        adapter_time = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, format_2);
        sp_2.setAdapter(adapter_time);
        sp_2.setVisibility(View.INVISIBLE);

        location = getIntent().getExtras().getString("location");
        num_text =getIntent().getExtras().getString("myNumber");

        tot = (TextView)findViewById(R.id.tot);

                mRootRef.child("customer").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        String notificationSound = String.valueOf(dataSnapshot.child(user.getUid()+"").child("Add").
                                child(location+"").child("notification").child("sound").getValue());
                        String notificationAlarm = String.valueOf(dataSnapshot.child(user.getUid()+"").child("Add").
                                child(location+"").child("notification").child("alarm").getValue());
                        String notificationType = String.valueOf(dataSnapshot.child(user.getUid()+"").child("Add").
                                child(location+"").child("notification").child("type").getValue());

                        if (notificationSound.equals("1")){
                            sw_alarm.setChecked(true);
                        }else if(notificationSound.equals("0")){
                            sw_alarm.setChecked(false);
                            sp.setEnabled(false);
                            sp_2.setEnabled(false);
                        }

                        if (notificationAlarm.equals("1")){
                            sw_notification.setChecked(true);
                            sp.setEnabled(true);
                            sp.setVisibility(View.VISIBLE);
                            tv_notification_setting.setTextColor(Color.parseColor("#000000"));
                        }else if(notificationAlarm.equals("0")){

                            sw_notification.setChecked(false);

                        }


                        if(notificationType.equals("0")){
                            tv_notification_setting.setEnabled(true);   // รูปแบบการแจ้งเตือน
                            sp.setEnabled(true);                        // spinner เลือกรูปแบบ

                            tv_detail_1.setEnabled(false);
                            ed_add_num.setEnabled(false);
                            tv_detail_1.setVisibility(View.GONE);
                            ed_add_num.setVisibility(View.GONE);

                            tv_detail_2.setEnabled(false);
                            sp_2.setEnabled(false);
                            tv_detail_2.setVisibility(View.GONE);
                            sp_2.setVisibility(View.GONE);

                            String myString = "ทุกคิว"; //the value you want the position for
                            ArrayAdapter myAdap = (ArrayAdapter) sp.getAdapter(); //cast to an ArrayAdapter
                            int spinnerPosition = myAdap.getPosition(myString);
                            sp.setSelection(spinnerPosition);

                        }else if(notificationType.equals("1")){
                            tv_notification_setting.setEnabled(true);   // รูปแบบการแจ้งเตือน
                            sp.setEnabled(true);                        // spinner เลือกรูปแบบ

                            tv_detail_1.setEnabled(true);
                            ed_add_num.setEnabled(true);
                            tv_detail_1.setVisibility(View.VISIBLE);
                            ed_add_num.setVisibility(View.VISIBLE);

                            tv_detail_2.setEnabled(false);              // เวลาที่ต้องการ
                            sp_2.setEnabled(false);
                            tv_detail_2.setVisibility(View.GONE);
                            sp_2.setVisibility(View.GONE);

                            String myString = "จำนวนที่กำหนด"; //the value you want the position for
                            ArrayAdapter myAdap = (ArrayAdapter) sp.getAdapter(); //cast to an ArrayAdapter
                            int spinnerPosition = myAdap.getPosition(myString);
                            sp.setSelection(spinnerPosition);


                        }else if(notificationType.equals("2")){
                            tv_notification_setting.setEnabled(true);   // รูปแบบการแจ้งเตือน
                            sp.setEnabled(true);                        // spinner เลือกรูปแบบ

                            tv_detail_1.setEnabled(false);
                            ed_add_num.setEnabled(false);
                            tv_detail_1.setVisibility(View.GONE);
                            ed_add_num.setVisibility(View.GONE);

                            tv_detail_2.setEnabled(true);              // เวลาที่ต้องการ
                            sp_2.setEnabled(true);
                            tv_detail_2.setVisibility(View.VISIBLE);
                            sp_2.setVisibility(View.VISIBLE );

                            String myString = "ก่อนระยะเวลาที่กำหนด"; //the value you want the position for
                            ArrayAdapter myAdap = (ArrayAdapter) sp.getAdapter(); //cast to an ArrayAdapter
                            int spinnerPosition = myAdap.getPosition(myString);
                            sp.setSelection(spinnerPosition);

                        }



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



        sw_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sw_alarm.isChecked()){

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference mCodeNotificationSound = mRootRef.child("customer").child(user.getUid()).child("Add").child(location+"")
                            .child("notification").child("sound");
                    mCodeNotificationSound.setValue("1");

                }else{

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference mCodeNotificationSound = mRootRef.child("customer").child(user.getUid()).child("Add").child(location+"")
                            .child("notification").child("sound");
                    mCodeNotificationSound.setValue("0");

                }
            }
        });


        sw_notification.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(sw_notification.isChecked()){

                    sp.setEnabled(true);

                    tv_detail_1.setEnabled(true);
                    tv_detail_1.setTextColor(Color.parseColor("#000000"));

                    ed_add_num.setEnabled(true);

                    tv_detail_2.setEnabled(true);
                    tv_detail_2.setTextColor(Color.parseColor("#000000"));

                    sp_2.setEnabled(true);
                    tv_notification_setting.setTextColor(Color.parseColor("#000000"));

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference mCodeNotificationSound = mRootRef.child("customer").child(user.getUid()).child("Add").child(location+"")
                            .child("notification").child("alarm");
                    mCodeNotificationSound.setValue("1");
                    tot.setText("กด");
                }else if (!sw_notification.isChecked()){

                    sp.setEnabled(false);

                    tv_detail_1.setEnabled(false);
                    tv_detail_1.setTextColor(Color.parseColor("#cac8ca"));

                    ed_add_num.setEnabled(false);

                    tv_detail_2.setEnabled(true);
                    tv_detail_2.setTextColor(Color.parseColor("#cac8ca"));

                    sp_2.setEnabled(false);
                    tv_notification_setting.setTextColor(Color.parseColor("#cac8ca"));
tot.setText("ไม่กด");

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference mCodeNotificationSound = mRootRef.child("customer").child(user.getUid()).child("Add").child(location+"")
                            .child("notification").child("alarm");
                    mCodeNotificationSound.setValue("0");

                }
            }
        });

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference mCodeNotificationType = mRootRef.child("customer").child(user.getUid()).child("Add").child(location+"")
                        .child("notification").child("type");
/*
                switch (i){
                    case 0: temp = 0;
                        tv_detail_1.setVisibility(View.GONE);  // จำนวนที่ต้องการให้แจ้งล่วงหน้า
                        ed_add_num.setVisibility(View.GONE);   // ช่องเติมระบุจำนวนคิว
                        tv_detail_2.setVisibility(View.GONE);  // ก่อนระยะเวลาที่กำหนด
                        sp_2.setVisibility(View.GONE);         // sp2

                        mCodeNotificationType.setValue("0");

                        break; //ทุกคิว
                    case 1: temp = 1;
                        tv_detail_1.setVisibility(View.VISIBLE);  // จำนวนที่ต้องการให้แจ้งล่วงหน้า
                        ed_add_num.setVisibility(View.VISIBLE);   // ช่องเติมระบุจำนวนคิว
                        tv_detail_2.setVisibility(View.GONE);  // ก่อนระยะเวลาที่กำหนด
                        sp_2.setVisibility(View.GONE);         // sp2

                        mCodeNotificationType.setValue("1");


                        break; //จำนวนที่กำหนด
                    case 2: temp = 2;
                        tv_detail_1.setVisibility(View.GONE);  // จำนวนที่ต้องการให้แจ้งล่วงหน้า
                        ed_add_num.setVisibility(View.GONE);   // ช่องเติมระบุจำนวนคิว
                        tv_detail_2.setVisibility(View.VISIBLE);  // ก่อนระยะเวลาที่กำหนด
                        sp_2.setVisibility(View.VISIBLE);         // sp2

                        mCodeNotificationType.setValue("2");

                    break; //ก่อนเวลาที่กำหนด
                }
*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }}


        );

        sp_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                /*
               switch (i){
                    case 0: temp_1 = 0;   break; //
                    case 1: temp_1 = 1;   break;
                    case 2: temp_1 = 2;   break;
                    case 3: temp_1 = 3;   break;
                    case 4: temp_1 = 4;   break;
                }
                */

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_blank,menu);
        return super.onCreateOptionsMenu(menu);

        //  return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.item_sound){
            Intent intent = new Intent(getApplicationContext(),Notification.class);
            startActivity(intent);
        }else if(id == android.R.id.home){
        finish();
        //onBackPressed();
        //return true;
        }
        return super.onOptionsItemSelected(item);

    }




}




