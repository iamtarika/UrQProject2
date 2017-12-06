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

public class Edit extends AppCompatActivity {

    EditText num_edit ;
    String num_text;
    int temp;


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
