package com.example.jnstar.urqproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FillInformation extends AppCompatActivity {

    Button btn_fill_save;
    EditText et_num_q;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btn_fill_save =(Button)findViewById(R.id.btn_fill_save);
        et_num_q =(EditText)findViewById(R.id.et_num_q);


        mRootRef.child("shop").child("nameShop").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> shop = new ArrayList<String>();

                for (DataSnapshot shopSnapshot: dataSnapshot.getChildren()) {
                    String shopName = shopSnapshot.getKey().toString();
                    shop.add(shopName);
                }

                Spinner areaSpinner = (Spinner) findViewById(R.id.sp_location_q);
                ArrayAdapter<String> shopsAdapter = new ArrayAdapter<String>(FillInformation.this, android.R.layout.simple_spinner_item, shop);
                shopsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(shopsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


   public void clickButtonfillInformation (View v){
       if(v == btn_fill_save ){

           Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
           startActivity(intent);

       }

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
