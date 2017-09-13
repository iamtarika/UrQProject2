package com.example.jnstar.urqproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

public class setting extends AppCompatActivity {

    private ListView listView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] textString = {"การแจ้งเตือน", "เสียงแจ้งเตือน"};
        int[] drawableIds = {R.drawable.ic_notifications_black_24dp, R.drawable.ic_music_note_black_24dp};

        listView1 = (ListView)findViewById(R.id.menuList);

    }
}
