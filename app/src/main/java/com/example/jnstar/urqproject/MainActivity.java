package com.example.jnstar.urqproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    TextView tv_specify_q;
    Button btn_fill_inform;

    TextView test2;

    /////////////////////////
    private static final int SIGN_IN_CODE =666 ;
    private Button btnLogout;
    String mCode;

    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference jRootRef = FirebaseDatabase.getInstance().getReference();
    /////////////////////////


    String checkListAdded;
    ListView lv_show_added;
    String [] arr_1;
    String [] arr_2;
    String [] arr_3;
    String [] arr_4;
    List<ListSearchStore> list;
    ArrayAdapter<ListSearchStore> adapter;

    String countAdd;
    String nameShop = ".";
    String noQ = ".";
    String noShop = ".";
    String qWait = ".";
    String countStatus = ".";
    int countFinish = 0;
    int countDoing = 0;
    int countQ = 0;
    int countFinishAndDoing = 0;

    TextView textShowList2_1 ;
    TextView textShowList2_2 ;
    int i = 0;
    String tempValue ="นอว์";
    int m=1;

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

        lv_show_added = (ListView)findViewById(R.id.lv_show_added);
        list = new ArrayList<ListSearchStore>();

        textShowList2_1 = (TextView)findViewById(R.id.textShowList2_1);
        textShowList2_2 = (TextView)findViewById(R.id.textShowList2_2);

        test2  = (TextView)findViewById(R.id.test2);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        firebaseAuth =FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //setUserData(user);
                    //read&write data

                    mRootRef.child("customer").child(user.getUid() + "").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            checkListAdded = String.valueOf(dataSnapshot.getChildrenCount());

                            list.clear();

                            if (Integer.parseInt(checkListAdded) == 0) {
                                tv_specify_q.setVisibility(View.VISIBLE);
                                lv_show_added.setVisibility(View.GONE);
                            } else {
                                tv_specify_q.setVisibility(View.GONE);
                                lv_show_added.setVisibility(View.VISIBLE);


                                countAdd = String.valueOf(dataSnapshot.child("Add").getChildrenCount());
                                arr_1 = new String[Integer.parseInt(countAdd)];
                                arr_2 = new String[Integer.parseInt(countAdd)];
                                arr_3 = new String[Integer.parseInt(countAdd)];
                                arr_4 = new String[Integer.parseInt(countAdd)];



                                      int  m=0;
                                    for (DataSnapshot shopSnapshot : dataSnapshot.child("Add").getChildren()) {


                                        nameShop = String.valueOf(shopSnapshot.child("nameShop").getValue());
                                        noShop = String.valueOf(shopSnapshot.child("noShop").getValue());
                                        noQ = String.valueOf(shopSnapshot.child("noQ").getValue());
                                        arr_1[m] = new String(nameShop);
                                        arr_2[m] = new String(noShop);
                                        arr_3[m] = new String(noQ);




                                        mRootRef.child("user").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    for (int i = 0 ;i<Integer.parseInt(countAdd);i++){

                                                        for (DataSnapshot _shopSnapshot: dataSnapshot.getChildren()) {
                                                            String nameAllUser = String.valueOf(_shopSnapshot.child("shopName").child("name").getValue());

                                                            if (arr_1[i].equals(nameAllUser)){

                                                                int k=1;

                                                                while (countStatus!="null") {

                                                                    countStatus = String.valueOf(_shopSnapshot.child("qNumber").child(k + "").child("status").getValue());

                                                                    if (countStatus.equals("finish")) {
                                                                        countFinish++;

                                                                    } else if (countStatus.equals("doing")) {
                                                                        countDoing++;

                                                                    } else if (countStatus.equals("q")) {
                                                                        countQ++;

                                                                    }

                                                                    k++;

                                                                }

                                                                countFinishAndDoing = countFinish+countDoing;

                                                                String statusAllUser = String.valueOf(_shopSnapshot.child("qNumber").child(arr_3[i]+"").child("status").getValue());
                                                                if (statusAllUser.equals("finish")){
                                                                    DatabaseReference qWaitShopRef = mRootRef.child("customer").child(user.getUid()).child("Add").child(arr_2[i]+"").child("qWait");
                                                                    qWaitShopRef.setValue("0");
                                                                }else if(statusAllUser.equals("doing")){
                                                                    DatabaseReference qWaitShopRef = mRootRef.child("customer").child(user.getUid()).child("Add").child(arr_2[i]+"").child("qWait");
                                                                    qWaitShopRef.setValue("0");
                                                                }else if(statusAllUser.equals("q")){
                                                                    DatabaseReference qWaitShopRef = mRootRef.child("customer").child(user.getUid()).child("Add").child(arr_2[i]+"").child("qWait");
                                                                    qWaitShopRef.setValue(Integer.parseInt(arr_3[i])-countFinishAndDoing);
                                                                }



                                                                countStatus = ".";
                                                                countFinish = 0;
                                                                countDoing = 0 ;
                                                                countQ = 0 ;



                                                            }




                                                        }

                                                    }


                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });


                                        qWait = String.valueOf(shopSnapshot.child("qWait").getValue());
                                        arr_4 [m] = new String(qWait+"");


                                        ListSearchStore l_search_store = new ListSearchStore(arr_1[m], arr_4[m]);
                                        list.add(l_search_store);

                                        m++;

                                    }



                            }


                            adapter = new ListSearchStore_adapter();
                            lv_show_added.setAdapter(adapter);

                            lv_show_added.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                                    intent.putExtra("location", Integer.parseInt(arr_2[position])); // String
                                    intent.putExtra("myNumber", arr_3[position]); // String
                                    //test2.setText(arr_2[position]+"----"+arr_3[position]);
                                    startActivity(intent);

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });


                }else{
                    GoLogInScrean();
                }
            }
        };








    }

    class ListSearchStore_adapter extends ArrayAdapter<ListSearchStore>{
        ListSearchStore_adapter(){
            super(MainActivity.this,R.layout.item_listview_2,list);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item_listview_2,parent,false);
            ListSearchStore l_search = list.get(position);

            TextView nameStore = (TextView)view.findViewById(R.id.nameShop);
            TextView qStore = (TextView)view.findViewById(R.id.qShop);

            nameStore.setText(l_search.getName_shop());
            qStore.setText(l_search.getQ_shop());


            return view;
        }


    }

   public void clickButtonEnter (View v){
        if(v == btn_fill_inform ){
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

    private void setUserData(FirebaseUser user) {
    /*    nameTextView.setText(user.getDisplayName());
        emailTextView.setText(user.getEmail());
        idTextView.setText(user.getUid());
        mCode = user.getUid();
        Glide.with(this).load(user.getPhotoUrl()).into(photoImageView);
       */
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.item_sound){
           Intent intent = new Intent(getApplicationContext(),Notification.class);
           startActivity(intent);
        }else if(id == R.id.action_search){
            Intent intent = new Intent(getApplicationContext(),SearchStore.class);
            startActivity(intent);
        }
       return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    public void logOut(View view){
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    GoLogInScrean();
                }else{
                    Toast.makeText(getApplicationContext(),"eiei",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void GoLogInScrean() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseAuthListener!=null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
}
