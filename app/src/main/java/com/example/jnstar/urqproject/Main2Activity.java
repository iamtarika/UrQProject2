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

     Button btn_dialog_clear;
     Button btn_dialog_cancel;

     TextView num_queqe;
     String num_text;
     int temp;

     TextView name_store;
     private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
     int i=-1;

     TextView remain_q;
     TextView waiting_time;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_button = (Button)findViewById(R.id.edit_button);
        decline_button = (Button)findViewById(R.id.decline_button);

        num_queqe =(TextView)findViewById(R.id.num_queue);
        temp = getIntent().getExtras().getInt("temp");
        num_text =getIntent().getExtras().getString("myNumber");
        num_queqe.setText(num_text);
        remain_q =(TextView)findViewById(R.id.remain_q);


        name_store =(TextView)findViewById(R.id.name_store);
        waiting_time =(TextView)findViewById(R.id.waiting_time);

        if(temp==0){
            mRootRef.child("shop").child("nameShop").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                  //  int questionCount = (int) dataSnapshot.getChildrenCount();
                   // name_store.setText(""+questionCount);

                    for (DataSnapshot shopSnapshot: dataSnapshot.getChildren()) {
                        if (i == 0) {

                            String shopName = shopSnapshot.getKey().toString();
                            name_store.setText(shopName);

                            String status = dataSnapshot.child(shopName).child("numQ").child(num_text).child("status").getValue().toString();
                            String waitTime = dataSnapshot.child(shopName).child("numQ").child(num_text).child("timeRemain").getValue().toString();

                            if(status=="finish"){
                                remain_q.setText("0");
                            }else if(status=="doing"){
                                remain_q.setText("1");
                            }

                            waiting_time.setText(waitTime);

                        }
                        i++;
                    }
                    i=-1;


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }else{
            mRootRef.child("shop").child("nameShop").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //  int questionCount = (int) dataSnapshot.getChildrenCount();
                    // name_store.setText(""+questionCount);

                    for (DataSnapshot shopSnapshot: dataSnapshot.getChildren()) {
                        if (i == 1) {

                            String shopName = shopSnapshot.getKey().toString();
                            name_store.setText(shopName);

                            String status = dataSnapshot.child(shopName).child("numQ").child(num_text).child("status").getValue().toString();
                            String waitTime = dataSnapshot.child(shopName).child("numQ").child(num_text).child("timeRemain").getValue().toString();

                            if(status=="finish"){
                                remain_q.setText("0");
                            }else if(status=="doing"){
                                remain_q.setText("1");
                            }

                            waiting_time.setText(waitTime);


                        }
                        i++;
                    }
                    i=-1;

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Edit.class);
                intent.putExtra("location", temp);
                intent.putExtra("myNumber", num_text);
                startActivity(intent);
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
