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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class FillInformation extends AppCompatActivity {

    Button btn_fill_save;
    EditText et_num_q;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    Spinner areaSpinner;
    int temp=0;
    String countNo="0";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btn_fill_save =(Button)findViewById(R.id.btn_fill_save);
        et_num_q =(EditText)findViewById(R.id.et_num_q);
        areaSpinner = (Spinner) findViewById(R.id.sp_location_q);



        mRootRef.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> shop = new ArrayList<String>();

                for (DataSnapshot shopSnapshot: dataSnapshot.getChildren()) {
                    String shopName = String.valueOf(shopSnapshot.child("shopName").child("name").getValue());
                    shop.add(shopName);
                }


                ArrayAdapter<String> shopsAdapter = new ArrayAdapter<String>(FillInformation.this, android.R.layout.simple_spinner_item, shop);
                shopsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(shopsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    temp = i+1 ;

                final TextView a2 = (TextView) findViewById(R.id.text_2);
                final int[] k = {1};
                mRootRef.child("user").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot numSnapshot: dataSnapshot.getChildren()) {


                            if(temp == k[0]){
                                countNo = String.valueOf(numSnapshot.child("qNumber").getChildrenCount());

                                a2.setText(countNo+"--");


                            }
                            k[0]++;


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        btn_fill_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if(v == btn_fill_save && et_num_q.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(), "กรุณาระบุเลขคิว" ,Toast.LENGTH_SHORT).show();

                }
                else if(v == btn_fill_save && (Integer.parseInt(et_num_q.getText().toString()) > Integer.parseInt(countNo) )){

                    Toast.makeText(getApplicationContext(), "ไม่มีเลขคิวนี้ในระบบ" ,Toast.LENGTH_SHORT).show();

                }else if(et_num_q.getText().toString().equals("0") ){

                    Toast.makeText(getApplicationContext(), "กรุณาระบุเลขคิว" ,Toast.LENGTH_SHORT).show();

                }
                else if(et_num_q.getText().toString().length()!=0){

                    Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                    intent.putExtra("location", temp);
                    intent.putExtra("myNumber", et_num_q.getText().toString());
                    startActivity(intent);

                }else{

                    Toast.makeText(getApplicationContext(), "กรุณาระบุเลขคิว" ,Toast.LENGTH_SHORT).show();

                }


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
