package com.example.naile_firm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import static android.R.layout.simple_spinner_item;

public class status_c extends  AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mdrawerLayout;
    private Toolbar toolbar;
    private static ProgressDialog mProgressDialog;
    private ArrayList<statuscheck> statuscheckArrayList;
    private ArrayList<String> names = new ArrayList<String>();
    private Spinner spinner;
    String url = "http://192.168.43.78/www/html/Naile_progect/no_matchc.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_c);
        mdrawerLayout=findViewById(R.id.drawerlayoutstatusc);
        toolbar=findViewById(R.id.toolBar2);
        spinner=findViewById(R.id.spinnerstatusc);
        retrieveJSON();
        drawable2();
    }


    public void drawable2(){

        ActionBarDrawerToggle darwertoggle=new ActionBarDrawerToggle(this,mdrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        mdrawerLayout.addDrawerListener(darwertoggle);
        darwertoggle.syncState();
        NavigationView nav_view=findViewById(R.id.nav_viewc);
        nav_view.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                Intent i=new Intent(".home");
                startActivity(i);
                break;
            case R.id.nav_arriv_c:
                Intent i2=new Intent(".arrivals_c");
                startActivity(i2);
                break;
            case R.id.status_C:
                Intent i3=new Intent(".status_c");
                startActivity(i3);
                break;
            case R.id.trans_chuka:
                Intent i8=new Intent(".transist");
                startActivity(i8);
                break;
            case R.id.transist_B:
                Intent i9=new Intent(".trans_b");
                startActivity(i9);
                break;
            case R.id.transist_C:
                Intent i10=new Intent(".trans_c");
                startActivity(i10);
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
    private void retrieveJSON() {

        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);


                        try {

                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("status").equals("true")){

                                statuscheckArrayList = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    statuscheck playerModel = new statuscheck();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setTransName(dataobj.getString("transname"));
                                    playerModel.setTranstype(dataobj.getString("transtype"));
                                    playerModel.setTransquantity(dataobj.getString("transquantity"));
                                    playerModel.setTransdate(dataobj.getString("transdate"));
                                    playerModel.setTranstime(dataobj.getString("transtime"));
                                    playerModel.setTransid(dataobj.getString("transid"));

                                    statuscheckArrayList.add(playerModel);

                                }

                                for (int i = 0; i < statuscheckArrayList.size(); i++){
                                    names.add(statuscheckArrayList.get(i).gettransName());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(status_c.this,simple_spinner_item, names);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                spinner.setAdapter(spinnerArrayAdapter);
                                removeSimpleProgressDialog();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


