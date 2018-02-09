package com.example.jnstar.urqproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class Main2Activity extends AppCompatActivity {

     Button edit_button ;
     Button decline_button;
     Button fill_button;
     Button delete_button;


     Button btn_dialog_clear;
     Button btn_dialog_cancel;


     TextView num_queqe;
     String num_text;
     int temp;
     TextView name_store;

     private FirebaseAuth firebaseAuth;
     private FirebaseAuth.AuthStateListener firebaseAuthListener;
     private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
     int i=1;

     TextView remain_q;
     TextView waiting_time;

    int countFinishAndDoing = 0;

    int countFinish = 0 ;
    int countDoing = 0;
    int countQ =0;
    String countStatus = ".";

        TextView textServiced;
        TextView textShow0;
        TextView textShow1;
        TextView textShow2;
        TextView textShow3;
        TextView textShow4;
        TextView textAdd1;
        TextView textAdd2;
        TextView textAdd3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_button = (Button)findViewById(R.id.edit_button);
        decline_button = (Button)findViewById(R.id.decline_button);
        fill_button = (Button)findViewById(R.id.fill_button);
        delete_button = (Button)findViewById(R.id.delete_button);



        num_queqe =(TextView)findViewById(R.id.num_queue);
        temp = getIntent().getExtras().getInt("location");
        num_text =getIntent().getExtras().getString("myNumber");
        num_queqe.setText(num_text);
        remain_q =(TextView)findViewById(R.id.remain_q);


        name_store =(TextView)findViewById(R.id.name_store);
        waiting_time =(TextView)findViewById(R.id.waiting_time);


        textServiced = (TextView)findViewById(R.id.textServiced);
        textAdd1 = (TextView)findViewById(R.id.textAdd1);
        textAdd2 = (TextView)findViewById(R.id.textAdd2);
        textAdd3 = (TextView)findViewById(R.id.textAdd3);
        textShow0 = (TextView)findViewById(R.id.textShow0);
        textShow1 = (TextView)findViewById(R.id.textShow1);
        textShow2 = (TextView)findViewById(R.id.textShow2);
        textShow3 = (TextView)findViewById(R.id.textShow3);
        textShow4 = (TextView)findViewById(R.id.textShow4);



        mRootRef.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot shopSnapshot: dataSnapshot.getChildren()) {
                    if(temp == i){
                        String shopName = String.valueOf(shopSnapshot.child("shopName").child("name").getValue());
                        name_store.setText(shopName);

                        String statusCheck = String.valueOf(shopSnapshot.child("qNumber").child(num_text).child("status").getValue());
                        String repeatCheck = String.valueOf(shopSnapshot.child("qNumber").child(num_text).child("repeat").getValue());
                        String qType = String.valueOf(shopSnapshot.child("shopName").child("qType").getValue());


                        /////////////////นับจำนวณว่ามีแต่ละอันเท่าไหร่
                        int k=1;
                        while (!countStatus.equals("null")){

                            countStatus = String.valueOf(shopSnapshot.child("qNumber").child(k+"").child("status").getValue());

                            if (countStatus.equals("finish")){
                                countFinish++;
                            }else if(countStatus.equals("doing")){
                                countDoing++;
                            }else if(countStatus.equals("q")){
                                countQ++;
                            }

                            if(!countStatus.equals("null")){
                                k++;
                            }
                        }
                        countFinishAndDoing = countFinish+countDoing;

                        if (num_text.equals(String.valueOf(countFinishAndDoing))) {
                            textServiced.setVisibility(View.GONE); //หมายเลขนี้ได้รับการบริการไปแล้ว

                            textShow1.setVisibility(View.GONE);     //จำนวนคิวที่ต้องรอ
                            remain_q.setVisibility(View.GONE);      // เลขคิว
                            textShow2.setVisibility(View.GONE);     // คิว

                            textShow0.setVisibility(View.VISIBLE); // ถึงคิวของคุณแล้ว

                            textAdd1.setVisibility(View.GONE);    // กด เพิ่มหมายเลขคิว เพื่อทำรายการแจ้งเตือนใหม่
                            textAdd2.setVisibility(View.GONE);    // เพิ่มหมายเลขคิว
                            textAdd3.setVisibility(View.GONE);    // เพื่อทำรายการแจ้งเตือนใหม่

                            textShow3.setVisibility(View.GONE);     // ต้องรอประมาณ
                            waiting_time.setVisibility(View.GONE);  // เลข
                            textShow4.setVisibility(View.GONE);     // นาที

                            fill_button.setVisibility(View.GONE); // กดเพิ่มรายการ
                            edit_button.setVisibility(View.GONE);    // แก้ไข
                            decline_button.setVisibility(View.GONE); // ลบ
                            delete_button.setVisibility(View.VISIBLE); //ลบรายการแจ้งเตือนนี้

                        }else if(statusCheck.equals("finish")||statusCheck.equals("doing")){

                            textServiced.setVisibility(View.VISIBLE); //หมายเลขนี้ได้รับการบริการไปแล้ว

                            textShow1.setVisibility(View.GONE);     //จำนวนคิวที่ต้องรอ
                            remain_q.setVisibility(View.GONE);      // เลขคิว
                            textShow2.setVisibility(View.GONE);     // คิว

                            textShow0.setVisibility(View.GONE); // ถึงคิวของคุณแล้ว

                            textAdd1.setVisibility(View.VISIBLE);    // กด เพิ่มหมายเลขคิว เพื่อทำรายการแจ้งเตือนใหม่
                            textAdd2.setVisibility(View.VISIBLE);    // เพิ่มหมายเลขคิว
                            textAdd3.setVisibility(View.VISIBLE);    // เพื่อทำรายการแจ้งเตือนใหม่

                            textShow3.setVisibility(View.GONE);     // ต้องรอประมาณ
                            waiting_time.setVisibility(View.GONE);  // เลข
                            textShow4.setVisibility(View.GONE);     // นาที

                            fill_button.setVisibility(View.VISIBLE); // กดเพิ่มรายการ
                            edit_button.setVisibility(View.GONE);    // แก้ไข
                            decline_button.setVisibility(View.GONE); // ลบ
                            delete_button.setVisibility(View.GONE); //ลบรายการแจ้งเตือนนี้

                        }else if(statusCheck.equals("q")){

                            textServiced.setVisibility(View.GONE); //หมายเลขนี้ได้รับการบริการไปแล้ว

                            textShow1.setVisibility(View.VISIBLE);     //จำนวนคิวที่ต้องรอ
                            remain_q.setVisibility(View.VISIBLE);      // เลขคิว
                            textShow2.setVisibility(View.VISIBLE);     // คิว

                            textShow0.setVisibility(View.GONE); // ถึงคิวของคุณแล้ว

                            textAdd1.setVisibility(View.GONE);    // กด เพิ่มหมายเลขคิว เพื่อทำรายการแจ้งเตือนใหม่
                            textAdd2.setVisibility(View.GONE);    // เพิ่มหมายเลขคิว
                            textAdd3.setVisibility(View.GONE);    // เพื่อทำรายการแจ้งเตือนใหม่

                            textShow3.setVisibility(View.GONE);     // ต้องรอประมาณ
                            waiting_time.setVisibility(View.GONE);  // เลข
                            textShow4.setVisibility(View.GONE);     // นาที

                            fill_button.setVisibility(View.GONE); // กดเพิ่มรายการ
                            edit_button.setVisibility(View.VISIBLE);    // แก้ไข
                            decline_button.setVisibility(View.VISIBLE); // ลบ
                            delete_button.setVisibility(View.GONE); //ลบรายการแจ้งเตือนนี้

                            remain_q.setText(Integer.parseInt(num_text)-(countFinish+countDoing)+"");

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


        fill_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FillInformation.class);
                startActivity(intent);
            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Edit.class);
                intent.putExtra("location", temp);
                intent.putExtra("myNumber", num_text);
                startActivity(intent);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

              //  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
              //   DatabaseReference db_node = mRootRef.child("customer").child(user.getUid()+"").child("Add").child(temp+"");
              //   db_node.removeValue();



            }
        });

        decline_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Main2Activity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_main2_decline,null);
                btn_dialog_clear = (Button)mView.findViewById(R.id.btn_dialog_clear);
                btn_dialog_cancel = (Button)mView.findViewById(R.id.btn_dialog_cancel);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();;

                btn_dialog_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("location", temp);
                        intent.putExtra("myNumber", num_text);
                        startActivity(intent);
                    }
                });
                btn_dialog_cancel.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();

                    }
                });

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
            intent.putExtra("location", temp);
            intent.putExtra("myNumber", num_text);
            startActivity(intent);
        }else if(id == android.R.id.home){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("location", temp);
            intent.putExtra("myNumber", num_text);
            startActivity(intent);
            //onBackPressed();
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }





}
