package com.example.naile_firm;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


import java.util.ArrayList;

import static android.R.layout.simple_spinner_item;

public class rawmmixing extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ProgressBar loading;
    ConstraintLayout rootlayout;
    FloatingActionButton fbtnmix;
    private static ProgressDialog mProgressDialog;
    String url = "http://192.168.43.78/www/html/Naile_progect/mix.php";
    String url2 = "http://192.168.43.78/www/html/Naile_progect/get_products.php";
    String url3 = "http://192.168.43.78/www/html/Naile_progect/get_rawmat.php";
    String urlconfirm = "http://192.168.43.78/www/html/Naile_progect/validatingmix.php";
    String urlconfirmp1= "http://192.168.43.78/www/html/Naile_progect/mixcomfirm1.php";

    final String TAG=this.getClass().getSimpleName();
    private ArrayList<products2> statuscheckArrayList;
    private ArrayList<productsraw> statuscheckArrayList2;
    private ArrayList<productsconfirm> statuscheckArrayList3;
    SessionManager sessionManager;

    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> names2 = new ArrayList<String>();
    private ArrayList<String> names3 = new ArrayList<String>();
   public Spinner spinner,editmix1,editmix2,editmix3;

    public EditText quantitymix1,quantitymix2,quantitymix3,timet,date;
    public  TextView confirm11,confirm2,confirm3;
    ArrayAdapter<CharSequence>adapter;
    public String timemix,datemix,namemix1,namemix2,namemix3,quantity1,quantity2,quantity3,desiredString1,desiredString2,typeexpected,desiredString3,mixid;
    DrawerLayout mdrawerLayout;
    private Toolbar toolbar;
    public String idtype,product1,product2,product3,confirm1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rawmmixing);
        editmix1=findViewById(R.id.editmix1);
        editmix2=findViewById(R.id.editmix2);
        editmix3=findViewById(R.id.editmix3);
        timet=findViewById(R.id.timetxtmix);
        sessionManager=new SessionManager(this);
        rootlayout=findViewById(R.id.rootlayout);

        quantitymix1=findViewById(R.id.rawmix1);
        quantitymix2=findViewById(R.id.rawmix2);
        quantitymix3=findViewById(R.id.rawmix3);
        mdrawerLayout=findViewById(R.id.drawerlayout2);
        toolbar=findViewById(R.id.toolBar2);
        loading=findViewById(R.id.loading2);
        loading.setVisibility(View.GONE);



timepicker();
getjsonraw();

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
            floatbtn();
        }
        else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(getApplicationContext(), "YOU ARE OFFLINE", Toast.LENGTH_LONG).show();
        }
    }

    public void timepicker(){
        timet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);


                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(rawmmixing.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timet.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });}




