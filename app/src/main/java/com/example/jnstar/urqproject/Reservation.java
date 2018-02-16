package com.example.jnstar.urqproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Reservation extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    Button btn_main_dialog_Logout;
    Button btn_main_dialog_cancel;

    int temp;
    int i=1;
    TextView tarika;

    TextView name_reserve_store;


    TextView text_reserve_1;
    TextView text_reserve_time_open_close;
    TextView text_reserve_2;

    TextView text_reserve_3;
    TextView text_reserve_time_reserve;
    TextView text_reserve_4;

    TextView text_reserve_5;
    TextView text_reserve_6;
    TextView text_reserve_q_wait;
    TextView text_reserve_7;
    Button   btn_reserve;
    LinearLayout linear_1;
    LinearLayout linear_2;

    int countQ =0;
    String countStatus = ".";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tarika = (TextView)findViewById(R.id.tarika);


        name_reserve_store = (TextView)findViewById(R.id.name_reserve_store);
        text_reserve_time_open_close = (TextView)findViewById(R.id.text_reserve_time_open_close);

        linear_1 =(LinearLayout)findViewById(R.id.linear_1);
        text_reserve_3 = (TextView)findViewById(R.id.text_reserve_3);
        text_reserve_time_reserve = (TextView)findViewById(R.id.text_reserve_time_reserve);
        text_reserve_4 = (TextView)findViewById(R.id.text_reserve_4);

        text_reserve_5 = (TextView)findViewById(R.id.text_reserve_5);   //  หากจองคิวต้องรอคิว
        text_reserve_6 = (TextView)findViewById(R.id.text_reserve_6);   //  มีคิวที่รอรับบริการ
        text_reserve_q_wait = (TextView)findViewById(R.id.text_reserve_q_wait);
        text_reserve_7 = (TextView)findViewById(R.id.text_reserve_7);

        temp = getIntent().getExtras().getInt("location")+1;


                    mRootRef.child("user").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot shopSnapshot: dataSnapshot.getChildren()) {

                            if(temp == i){

                                /// ส่วนของชื่อร้าน
                                String shopName  = String.valueOf(shopSnapshot.child("shopName").child("name").getValue());
                                name_reserve_store.setText(shopName);
                                //เวลาเปิดปิด
                                String timeOpen  = String.valueOf(shopSnapshot.child("shopName").child("time").child("open").getValue());
                                String timeClose = String.valueOf(shopSnapshot.child("shopName").child("time").child("close").getValue());
                                text_reserve_time_open_close.setText(timeOpen+"-"+timeClose);
                                //เวลาที่เปิดจอง
                                String qType  = String.valueOf(shopSnapshot.child("shopName").child("qType").getValue());
                                // 1 คือจองได้,
                                if (qType.equals("1")){
                                    String timeReserve = String.valueOf(shopSnapshot.child("shopName").child("time").child("reserve").getValue());
                                    text_reserve_time_reserve.setText(timeReserve);

                                    text_reserve_3.setVisibility(View.VISIBLE);
                                    text_reserve_time_reserve.setVisibility(View.VISIBLE);
                                    text_reserve_4.setVisibility(View.VISIBLE);
                                }else {
                                    linear_1.setVisibility(View.GONE);
                                    text_reserve_3.setVisibility(View.GONE);
                                    text_reserve_time_reserve.setVisibility(View.GONE);
                                    text_reserve_4.setVisibility(View.GONE);

                                    int k=1;
                                    countQ =0;
                                    countStatus = ".";
                                    while (!countStatus.equals("null")){
                                        countStatus = String.valueOf(shopSnapshot.child("qNumber").child(k+"").child("status").getValue());
                                        if(countStatus.equals("q")){
                                            countQ++;
                                        }
                                        k++;
                                    }

                                    text_reserve_5.setVisibility(View.GONE);   //  หากจองคิวต้องรอคิว
                                    text_reserve_6.setVisibility(View.VISIBLE);   //  มีคิวที่รอรับบริการ
                                    text_reserve_q_wait = (TextView)findViewById(R.id.text_reserve_q_wait);
                                    text_reserve_7 = (TextView)findViewById(R.id.text_reserve_7);


                                }









                            }
                            i++;


                        }

                        i=1;



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



}
