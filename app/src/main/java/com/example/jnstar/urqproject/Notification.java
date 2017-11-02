package com.example.jnstar.urqproject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import static com.example.jnstar.urqproject.R.array.planets_array;

public class Notification extends AppCompatActivity {

    Switch sw_alarm , sw_notification;
    TextView tv_notification_setting;
    Spinner sp;
    String format[] = {"ทุกคิว", "จำนวนที่กำหนด", "ก่อนระยะเวลาที่กำหนด"};
    ArrayAdapter<String> adapter;
    public int temp   = 0;
    public int temp_1   = 0;


    Spinner sp_2;
    String format_2[] = {"5 นาที", "10 นาที" , "20 นาที", "30 นาที" ,"60 นาที"};
    ArrayAdapter<String> adapter_time;

    EditText ed_add_num;
    TextView tv_detail_1 , tv_detail_2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sw_alarm = (Switch)findViewById(R.id.sw_alarm);
        sw_notification =(Switch)findViewById(R.id.sw_notification);

        tv_notification_setting =(TextView)findViewById(R.id.tv_notification_setting);
        tv_notification_setting.setTextColor(Color.parseColor("#cac8ca"));

        sp = (Spinner) findViewById(R.id.sp_setting);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, format);
        sp.setAdapter(adapter);
        sp.setEnabled(false);

        ed_add_num = (EditText)findViewById(R.id.ed_add_num);
        ed_add_num.setVisibility(View.INVISIBLE);

        tv_detail_1 =(TextView)findViewById(R.id.tv_detail_1);
        tv_detail_1.setVisibility(View.INVISIBLE);

        tv_detail_2 =(TextView)findViewById(R.id.tv_detail_2);
        tv_detail_2.setVisibility(View.INVISIBLE);

        sp_2 = (Spinner) findViewById(R.id.sp_time);
        adapter_time = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, format_2);
        sp_2.setAdapter(adapter_time);
        sp_2.setVisibility(View.INVISIBLE);



        sw_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sw_alarm.isChecked()){

                }else{
                   // sp.setVisibility(View.INVISIBLE);
                }
            }
        });

        sw_notification.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(sw_notification.isChecked()){
                    sp.setEnabled(true);
                    tv_detail_1.setTextColor(Color.parseColor("#000000"));
                    ed_add_num.setEnabled(true);
                    tv_detail_2.setTextColor(Color.parseColor("#000000"));
                    sp_2.setEnabled(true);
                    tv_notification_setting.setTextColor(Color.parseColor("#000000"));
                }else{
                    sp.setEnabled(false);
                    tv_detail_1.setTextColor(Color.parseColor("#cac8ca"));
                    ed_add_num.setEnabled(false);
                    tv_detail_2.setTextColor(Color.parseColor("#cac8ca"));
                    sp_2.setEnabled(false);
                    tv_notification_setting.setTextColor(Color.parseColor("#cac8ca"));
                }
            }
        });

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: temp = 0;
                        tv_detail_1.setVisibility(View.GONE);  // จำนวนที่ต้องการให้แจ้งล่วงหน้า
                        ed_add_num.setVisibility(View.GONE);   // ช่องเติมระบุจำนวนคิว
                        tv_detail_2.setVisibility(View.GONE);  // ก่อนระยะเวลาที่กำหนด
                        sp_2.setVisibility(View.GONE);         // sp2
                        break; //ทุกคิว
                    case 1: temp = 1;
                        tv_detail_1.setVisibility(View.VISIBLE);  // จำนวนที่ต้องการให้แจ้งล่วงหน้า
                        ed_add_num.setVisibility(View.VISIBLE);   // ช่องเติมระบุจำนวนคิว
                        tv_detail_2.setVisibility(View.GONE);  // ก่อนระยะเวลาที่กำหนด
                        sp_2.setVisibility(View.GONE);         // sp2
                        break; //จำนวนที่กำหนด
                    case 2: temp = 2;
                        tv_detail_1.setVisibility(View.GONE);  // จำนวนที่ต้องการให้แจ้งล่วงหน้า
                        ed_add_num.setVisibility(View.GONE);   // ช่องเติมระบุจำนวนคิว
                        tv_detail_2.setVisibility(View.VISIBLE);  // ก่อนระยะเวลาที่กำหนด
                        sp_2.setVisibility(View.VISIBLE);         // sp2
                    break; //ก่อนเวลาที่กำหนด
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }}


        );

        sp_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: temp_1 = 0;   break; //
                    case 1: temp_1 = 1;   break;
                    case 2: temp_1 = 2;   break;
                    case 3: temp_1 = 3;   break;
                    case 4: temp_1 = 4;   break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }



}




