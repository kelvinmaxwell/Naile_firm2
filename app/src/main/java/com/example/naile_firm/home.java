package com.example.naile_firm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    SessionManager sessionManager;
    Button btnmain,btnchuka,btnb,btnc;
DrawerLayout mdrawerLayout;
    private ArrayList<arrivals_session> statuscheckArrayList;
    private ArrayList<String> names = new ArrayList<String>();
    String url = "http://192.168.43.78/www/html/Naile_progect/arrivchuka.php";
    public String session,mEmail,mName,mprevelage,mlocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       mdrawerLayout=findViewById(R.id.drawerlayout);
        toolbar=findViewById(R.id.toolBar2);
        btnmain=findViewById(R.id.main);
        btnchuka=findViewById(R.id.chuka);
        btnb=findViewById(R.id.B);
        btnc=findViewById(R.id.C);
        setSupportActionBar(toolbar);
        sessionManager=new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String,String> user=sessionManager.getUserDetail();
        mName=user.get(sessionManager.NAME);
       mEmail=user.get(sessionManager.EMAIL);
       mprevelage=user.get(SessionManager.PREVELAGE);
        mlocation=user.get(sessionManager.LOCATION);

btnchuka.setText(mlocation);

        Toast.makeText(getApplicationContext(),mName+mEmail,Toast.LENGTH_LONG).show();

        //ActionBarDrawerToggle darwertoggle=new ActionBarDrawerToggle(this,mdrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);

localhome();
        drawableb();

    }

    public void   localhome(){
        btnchuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(".arrivals_chuka");
                startActivity(i);
            }
        });

        btnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(".arrivals_b");
                startActivity(i);
            }
        });
        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(".arrivals_c");
                startActivity(i);
            }
        });
        btnmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mprevelage.equalsIgnoreCase("ADMIN")||mprevelage.equalsIgnoreCase("management")){
                Intent i=new Intent(".rawm_entry");
                startActivity(i);
            }else{
                    btnmain.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"access denied,contact management",Toast.LENGTH_SHORT).show();
            }}
        });

    }


    public void drawableb(){
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
            case R.id.logout:
               sessionManager.logout();
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
