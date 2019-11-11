package com.example.naile_firm;



import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
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

public class arrivals_chuka extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    final Calendar myCalendar = Calendar.getInstance();
    Spinner rawmatspinner;
    ArrayAdapter<CharSequence>adapter;
    public String type,quantity,time,id,name,date;
    public EditText typetxt,quantitytxt,timetxt,datetxt,idtxt;
    public FloatingActionButton myFab;
    final String TAG=this.getClass().getSimpleName();
    String url = "http://192.168.43.78/www/html/Naile_progect/arrivchuka.php";
    DrawerLayout mdrawerLayout;
    private Toolbar toolbar;
    private ArrayList<arrivals_session> statuscheckArrayList;
public Button btn;

    private ArrayList<String> names = new ArrayList<String>();
    public String session;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrivals_chuka);
        rawmatspinner=findViewById(R.id.arrivspinner);
        typetxt=findViewById(R.id.arrivtypetxt);
        quantitytxt=findViewById(R.id.arrivquantitytxt);
        datetxt=findViewById(R.id.arrivdatetxt);
        timetxt=findViewById(R.id.arrivtimetxt);
        idtxt=findViewById(R.id.arrividtxt);
        mdrawerLayout=findViewById(R.id.drawerlayout_chuka);
        toolbar=findViewById(R.id.toolBar2);
btn=findViewById(R.id.button2);
        drawable2();
        spinner();
        datepicker();
        timepicker();

        save_raw_mat_data();
        getjson();
        clock();
    }

     public void clock(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Toast.makeText(getApplicationContext(),session,Toast.LENGTH_LONG).show();
            }
        });
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
                mTimePicker = new TimePickerDialog(arrivals_chuka.this, new TimePickerDialog.OnTimeSetListener() {
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
                new DatePickerDialog(arrivals_chuka.this, date, myCalendar
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





    public void spinner(){
        adapter=ArrayAdapter.createFromResource(this,R.array.country_arrays,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rawmatspinner.setAdapter(adapter);
        rawmatspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"selected",Toast.LENGTH_LONG).show();
                name=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void save_raw_mat_data() {

        myFab = findViewById(R.id.arrivfloatbtn);
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
                        type=typetxt.getText().toString();
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
                MySingleton.getInstance(arrivals_chuka .this).addToRequestQueue(stringRequest);}

        });


    }
    public void drawable2(){

        ActionBarDrawerToggle darwertoggle=new ActionBarDrawerToggle(this,mdrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        mdrawerLayout.addDrawerListener(darwertoggle);
        darwertoggle.syncState();
        NavigationView nav_view=findViewById(R.id.nav_viewchuka);
        nav_view.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                Intent i=new Intent(".home");
                startActivity(i);
                break;
            case R.id.nav_arriv_chuka:
                Intent i2=new Intent(".arrivals_chuka");
                startActivity(i2);
                break;
            case R.id.status_chuka:
                Intent i3=new Intent(".status");
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
    private void getjson(){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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

                                    arrivals_session playerModel = new arrivals_session();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    playerModel.setName(dataobj.getString("name"));
                                    playerModel.setEmail(dataobj.getString("email"));



                                    statuscheckArrayList.add(playerModel);

                                }

                                for (int i = 0; i < statuscheckArrayList.size(); i++){
                                    names.add(statuscheckArrayList.get(i).getName());
                                    names.add(statuscheckArrayList.get(i).getEmail());

                                }

  session=names.get(0);





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

