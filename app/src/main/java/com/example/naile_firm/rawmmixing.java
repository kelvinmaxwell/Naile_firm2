package com.example.naile_firm;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class rawmmixing extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ConstraintLayout rootlayout;
    FloatingActionButton fbtnmix;

    String url = "http://192.168.43.78/www/html/Naile_progect/mix.php";
    final String TAG=this.getClass().getSimpleName();

    public EditText quantitymix1,quantitymix2,quantitymix3,editmix1,editmix2,editmix3,timet,date;
    ArrayAdapter<CharSequence>adapter;
    public String timemix,datemix,namemix1,namemix2,namemix3,quantity1,quantity2,quantity3,desiredString1,desiredString2,typeexpected,desiredString3,mixid;
    DrawerLayout mdrawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rawmmixing);
        editmix1=findViewById(R.id.editmix1);
        editmix2=findViewById(R.id.editmix2);
        editmix3=findViewById(R.id.editmix3);
        timet=findViewById(R.id.timetxtmix);



        rootlayout=findViewById(R.id.rootlayout);

        quantitymix1=findViewById(R.id.rawmix1);
        quantitymix2=findViewById(R.id.rawmix2);
        quantitymix3=findViewById(R.id.rawmix3);
        mdrawerLayout=findViewById(R.id.drawerlayout2);
        toolbar=findViewById(R.id.toolBar2);


timepicker();

        drawable2();

        floatbtn();


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

      final  EditText userInput = (EditText) promptsView.findViewById(R.id.etUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                        // edit text
                    String goodname=userInput.getText().toString();
                        Toast.makeText(getApplicationContext(), "Entered: "+goodname, Toast.LENGTH_LONG).show();
                        typeexpected=goodname;
                        save_raw_mat_data();

                        Intent i=new Intent(".get_products_contents");
                        startActivity(i);

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
    CharSequence foo1 = editmix1.getText();
    String sub1 = foo1.toString();
 desiredString1 = sub1.substring(0,3);

    CharSequence foo2 = editmix1.getText();
    String sub2 = foo2.toString();
    desiredString2 = sub2.substring(0,3);

    CharSequence foo3 = editmix1.getText();
    String sub3 = foo3.toString();
    desiredString3= sub3.substring(0,3);

    mixid=desiredString1+desiredString2+desiredString3;


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


            namemix1=editmix1.getText().toString();
            namemix2=editmix2.getText().toString();
            namemix3=editmix3.getText().toString();
            timemix=timet.getText().toString();


            quantity1= quantitymix1.getText().toString();
            quantity2 = quantitymix2.getText().toString();
            quantity3 = quantitymix3.getText().toString();


            params.put("namemix1", namemix1);
            params.put("namemix2", namemix2);
            params.put("namemix3", namemix3);
            params.put("quantity1", quantity1);
            params.put("quantity2", quantity2);
            params.put("quantity3", quantity3);
            params.put("typeexpected", typeexpected);
            params.put("time", timemix);

            params.put("mixid", mixid);

            return params;
        }
    };
    MySingleton.getInstance(rawmmixing.this).addToRequestQueue(stringRequest);

    Snackbar.make(rootlayout,"data saved succesfully",Snackbar.LENGTH_SHORT).show();
}



    public void floatbtn() {

        fbtnmix = findViewById(R.id.floatbtnmix);
        fbtnmix.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                idgeneration();
                popup();
                Toast.makeText(getApplicationContext(), "SAVED", Toast.LENGTH_SHORT).show();




            }

        });}
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


            case R.id.nav_arriv_chuka:
                Intent i6=new Intent(".arrivals_chuka");
                startActivity(i6);
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
