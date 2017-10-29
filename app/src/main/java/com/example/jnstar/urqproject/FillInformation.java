package com.example.jnstar.urqproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class FillInformation extends AppCompatActivity {

    Button btn_fill_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_fill_save = (Button)findViewById(R.id.btn_fill_save);


    }

   public void clickButtonfillInformation (View v){
       if(v == btn_fill_save){
           Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
           startActivity(intent);
       }

   }

}
