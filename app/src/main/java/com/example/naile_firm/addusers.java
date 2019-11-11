package com.example.naile_firm;

import android.content.Intent;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addusers);
idtxt=findViewById(R.id.idusers);
nametxt=findViewById(R.id.nameusers);
locationtxt=findViewById(R.id.locationusers);
prevelagetxt=findViewById(R.id.prevelage);
        mdrawerLayout=findViewById(R.id.drawerlayout_b);
save_raw_mat_data();
drawable2();


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
    public void drawable2(){

        ActionBarDrawerToggle darwertoggle=new ActionBarDrawerToggle(this,mdrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        mdrawerLayout.addDrawerListener(darwertoggle);
        darwertoggle.syncState();
        NavigationView nav_view=findViewById(R.id.nav_viewb);
        nav_view.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                Intent i=new Intent(".home");
                startActivity(i);
                break;
            case R.id.nav_arriv_B:
                Intent i2=new Intent(".arrivals_b");
                startActivity(i2);
                break;
            case R.id.status_B:
                Intent i3=new Intent(".status_b");
                startActivity(i3);
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
