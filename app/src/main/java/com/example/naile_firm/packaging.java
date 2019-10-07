package com.example.naile_firm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kosalgeek.android.json.JsonConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.layout.simple_spinner_item;

public class packaging extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    final String TAG = this.getClass().getSimpleName();
    ListView lvProduct;
    String url = "http://192.168.43.78/www/html/Naile_progect/sackgen.php";
    String url2 = "http://192.168.43.78/www/html/Naile_progect/getdata.php";
    String url3 = "http://192.168.43.78/www/html/Naile_progect/getcount.php";
    String url4 = "http://192.168.43.78/www/html/Naile_progect/updatevalues.php";
    String url5= "http://192.168.43.78/www/html/Naile_progect/getstatus.php";




    private static ProgressDialog mProgressDialog;
    private ArrayList<mainid> statuscheckArrayList;
    private ArrayList<String> names = new ArrayList<String>();
    private Spinner spinner;

    public Button getdata, changestatus;
    public EditText getid;
    DrawerLayout mdrawerLayout;
    private Toolbar toolbar;
    public String selectedname,statuss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packaging);
        lvProduct = findViewById(R.id.listviewpack);
        mdrawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolBar2);
        setSupportActionBar(toolbar);
        spinner = findViewById(R.id.spinnerpack);
        changestatus = findViewById(R.id.changestatus);
        getid = findViewById(R.id.getidpack);


        getdata = findViewById(R.id.getdatapack);



