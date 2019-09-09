package com.example.naile_firm;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class rawm_entry extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    final Calendar myCalendar = Calendar.getInstance();
    Spinner rawmatspinner;
    ArrayAdapter<CharSequence>adapter;
    public String type,quantity,time,id,name,date;
   public EditText typetxt,quantitytxt,timetxt,datetxt,idtxt;
    public FloatingActionButton myFab;
    final String TAG=this.getClass().getSimpleName();
    String url = "http://192.168.43.78/www/html/Naile_progect/insert_raw_mat.php";
    DrawerLayout mdrawerLayout;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rawm_entry);
        rawmatspinner=findViewById(R.id.spinner);
        typetxt=findViewById(R.id.typetxt);
        quantitytxt=findViewById(R.id.quantitytxt);
        datetxt=findViewById(R.id.datetxt);
        timetxt=findViewById(R.id.timetxt);
        idtxt=findViewById(R.id.idtxt);
        mdrawerLayout=findViewById(R.id.drawerlayout);
        toolbar=findViewById(R.id.toolBar2);

drawable2();
        spinner();
        datepicker();
        timepicker();

        save_raw_mat_data();
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
                mTimePicker = new TimePickerDialog(rawm_entry.this, new TimePickerDialog.OnTimeSetListener() {
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
                new DatePickerDialog(rawm_entry.this, date, myCalendar
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

       myFab = findViewById(R.id.floatbtn);
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
                   Toast.makeText(getApplicationContext(), "Error while reading googl", Toast.LENGTH_SHORT).show();

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
                   MySingleton.getInstance(rawm_entry .this).addToRequestQueue(stringRequest);}

       });



   }
    public void drawable2(){

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


            case R.id.status_chuka:
                Intent i7=new Intent(".status");
                startActivity(i7);
                break;
            case R.id.status_B:
                Intent i11=new Intent(".status_b");
                startActivity(i11);
                break;
            case R.id.status_C:
                Intent i12=new Intent(".status_c");
                startActivity(i12);

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
       }

