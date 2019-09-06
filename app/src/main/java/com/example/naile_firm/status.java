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
import static android.R.layout.simple_spinner_item;

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
import java.util.List;

public class status extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mdrawerLayout;
    private Toolbar toolbar;
    String url = "http://192.168.43.78/www/html/Naile_progect/no_match.php";
    private static ProgressDialog mProgressDialog;
    private ArrayList<statuscheck> statuscheckArrayList;
    private ArrayList<String> names = new ArrayList<String>();
    private Spinner spinner;
    final String TAG=this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        mdrawerLayout=findViewById(R.id.drawerlayoutstatus);
        toolbar=findViewById(R.id.toolBar2);
        spinner=findViewById(R.id.spinnerstatus);
        drawableb();

        retrieveJSON();
    }







    public void drawableb(){
        NavigationView nav_view=findViewById(R.id.nav_view);
        ActionBarDrawerToggle darwertoggle=new ActionBarDrawerToggle(this,mdrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        mdrawerLayout.addDrawerListener(darwertoggle);
        darwertoggle.syncState();

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
            case R.id.nav_transist:
                Intent i5=new Intent(".transist");
                startActivity(i5);
                break;
            case R.id.nav_arriv_chuka:
                Intent i6=new Intent(".arrivals_chuka");
                startActivity(i6);
                break;
            case R.id.status_chuka:
                Intent i7=new Intent(".status");
                startActivity(i7);
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

                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(status.this,simple_spinner_item, names);
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