public void popup(){


        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.popup, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
    spinner = (Spinner) promptsView
            .findViewById(R.id.spinnerpop);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                        // edit text

                        idgeneration();
                        save_raw_mat_data();

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

public void idgeneration(){
    CharSequence foo1 = editmix1.getSelectedItem().toString();
    String sub1 = foo1.toString();
 desiredString1 = sub1.substring(0,3);

    CharSequence foo2 = editmix1.getSelectedItem().toString();
    String sub2 = foo2.toString();
    desiredString2 = sub2.substring(0,3);

    CharSequence foo3 = editmix1.getSelectedItem().toString();
    String sub3 = foo3.toString();
    desiredString3= sub3.substring(0,3);

    mixid=desiredString1+desiredString2+desiredString3;


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

                                        products2 playerModel = new products2();
                                        JSONObject dataobj = dataArray.getJSONObject(i);

                                        playerModel.setId(dataobj.getString("id"));
                                        playerModel.setproductName(dataobj.getString("productname"));


                                        statuscheckArrayList.add(playerModel);

                                    }

                                    for (int i = 0; i < statuscheckArrayList.size(); i++){
                                        names.add(statuscheckArrayList.get(i).getproductName());
                                        idtype=(statuscheckArrayList.get(i).getId());
                                    }


                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(rawmmixing.this,simple_spinner_item, names);
                                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                    spinner.setAdapter(spinnerArrayAdapter);
                                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            String slectedname = parent.getItemAtPosition(position).toString();

                                            Toast.makeText(getApplicationContext(), "Entered: "+slectedname, Toast.LENGTH_LONG).show();
                                            typeexpected=slectedname;

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

public void save_raw_mat_data(){
    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d(TAG, response);

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

quantity1=quantitymix1.getText().toString();
quantity2=quantitymix2.getText().toString();
quantity3=quantitymix3.getText().toString();
   timemix=timet.getText().toString();
   params.put("namemix1",namemix1);
            params.put("namemix2",namemix2);
            params.put("namemix3",namemix3);
            params.put("quantity1",quantity1);
            params.put("quantity2",quantity2);
            params.put("quantity3",quantity3);
            params.put("time",timemix);

            params.put("typeexpected", typeexpected);


           params.put("mixid", mixid);

            return params;
        }
    };
    MySingleton.getInstance(rawmmixing.this).addToRequestQueue(stringRequest);

    Snackbar.make(rootlayout,"data saved succesfully",Snackbar.LENGTH_SHORT).show();
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






    public void floatbtn() {

        fbtnmix = findViewById(R.id.floatbtnmix);
        fbtnmix.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(editmix1.getSelectedItem().toString().equals("")||editmix2.getSelectedItem().toString().equals("")||editmix3.getSelectedItem().toString().equals("")||quantitymix1.getText().toString().equals("")||quantitymix2.getText().toString().equals("")||quantitymix3.getText().toString().equals("")||timet.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "fill in the missing field", Toast.LENGTH_SHORT).show();}

                else  if(editmix1.getSelectedItem().toString().length()<4||editmix2.getSelectedItem().toString().length()<4||editmix3.getSelectedItem().toString().length()<4){
                    Toast.makeText(getApplicationContext(), "please check the values of ur id and try again", Toast.LENGTH_LONG).show();
                }
                else {
                    loading.setVisibility(View.VISIBLE);
getjson2confirm();


                }


            }

        });}
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

    private void getjsonraw(){


        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);


                        try {

                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("status").equals("true")){

                                statuscheckArrayList2 = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    productsraw playerModel = new productsraw();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setId(dataobj.getString("index1"));
                                    playerModel.setType(dataobj.getString("infor"));


                                    statuscheckArrayList2.add(playerModel);

                                }

                                for (int i = 0; i < statuscheckArrayList2.size(); i++){
                                    names2.add(statuscheckArrayList2.get(i).getType());
                                    idtype=(statuscheckArrayList2.get(i).getId());
                                }


                                ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(rawmmixing.this,simple_spinner_item, names2);
                                spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                editmix1.setAdapter(spinnerArrayAdapter2);
                               editmix1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String slectedname1 = parent.getItemAtPosition(position).toString();

                                        Toast.makeText(getApplicationContext(), "Entered: "+slectedname1, Toast.LENGTH_LONG).show();
                                        namemix1=slectedname1;

                                        confirm1=slectedname1;
                                      //  retrieveJSON2(confirm1);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                                editmix2.setAdapter(spinnerArrayAdapter2);
                                editmix2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String slectedname2 = parent.getItemAtPosition(position).toString();

                                        Toast.makeText(getApplicationContext(), "Entered: "+slectedname2, Toast.LENGTH_LONG).show();
                                        namemix2=slectedname2;

                                       // retrieveJSON22(namemix2);



                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                editmix3.setAdapter(spinnerArrayAdapter2);
                                editmix3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String slectedname3 = parent.getItemAtPosition(position).toString();

                                        Toast.makeText(getApplicationContext(), "Entered: "+slectedname3, Toast.LENGTH_LONG).show();
                                        namemix3=slectedname3;
                                       // retrieveJSON3(namemix3);

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



    private void getjson2confirm(){


        //showSimpleProgressDialog(this, "Loading...","Fetching Json",true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlconfirm,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);


                        try {










                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("status").equals("true")){

                                statuscheckArrayList3 = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    productsconfirm playerModel = new productsconfirm();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setName1(dataobj.getString("name1"));
                                    playerModel.setName2(dataobj.getString("name2"));
                                    playerModel.setName3(dataobj.getString("name3"));
                                    playerModel.setName4(dataobj.getString("name4"));


                                    statuscheckArrayList3.add(playerModel);

                                }

                                for (int i = 0; i < statuscheckArrayList3.size(); i++){
                                     product1=statuscheckArrayList3.get(i).getName1();
                                    product2=statuscheckArrayList3.get(i).getName2();
                                    product3=statuscheckArrayList3.get(i).getName3();
                                    names3.add(statuscheckArrayList3.get(i).getName4());
                                    if(!product1.equalsIgnoreCase("good"))

                                    {
                                    Toast.makeText(getApplicationContext(), "not enough   :  "+product1  +"  please add stock", Toast.LENGTH_LONG).show();
                                        loading.setVisibility(View.GONE);
                                }
                                if(!product2.equalsIgnoreCase("good")){


                                    Toast.makeText(getApplicationContext(), "not enough   :   "+product2  +"  please add stock", Toast.LENGTH_LONG).show();
                                    loading.setVisibility(View.GONE);
                            }

                            if(!product3.equalsIgnoreCase("good"))
                            {

                                Toast.makeText(getApplicationContext(), "not enough   : "+product3 +"  please add stock", Toast.LENGTH_LONG).show();
                                loading.setVisibility(View.GONE);
                        }
                                if (statuscheckArrayList3.get(i).getName4().equalsIgnoreCase("save")) {
                                    loading.setVisibility(View.GONE);
                                    popup();
                                    getjson();

                                    Toast.makeText(getApplicationContext(), "SAVED", Toast.LENGTH_SHORT).show();
                                }





                            }

                        } }catch (JSONException e) {
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                quantity1=quantitymix1.getText().toString();
                quantity2=quantitymix2.getText().toString();
                quantity3=quantitymix3.getText().toString();
                timemix=timet.getText().toString();
                params.put("namemix1",namemix1);
                params.put("namemix2",namemix2);
                params.put("namemix3",namemix3);
                params.put("quantity1",quantity1);
                params.put("quantity2",quantity2);
                params.put("quantity3",quantity3);
                params.put("time",timemix);






                return params;
            }
        };
        MySingleton.getInstance(rawmmixing.this).addToRequestQueue(stringRequest);



    }


    private void retrieveJSON2(final String confirm1) {

        //   showSimpleProgressDialog(this, "Loading...", "Fetching Json", false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlconfirmp1,
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

                                    id3 playerModel = new id3();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setid(dataobj.getString("id"));

                                    String type=dataobj.getString("id");

                                   // confirm11.setText(type);

                                }












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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();





                params.put("id",confirm1);

                return params;
            }
        };
        MySingleton.getInstance(rawmmixing.this).addToRequestQueue(stringRequest);


    }


    private void retrieveJSON22(final String confirm1) {

        //   showSimpleProgressDialog(this, "Loading...", "Fetching Json", false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlconfirmp1,
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

                                    id3 playerModel = new id3();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setid(dataobj.getString("id"));

                                    String type=dataobj.getString("id");

                                 //   confirm11.setText(type);

                                }












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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();





                params.put("id",confirm1);

                return params;
            }
        };
        MySingleton.getInstance(rawmmixing.this).addToRequestQueue(stringRequest);


    }
    private void retrieveJSON3(final String confirm1) {

        //   showSimpleProgressDialog(this, "Loading...", "Fetching Json", false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlconfirmp1,
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

                                    id3 playerModel = new id3();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setid(dataobj.getString("id"));

                                    String type=dataobj.getString("id");

                             //       confirm11.setText(type);

                                }












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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();





                params.put("id",confirm1);

                return params;
            }
        };
        MySingleton.getInstance(rawmmixing.this).addToRequestQueue(stringRequest);


    }



}
