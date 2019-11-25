package com.example.naile_firm;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class get_products_contents extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener  {
    final String TAG=this.getClass().getSimpleName();
    ListView lvProduct;

    String url = "http://192.168.43.78/www/html/Naile_progect/get_good_content.php";
    public Button getdata;
    public EditText getid;
    DrawerLayout mdrawerLayout;
    private Toolbar toolbar;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_products_contents);
        lvProduct=findViewById(R.id.listview);
        mdrawerLayout=findViewById(R.id.drawerlayout);
        toolbar=findViewById(R.id.toolBar2);
        setSupportActionBar(toolbar);
        sessionManager=new SessionManager(this);
        getid=findViewById(R.id.getid);
        getdata=findViewById(R.id.getdata);
        drawable();
        checkconn();
        secs();



    }
    public void secs(){
        final Handler handler = new Handler();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkconn2();
                handler.postDelayed(this, 10000);
            }
        }, 10000);
    }




    public void  checkconn2(){
        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {




        }
        else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(getApplicationContext(), "YOU ARE OFFLINE", Toast.LENGTH_LONG).show();
        }
    }


    public void  checkconn(){
        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {




            getdatabtn();

        }
        else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(getApplicationContext(), "YOU ARE OFFLINE", Toast.LENGTH_LONG).show();
        }
    }




    public void getdatabtn(){
        getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getid.getText().toString().equals("")) {
                    Toast.makeText(get_products_contents.this, "You did not enter an id", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                get_good_id();

            }}
        });
    }





    public void get_good_id(){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG,response);

                    ArrayList<Products> jsonConverter=new JsonConverter<Products>().toArrayList(response, Products.class);

                    BindDictionary<Products> dictionary=new BindDictionary<>();
                    dictionary.addStringField(R.id.namemix1, new StringExtractor<Products>() {
                        @Override
                        public String getStringValue(Products products, int position) {



                            return products.namemix1;



                        }
                    });
                    dictionary.addStringField(R.id.namemix2, new StringExtractor<Products>() {
                        @Override
                        public String getStringValue(Products products, int position) {



                            return products.namemix2 ;



                        }
                    });

                    dictionary.addStringField(R.id.namemix3, new StringExtractor<Products>() {
                        @Override
                        public String getStringValue(Products products, int position) {



                            return products.namemix3 ;



                        }
                    });
                    dictionary.addStringField(R.id.quantity1, new StringExtractor<Products>() {
                        @Override
                        public String getStringValue(Products products, int position) {



                            return products.quantity1;



                        }
                    });
                    dictionary.addStringField(R.id.quantity2, new StringExtractor<Products>() {
                        @Override
                        public String getStringValue(Products products, int position) {



                            return products.quantity2;



                        }
                    });
                    dictionary.addStringField(R.id.quantity3, new StringExtractor<Products>() {
                        @Override
                        public String getStringValue(Products products, int position) {



                            return products.quantity3;



                        }
                    });
                    dictionary.addStringField(R.id.typeexpected, new StringExtractor<Products>() {
                        @Override
                        public String getStringValue(Products products, int position) {



                            return products.typeexpected ;



                        }
                    });
                    dictionary.addStringField(R.id.mixid, new StringExtractor<Products>() {
                        @Override
                        public String getStringValue(Products products, int position) {



                            return products.mixid ;



                        }
                    });
                    dictionary.addStringField(R.id.time, new StringExtractor<Products>() {
                        @Override
                        public String getStringValue(Products products, int position) {



                            return products.time ;



                        }
                    });

                    dictionary.addStringField(R.id.totals, new StringExtractor<Products>() {
                        @Override
                        public String getStringValue(Products products, int position) {



                            return products.totals ;



                        }
                    });
                    dictionary.addStringField(R.id.dateget, new StringExtractor<Products>() {
                        @Override
                        public String getStringValue(Products products, int position) {



                            return products.date;



                        }
                    });

                    FunDapter<Products> adapter=new FunDapter<>(getApplicationContext(),jsonConverter,R.layout.product_layout,dictionary);
                    lvProduct.setAdapter(adapter);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error while reading googl", Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();


                  String  id=getid.getText().toString();





                    params.put("id", id);



                    return params;
                }
            };
            MySingleton.getInstance(get_products_contents.this).addToRequestQueue(stringRequest);


        }

    public void drawable(){
        ActionBarDrawerToggle darwertoggle=new ActionBarDrawerToggle(this,mdrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        mdrawerLayout.addDrawerListener(darwertoggle);
        darwertoggle.syncState();
        NavigationView nav_view=findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_rawm_entry:
                Intent i=new Intent(".rawm_entry");
                startActivity(i);
                break;
            case R.id.nav_raw_mixing:
                Intent i2=new Intent(".rawmmixing");
                startActivity(i2);
                break;
            case R.id.nav_get_contents:
                Intent i3=new Intent(".get_products_contents");
                startActivity(i3);
                break;
            case R.id.nav_home:
                Intent i4=new Intent(".home");
                startActivity(i4);
                break;
            case R.id.trans_chuka:
                Intent i8=new Intent(".transist");
                startActivity(i8);
                break;

            case R.id.nav_arriv_chuka:
                Intent i6=new Intent(".arrivals_chuka");
                startActivity(i6);
                break;
            case R.id.status_chuka:
                Intent i7=new Intent(".status");
                startActivity(i7);
                break;

            case R.id.addproduct:
                Intent i13=new Intent(".addproducts");
                startActivity(i13);
                break;
            case R.id.productgen:
                Intent i14=new Intent(".packaging");
                startActivity(i14);
                break;
            case R.id.reports:
                Intent i1r=new Intent(".balances");
                startActivity(i1r);
                break;
            case R.id.addusers:
                Intent i2r=new Intent(".addusers");
                startActivity(i2r);
                break;

            case R.id.addraw:
                Intent i2r1=new Intent(".addnewrawmat");
                startActivity(i2r1);
                break;
            case R.id.addcar:
                Intent i2r2=new Intent(".addcar");
                startActivity(i2r2);
                break;
            case R.id.addsupplier:
                Intent i2r21=new Intent(".addsupplier");
                startActivity(i2r21);
                break;
            case R.id.logout:
                sessionManager.logout();
                (this).finish();
                break;

        }
        mdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mdrawerLayout.isDrawerOpen(GravityCompat.START)){
            mdrawerLayout.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();}

    }





}