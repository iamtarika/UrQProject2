package com.example.jnstar.urqproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReserveOnline extends AppCompatActivity {


    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    String countStatus = ".";
    int countQ ;
    int countOpenReserve = 0 ;
    String [] arr1;
    String [] arr2;

    ListView listViewReserve;

    List<ListSearchStore> list;
    ArrayAdapter<ListSearchStore> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_online);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listViewReserve = (ListView) findViewById(R.id.listViewReserve);
        list = new ArrayList<ListSearchStore>();

        final TextView ttt = (TextView)findViewById(R.id.ttt);


            mRootRef.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot shopSnapshot: dataSnapshot.getChildren()) {

                    String statusReserve = String.valueOf(shopSnapshot.child("shopName").child("statusReserve").getValue());
                    if (statusReserve.equals("1")){
                        countOpenReserve = countOpenReserve +1;
                    }

                }

                arr1 = new String[countOpenReserve];
                arr2 = new String[countOpenReserve];

                int i =0;

                for (DataSnapshot shopSnapshot: dataSnapshot.getChildren()) {

                    String statusReserve = String.valueOf(shopSnapshot.child("shopName").child("statusReserve").getValue());
                    if (statusReserve.equals("1")){
                            String shopName = String.valueOf(shopSnapshot.child("shopName").child("name").getValue());

                            arr1[i] = new String(shopName);

                            int k=1;
                            countQ =0;
                            countStatus = ".";
                            while (!countStatus.equals("null")){
                                countStatus = String.valueOf(shopSnapshot.child("qNumber").child(k+"").child("status").getValue());
                                if(countStatus.equals("q")){
                                    countQ++;
                                }
                                k++;
                            }

                            arr2[i] = new String(countQ+"");

                            ListSearchStore l_search_store = new ListSearchStore(arr1[i],arr2[i]);
                            list.add(l_search_store);

                            i++;

                    }

                }
                adapter = new ReserveOnline.ListSearchStore_adapter();
                listViewReserve.setAdapter(adapter);

                listViewReserve.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ReserveOnline.this, Reservation.class);
                        intent.putExtra("shopName", arr1[position]); // String ----> ส่งชื่อร้านไป
                        startActivity(intent);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    class ListSearchStore_adapter extends ArrayAdapter<ListSearchStore>{
        ListSearchStore_adapter(){
            super(ReserveOnline.this,R.layout.item_listview_3,list);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item_listview_3,parent,false);
            ListSearchStore l_search = list.get(position);

            TextView nameStore = (TextView)view.findViewById(R.id.nameReserveStore);
            TextView qStore = (TextView)view.findViewById(R.id.qReserveStore);

            nameStore.setText(l_search.getName_shop());
            qStore.setText(l_search.getQ_shop());


            return view;
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
