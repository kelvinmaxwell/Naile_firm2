package com.example.naile_firm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class arrivals_chuka extends AppCompatActivity {
    final String TAG=this.getClass().getSimpleName();
    ListView lvProduct;
    String url = "http://192.168.43.78/www/html/Naile_progect/get_good_content.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrivals_chuka);
        save_raw_mat_data();
        save_raw_mat_data();

    }


    public void save_raw_mat_data() {



                Intent i=new Intent(".rawmmixing");
                startActivity(i);

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

                        params.put("name", "max");

                        return params;
                    }
                };
                MySingleton.getInstance(arrivals_chuka .this).addToRequestQueue(stringRequest);}




    }