checkconn();
        drawableb();
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



            retrieveJSON();

            click();

        }
        else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(getApplicationContext(), "YOU ARE OFFLINE", Toast.LENGTH_LONG).show();
        }
    }


    public void getdatabtn() {
        getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getid.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "fill in the total number of bags", Toast.LENGTH_LONG).show();
                }else{
                get_good_id();}

            }
        });
    }


    public void get_good_id() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                ArrayList<products3> jsonConverter = new JsonConverter<products3>().toArrayList(response, products3.class);

                BindDictionary<products3> dictionary = new BindDictionary<>();
                dictionary.addStringField(R.id.index1, new StringExtractor<products3>() {
                    @Override
                    public String getStringValue(products3 products, int position) {


                        return products.indexno;


                    }
                });
                dictionary.addStringField(R.id.id1, new StringExtractor<products3>() {
                    @Override
                    public String getStringValue(products3 products, int position) {


                        return products.gen_id;


                    }
                });


                FunDapter<products3> adapter = new FunDapter<>(getApplicationContext(), jsonConverter, R.layout.product_layout2, dictionary);
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


                String id = getid.getText().toString();


                params.put("id", id);
                params.put("mixid", selectedname);


                return params;
            }
        };
        MySingleton.getInstance(packaging.this).addToRequestQueue(stringRequest);


    }




    public void drawableb() {
        NavigationView nav_view = findViewById(R.id.nav_view);
        ActionBarDrawerToggle darwertoggle = new ActionBarDrawerToggle(this, mdrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mdrawerLayout.addDrawerListener(darwertoggle);
        darwertoggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_rawm_entry:
                Intent i = new Intent(".rawm_entry");
                startActivity(i);
                break;
            case R.id.nav_raw_mixing:
                Intent i2 = new Intent(".rawmmixing");
                startActivity(i2);
                break;
            case R.id.nav_get_contents:
                Intent i3 = new Intent(".get_products_contents");
                startActivity(i3);
                break;
            case R.id.nav_home:
                Intent i4 = new Intent(".home");
                startActivity(i4);
                break;
            case R.id.trans_chuka:
                Intent i8 = new Intent(".transist");
                startActivity(i8);
                break;
            case R.id.transist_B:
                Intent i9 = new Intent(".trans_b");
                startActivity(i9);
                break;
            case R.id.transist_C:
                Intent i10 = new Intent(".trans_c");
                startActivity(i10);
                break;
            case R.id.nav_arriv_chuka:
                Intent i6 = new Intent(".arrivals_chuka");
                startActivity(i6);
                break;
            case R.id.status_chuka:
                Intent i7 = new Intent(".status");
                startActivity(i7);
                break;
            case R.id.status_B:
                Intent i11 = new Intent(".status_b");
                startActivity(i11);
                break;
            case R.id.status_C:
                Intent i12 = new Intent(".status_c");
                startActivity(i12);
                break;

        }
        mdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mdrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mdrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    private void retrieveJSON() {

        showSimpleProgressDialog(this, "Loading...", "Fetching Json", false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);


                        try {

                            JSONObject obj = new JSONObject(response);
                            if (obj.optString("status").equals("true")) {

                                statuscheckArrayList = new ArrayList<>();
                                JSONArray dataArray = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    mainid playerModel = new mainid();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setmainid(dataobj.getString("mixid"));


                                    statuscheckArrayList.add(playerModel);

                                }

                                for (int i = 0; i < statuscheckArrayList.size(); i++) {
                                    names.add(statuscheckArrayList.get(i).getmainid());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(packaging.this, simple_spinner_item, names);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                spinner.setAdapter(spinnerArrayAdapter);
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        getdata.setEnabled(false);
                                        validate();
                                        selectedname = parent.getItemAtPosition(position).toString();
                                        get_good_id2();

                                        Toast.makeText(getApplicationContext(), "selected: " + selectedname, Toast.LENGTH_LONG).show();





                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
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

    public void get_good_id2() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                ArrayList<products4> jsonConverter = new JsonConverter<products4>().toArrayList(response, products4.class);

                BindDictionary<products4> dictionary = new BindDictionary<>();

                dictionary.addStringField(R.id.idtotals, new StringExtractor<products4>() {
                    @Override
                    public String getStringValue(products4 products, int position) {


                        return products.count;


                    }
                });


                FunDapter<products4> adapter = new FunDapter<>(getApplicationContext(), jsonConverter, R.layout.product_layout3, dictionary);
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


                params.put("id", selectedname);


                return params;
            }
        };
        MySingleton.getInstance(packaging.this).addToRequestQueue(stringRequest);


    }


    public void packagingcomplete() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url4, new Response.Listener<String>() {
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
                Map<String, String> params = new HashMap<String, String>();


                params.put("mixid", selectedname);
                return params;
            }
        };
        MySingleton.getInstance(packaging.this).addToRequestQueue(stringRequest);
    }

    public void clickalalter() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set alert_dialog.xml to alertdialog builder


        // set dialog message
        alertDialogBuilder
                .setTitle("Click okey to submit ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

packagingcomplete();

                        Toast.makeText(packaging.this,"status saved",Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    public void click() {

        changestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickalalter();

            }
        });

    }



    public void validate(){


            StringRequest stringRequest = new StringRequest(Request.Method.POST, url5, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, response);
                    ArrayList<product5> jsonConverter = new JsonConverter<product5>().toArrayList(response, product5.class);

                    BindDictionary<product5> dictionary = new BindDictionary<>();
                    dictionary.addStringField(R.id.notvisible, new StringExtractor<product5>() {
                        @Override
                        public String getStringValue(product5 products, int position) {
statuss=products.status;

if(statuss.equals("complete")){

    getdata.setClickable(false);


    Toast.makeText(packaging.this,statuss,Toast.LENGTH_SHORT).show();
}
else if(statuss.equals("incomplete")){
    getdata.setEnabled(true);
    getdata.setClickable(true);
    Toast.makeText(packaging.this,statuss,Toast.LENGTH_SHORT).show();
    getdatabtn();

}


                           return null;


                        }
                    });



                    FunDapter<product5> adapter = new FunDapter<>(getApplicationContext(), jsonConverter, R.layout.layout_4, dictionary);
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





params.put("mixid",selectedname);

                    return params;
                }
            };
            MySingleton.getInstance(packaging.this).addToRequestQueue(stringRequest);


        }








    }





