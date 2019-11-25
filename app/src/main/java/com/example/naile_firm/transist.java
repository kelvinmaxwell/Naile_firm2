package com.example.naile_firm;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.R.layout.simple_spinner_item;

public class transist extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    final Calendar myCalendar = Calendar.getInstance();
   EditText rawmatspinner;
    ArrayAdapter<CharSequence>adapter;
    public String type,quantity,time,id,name,date;
    public EditText quantitytxt,timetxt,datetxt,idtxt;
    public FloatingActionButton myFab;
    final String TAG=this.getClass().getSimpleName();
    String url = "http://192.168.43.78/www/html/Naile_progect/transist.php";
    String url2 = "http://192.168.43.78/www/html/Naile_progect/selectcar.php";
    String url5 = "http://192.168.43.78/www/html/Naile_progect/get_products.php";
    DrawerLayout mdrawerLayout;
    private Toolbar toolbar;
    private ArrayList<String> names3 = new ArrayList<String>();
    public String typeexpected;
    private ArrayList<String> names = new ArrayList<String>();
    private static ProgressDialog mProgressDialog;
    private ArrayList<deleteraw> statuscheckArrayList;
    private ArrayList<products2> statuscheckArrayList1;
    private ArrayList<productsraw> statuscheckArrayList2;
    private ArrayList<productsconfirm> statuscheckArrayList3;
    public Spinner spinner,typetxt;
SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transist);
        spinner=findViewById(R.id.transtypetxt);
        typetxt=findViewById(R.id.transspinner);
        quantitytxt=findViewById(R.id.transquantitytxt);
        datetxt=findViewById(R.id.transdatetxt);
        timetxt=findViewById(R.id.transtimetxt);
        idtxt=findViewById(R.id.transidtxt);
        mdrawerLayout=findViewById(R.id.drawerlayout);
        toolbar=findViewById(R.id.toolBar2);
        sessionManager=new SessionManager(this);
        drawable();
       // spinner();
        datepicker();
        timepicker();
checkconn();

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
            getjson();
            getjson2();
            save_raw_mat_data();
        }
        else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(getApplicationContext(), "YOU ARE OFFLINE", Toast.LENGTH_LONG).show();
        }
    }



    public void timepicker(){
        timetxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);


                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(transist.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timetxt.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

    }

    public void  datepicker(){
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };

        datetxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                new DatePickerDialog(transist.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }



    private void updateLabel() {
        String myFormat = "YYYY-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        datetxt.setText(sdf.format(myCalendar.getTime()));
    }







    public void save_raw_mat_data() {

        myFab = findViewById(R.id.transfloatbtn);
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
                        //name=rawmatspinner.getText().toString();
                       // type=typetxt.getText().toString();
                        quantity=quantitytxt.getText().toString();
                        time=timetxt.getText().toString();
                        id=idtxt.getText().toString();
                        date=datetxt.getText().toString();
                        params.put("name", name);
                        params.put("type", type);
                        params.put("quantity", quantity);
                        params.put("date", date);
                        params.put("time", time);


                        params.put("id", id);
                        return params;
                    }
                };
                MySingleton.getInstance(transist .this).addToRequestQueue(stringRequest);}

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

    private void getjson(){


        showSimpleProgressDialog(this, "Loading...","Fetching Json",true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
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

                                    deleteraw playerModel = new deleteraw();
                                    JSONObject dataobj = dataArray.getJSONObject(i);


                                    playerModel.setName(dataobj.getString("name"));


                                    statuscheckArrayList.add(playerModel);

                                }

                                for (int i = 0; i < statuscheckArrayList.size(); i++){
                                    names.add(statuscheckArrayList.get(i).getName());

                                }


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(transist.this,simple_spinner_item, names);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                              typetxt.setAdapter(spinnerArrayAdapter);
                                typetxt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String slectedname = parent.getItemAtPosition(position).toString();

                                        Toast.makeText(getApplicationContext(), "Entered: "+slectedname, Toast.LENGTH_LONG).show();
                                       name=slectedname;

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

                    private void removeSimpleProgressDialog() {
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

    private void showSimpleProgressDialog(transist addcar, String s, String fetching_json, boolean b) {
    }



    private void getjson2(){


        showSimpleProgressDialog(this, "Loading...","Fetching Json",true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url5,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);


                        try {










                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("status").equals("true")){

                                statuscheckArrayList1 = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    products2 playerModel = new products2();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setId(dataobj.getString("id"));
                                    playerModel.setproductName(dataobj.getString("productname"));


                                    statuscheckArrayList1.add(playerModel);

                                }

                                for (int i = 0; i < statuscheckArrayList1.size(); i++){
                                    names3.add(statuscheckArrayList1.get(i).getproductName());
                                   // idtype=(statuscheckArrayList1.get(i).getId());
                                }


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(transist.this,simple_spinner_item, names3);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                spinner.setAdapter(spinnerArrayAdapter);
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String slectedname = parent.getItemAtPosition(position).toString();

                                        Toast.makeText(getApplicationContext(), "Entered: "+slectedname, Toast.LENGTH_LONG).show();
                                        type=slectedname;

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


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


}

