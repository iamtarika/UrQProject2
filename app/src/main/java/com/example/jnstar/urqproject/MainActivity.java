package com.example.jnstar.urqproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    TextView tv_specify_q;
    Button btn_fill_inform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_specify_q=(TextView)findViewById(R.id.tv_specify_q);
        Typeface tf_1=Typeface.createFromAsset(getAssets(),"fonts/CmPrasanmit.ttf");
        tv_specify_q.setTypeface(tf_1);

        btn_fill_inform = (Button)findViewById(R.id.fill_inform);

    }

   public void clickButtonEnter (View v){
        if(v == btn_fill_inform){
            Intent intent = new Intent(getApplicationContext(),FillInformation.class);
            startActivity(intent);
        }

   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main,menu);
            return super.onCreateOptionsMenu(menu);

      //  return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.item_sound){
           Intent intent = new Intent(getApplicationContext(),Notification.class);
           startActivity(intent);
      }
       return super.onOptionsItemSelected(item);

    }



}
