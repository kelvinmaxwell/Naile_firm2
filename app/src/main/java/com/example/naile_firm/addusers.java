package com.example.naile_firm;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class addusers extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public FloatingActionButton myFab;
    final String TAG=this.getClass().getSimpleName();
    String url = "http://192.168.43.78/www/html/Naile_progect/users.php";
    DrawerLayout mdrawerLayout;
    private Toolbar toolbar;
 public String id,name,location,prevelage;
 public EditText idtxt,nametxt,locationtxt,prevelagetxt;
 SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addusers);
idtxt=findViewById(R.id.idusers);
nametxt=findViewById(R.id.nameusers);
locationtxt=findViewById(R.id.locationusers);
prevelagetxt=findViewById(R.id.prevelage);
        mdrawerLayout=findViewById(R.id.drawerlayout);
        sessionManager=new SessionManager(this);

        checkconn();
        secs();
        drawable();
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


            save_raw_mat_data();



        }
        else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(getApplicationContext(), "YOU ARE OFFLINE", Toast.LENGTH_LONG).show();
        }
    }








    public void save_raw_mat_data() {

        myFab = findViewById(R.id.arrivfloatusers);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "SAVED", Toast.LENGTH_SHORT).show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error while reading nertwork", Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String,String>();
                        id=idtxt.getText().toString();
                       name=nametxt.getText().toString();
                       location=locationtxt.getText().toString();
                       prevelage=prevelagetxt.getText().toString();

                        params.put("name", name);
                        params.put("id", id);
                        params.put("location", location);
                        params.put("prevelage", prevelage);

                        return params;
                    }
                };
                MySingleton.getInstance(addusers .this).addToRequestQueue(stringRequest);}

        });


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
