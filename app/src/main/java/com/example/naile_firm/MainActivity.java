package com.example.naile_firm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
Button login;
private ProgressBar loading;
SessionManager sessionManager;
    private ArrayList<arrivals_session> statuscheckArrayList;
public String name,password;
public EditText loginname,loginpassword;
    final String TAG=this.getClass().getSimpleName();
    String url = "http://192.168.43.78/www/html/Naile_progect/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loading=findViewById(R.id.progressBar);
        login=findViewById(R.id.button);
        loginname=findViewById(R.id.loginname);
        sessionManager=new SessionManager(this);
        loginpassword=findViewById(R.id.loginpassword);
        loading.setVisibility(View.GONE);

login();

    }

    public void login(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginname.getText().toString().equalsIgnoreCase("") || loginpassword.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(),"check the credentilas",Toast.LENGTH_SHORT).show();
                }
                else{
                save_raw_mat_dat();
                loading.setVisibility(View.VISIBLE);
            }}
        });
    }

    public void save_raw_mat_dat() {


        login.setVisibility(View.GONE);

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

                                    // arrivals_session playerModel = new arrivals_session();
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    String name1=dataobj.getString("name").trim();
                                    String email1=dataobj.getString("email").trim();
                                    String prevelage1=dataobj.getString("prevelage").trim();
                                    String location1=dataobj.getString("location").trim();

                                    sessionManager.createSession(name1,email1,prevelage1,location1);
                                    Intent intent=new Intent(MainActivity.this,home.class);// playerModel.setName(dataobj.getString("name"));
                                    intent.putExtra("name",name1);
                                    intent.putExtra("email",email1);
                                    intent.putExtra("prevelage",prevelage1);
                                    intent.putExtra("location",location1);
                                    startActivity(intent);
                                    finish();// playerModel.setEmail(dataobj.getString("email"));



                                    //statuscheckArrayList.add(playerModel);

                                }







                            }
                            else {
                                Toast.makeText(getApplicationContext(),"wronng credentials contact admin or try again",Toast.LENGTH_SHORT).show();
                           loading.setVisibility(View.GONE);
                           login.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                        password=loginpassword.getText().toString();
                        name=loginname.getText().toString();

                        params.put("name", name);
                        params.put("email", password);

                        return params;
                    }
                };
                MySingleton.getInstance(MainActivity .this).addToRequestQueue(stringRequest);}

    private void getjson(){

loading.setVisibility(View.VISIBLE);
login.setVisibility(View.GONE);

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

                                    // arrivals_session playerModel = new arrivals_session();
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    String name1=dataobj.getString("name").trim();
                                    String email1=dataobj.getString("email").trim();
                              //      sessionManager.createSession(name1,email1,);
                                    Intent intent=new Intent(MainActivity.this,home.class);// playerModel.setName(dataobj.getString("name"));
                                    intent.putExtra("name",name1);
                                    intent.putExtra("email",email1);
                                    startActivity(intent);
                                    finish();// playerModel.setEmail(dataobj.getString("email"));



                                    //statuscheckArrayList.add(playerModel);

                                }







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
