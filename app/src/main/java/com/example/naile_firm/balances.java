package com.example.naile_firm;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.R.layout.simple_spinner_item;

public class balances extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<String> names = new ArrayList<String>();
    final Calendar myCalendar = Calendar.getInstance();
    public Calendar myCalendar2=Calendar.getInstance();
    public Spinner spinner;
    final String TAG=this.getClass().getSimpleName();
    public ListView lvProduct;
    ArrayAdapter<CharSequence>adapter;
    String url = "http://192.168.43.78/www/html/Naile_progect/reports.php";
    String url2 = "http://192.168.43.78/www/html/Naile_progect/reports2.php";
    String url3 = "http://192.168.43.78/www/html/Naile_progect/reports3.php";
    String url4 = "http://192.168.43.78/www/html/Naile_progect/selectcar.php";
   public String name,id1,startdates,enddates;
   public Button btn;
  public  EditText startdate,enddate;
  SessionManager sessionManager;

    public String typeexpected,idcar;
    private static ProgressDialog mProgressDialog;
    private ArrayList<deleteraw> statuscheckArrayList;
    private ArrayList<productsraw> statuscheckArrayList2;
    private ArrayList<productsconfirm> statuscheckArrayList3;
    public Spinner spinner2;
    DrawerLayout mdrawerLayout;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balances);
       spinner=findViewById(R.id.spinner);
        mdrawerLayout=findViewById(R.id.drawerlayout);
        spinner2=findViewById(R.id.txt);
        lvProduct=findViewById(R.id.listview);
        btn=findViewById(R.id.getdata);
        startdate=findViewById(R.id.startdate);
        enddate=findViewById(R.id.enddate);
        secs();checkconn();checkconn2();datepicker();drawable();
        sessionManager=new SessionManager(this);

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
            cal2();

            spinner3();


        }
        else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(getApplicationContext(), "YOU ARE OFFLINE", Toast.LENGTH_LONG).show();
        }
    }





    public void spinner3(){
        adapter= ArrayAdapter.createFromResource(this,R.array.reports,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"selected",Toast.LENGTH_LONG).show();
                name=parent.getItemAtPosition(position).toString();
                if(name.equalsIgnoreCase("vehicle reports")){

                    spinner2.setVisibility(View.VISIBLE);

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            get_good_id();
                        }
                    });

                }else if(name.equalsIgnoreCase("production balances")){
                    spinner2.setVisibility(View.GONE);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        get_good_id2();
                    }
            });}
            else if(name.equalsIgnoreCase("raw materials balances")){
                    spinner2.setVisibility(View.GONE);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            get_good_id3();
                        }
                    });

                }}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }




    public void get_good_id(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,response);
                ArrayList<reports> jsonConverter=new JsonConverter<reports>().toArrayList(response, reports.class);

                BindDictionary<reports> dictionary=new BindDictionary<>();
                dictionary.addStringField(R.id.name, new StringExtractor<reports>() {
                    @Override
                    public String getStringValue(reports products, int position) {



                        return products.car_number;



                    }
                });
                dictionary.addStringField(R.id.trans_type, new StringExtractor<reports>() {
                    @Override
                    public String getStringValue(reports products, int position) {



                        return products.transtype ;



                    }
                });

                dictionary.addStringField(R.id.transquantity, new StringExtractor<reports>() {
                    @Override
                    public String getStringValue(reports products, int position) {



                        return products.transquantity ;



                    }
                });
                dictionary.addStringField(R.id.transdate, new StringExtractor<reports>() {
                    @Override
                    public String getStringValue(reports products, int position) {



                        return products.transdate;



                    }
                });
                dictionary.addStringField(R.id.transtime, new StringExtractor<reports>() {
                    @Override
                    public String getStringValue(reports products, int position) {



                        return products.transtime;



                    }
                });
                dictionary.addStringField(R.id.transid, new StringExtractor<reports>() {
                    @Override
                    public String getStringValue(reports products, int position) {



                        return products.transid;



                    }
                });


                FunDapter<reports> adapter=new FunDapter<>(getApplicationContext(),jsonConverter,R.layout.reports,dictionary);
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




startdates=startdate.getText().toString();
enddates=enddate.getText().toString();
               // id1=txt.getText().toString();



                params.put("id", idcar);
                params.put("startdate",startdates);
                params.put("enddate",enddates);




                return params;
            }
        };
        MySingleton.getInstance(balances.this).addToRequestQueue(stringRequest);


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


        enddate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                new DatePickerDialog(balances.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

}


