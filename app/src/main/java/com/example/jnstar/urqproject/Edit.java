package com.example.jnstar.urqproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit extends AppCompatActivity {

    EditText num_edit ;
    String num_text;
    int temp;
    TextView tv_edit_location;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    int i =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        num_edit =(EditText) findViewById(R.id.num_edit);
        temp = getIntent().getExtras().getInt("temp");
        num_text = getIntent().getExtras().getString("myNumber");
        num_edit.setHint(num_text);
        tv_edit_location = (TextView)findViewById(R.id.tv_edit_location);


        if(temp==0){
            mRootRef.child("shop").child("nameShop").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //  int questionCount = (int) dataSnapshot.getChildrenCount();
                    // name_store.setText(""+questionCount);

                    for (DataSnapshot shopSnapshot: dataSnapshot.getChildren()) {
                        if (i == 0) {

                            String shopName = shopSnapshot.getKey().toString();
                            tv_edit_location.setText(shopName);
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
                            tv_edit_location.setText(shopName);
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

    }


    public void onClickButtonEditSave (View v){
        if(num_edit.getText().toString().length() == 0){
            Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
            intent.putExtra("location", temp);
            intent.putExtra("myNumber", num_text);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
            intent.putExtra("location", temp);
            intent.putExtra("myNumber", num_edit.getText().toString());
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