public void cal2(){
    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, monthOfYear);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateLabel();
        }

    };

    startdate.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            // TODO Auto-generated method stub
            new DatePickerDialog(balances.this, date, myCalendar2
                    .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                    myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
        }
    });

}
    private void updateLabel() {
        String myFormat = "YYYY-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        startdate.setText(sdf.format(myCalendar2.getTime()));

        enddate.setText(sdf.format(myCalendar.getTime()));
    }


    public void get_good_id2(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,response);
                ArrayList<productionlines> jsonConverter=new JsonConverter<productionlines>().toArrayList(response, productionlines.class);

                BindDictionary<productionlines> dictionary=new BindDictionary<>();
                dictionary.addStringField(R.id.namemix1, new StringExtractor<productionlines>() {
                    @Override
                    public String getStringValue(productionlines products, int position) {



                        return products.namemix1;



                    }
                });
                dictionary.addStringField(R.id.namemix2, new StringExtractor<productionlines>() {
                    @Override
                    public String getStringValue(productionlines products, int position) {



                        return products.namemix2 ;



                    }
                });

                dictionary.addStringField(R.id.namemix3, new StringExtractor<productionlines>() {
                    @Override
                    public String getStringValue(productionlines products, int position) {



                        return products.namemix3 ;



                    }
                });
                dictionary.addStringField(R.id.quantity1, new StringExtractor<productionlines>() {
                    @Override
                    public String getStringValue(productionlines products, int position) {



                        return products.quantity1;



                    }
                });
                dictionary.addStringField(R.id.quantity2, new StringExtractor<productionlines>() {
                    @Override
                    public String getStringValue(productionlines products, int position) {



                        return products.quantity2;



                    }
                });
                dictionary.addStringField(R.id.quantity3, new StringExtractor<productionlines>() {
                    @Override
                    public String getStringValue(productionlines products, int position) {



                        return products.quantity3;



                    }
                });
                dictionary.addStringField(R.id.typeexpected, new StringExtractor<productionlines>() {
                    @Override
                    public String getStringValue(productionlines products, int position) {



                        return products.typeexpected ;



                    }
                });
                dictionary.addStringField(R.id.mixid, new StringExtractor<productionlines>() {
                    @Override
                    public String getStringValue(productionlines products, int position) {



                        return products.mixid ;



                    }
                });
                dictionary.addStringField(R.id.time, new StringExtractor<productionlines>() {
                    @Override
                    public String getStringValue(productionlines products, int position) {



                        return products.time ;



                    }
                });

                dictionary.addStringField(R.id.totals, new StringExtractor<productionlines>() {
                    @Override
                    public String getStringValue(productionlines products, int position) {



                        return products.totals ;



                    }
                });

                FunDapter<productionlines> adapter=new FunDapter<>(getApplicationContext(),jsonConverter,R.layout.reports2production,dictionary);
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


                startdates=startdate.getText().toString();
                enddates=enddate.getText().toString();




                params.put("startdate",startdates);
                params.put("enddate",enddates);





                return params;
            }
        };
        MySingleton.getInstance(balances.this).addToRequestQueue(stringRequest);


    }
    public void get_good_id3(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,response);
                ArrayList<rawmateriasl> jsonConverter=new JsonConverter<rawmateriasl>().toArrayList(response, rawmateriasl.class);

                BindDictionary<rawmateriasl> dictionary=new BindDictionary<>();

                dictionary.addStringField(R.id.name, new StringExtractor<rawmateriasl>() {
                    @Override
                    public String getStringValue(rawmateriasl products, int position) {



                        return products.name;



                    }
                });
                dictionary.addStringField(R.id.type, new StringExtractor<rawmateriasl>() {
                    @Override
                    public String getStringValue(rawmateriasl products, int position) {



                        return products.type ;



                    }
                });

                dictionary.addStringField(R.id.quantity, new StringExtractor<rawmateriasl>() {
                    @Override
                    public String getStringValue(rawmateriasl products, int position) {



                        return products.quantity ;



                    }
                });
                dictionary.addStringField(R.id.date, new StringExtractor<rawmateriasl>() {
                    @Override
                    public String getStringValue(rawmateriasl products, int position) {



                        return products.date;



                    }
                });
                dictionary.addStringField(R.id.id, new StringExtractor<rawmateriasl>() {
                    @Override
                    public String getStringValue(rawmateriasl products, int position) {



                        return products.id;



                    }
                });
                dictionary.addStringField(R.id.used, new StringExtractor<rawmateriasl>() {
                    @Override
                    public String getStringValue(rawmateriasl products, int position) {



                        return products.used;



                    }
                });
                dictionary.addStringField(R.id.remaining, new StringExtractor<rawmateriasl>() {
                    @Override
                    public String getStringValue(rawmateriasl products, int position) {



                        return products.remaining;



                    }
                });


                FunDapter<rawmateriasl> adapter=new FunDapter<>(getApplicationContext(),jsonConverter,R.layout.reports3rawmats,dictionary);
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




                startdates=startdate.getText().toString();
                enddates=enddate.getText().toString();
               // id1=txt.getText().toString();




                params.put("startdate",startdates);
                params.put("enddate",enddates);




                return params;
            }
        };
        MySingleton.getInstance(balances.this).addToRequestQueue(stringRequest);


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

    private void getjson(){


        showSimpleProgressDialog(this, "Loading...","Fetching Json",true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url4,
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


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(balances.this,simple_spinner_item, names);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                spinner2.setAdapter(spinnerArrayAdapter);
                                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String slectedname = parent.getItemAtPosition(position).toString();

                                        Toast.makeText(getApplicationContext(), "Entered: "+slectedname, Toast.LENGTH_LONG).show();
                                       idcar=slectedname;

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                removeSimpleProgressDialog();

                            }else {
                                Toast.makeText(getApplicationContext(),"no data specified exist",Toast.LENGTH_SHORT).show();
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

    private void showSimpleProgressDialog(balances addcar, String s, String fetching_json, boolean b) {
    }

}
