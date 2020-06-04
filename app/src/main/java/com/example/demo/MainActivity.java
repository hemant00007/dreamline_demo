package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demo.Model.Demomodel;
import com.example.demo.Model.User;
import com.example.demo.Retrofit.Api_client;
import com.example.demo.Retrofit.Api_interface;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText phoneno;
    private Button signin, register;
    private SpinKitView progressbar;
    public static final String TAG = MainActivity.class.getSimpleName();

    ProgressDialog progressDialog;
    private Integer sCode = 0;
    private String not_user="null";
    private String Uid = "", message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneno = findViewById(R.id.emailAddressForLogin);


        signin = findViewById(R.id.signInButton);
        register = findViewById(R.id.signUpButton);
        signin.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.signInButton:
                if (phoneno.getText().length() == 0) {
//                    Toast.makeText(getApplicationContext(),"Phone no can't be empty",Toast.LENGTH_SHORT).show();
                    phoneno.setError("Phone no can't be empty");
                    return;
                } else if (phoneno.getText().length() < 10) {
//                    Toast.makeText(getApplicationContext(), "Phone No must be 10 digit.", Toast.LENGTH_SHORT).show();
                    phoneno.setError("Phone no must be 10 digit.");
                    return;
                } else if (phoneno.getText().length() > 10) {
//                    Toast.makeText(getApplicationContext(),"phone No should only have 10 digit",Toast.LENGTH_SHORT).show();
                    phoneno.setError("Phone no should only 10 digit");
                    return;
                } else {
//
                    if (isNetworkAvailable())
                        getData();

                }
                break;
            case R.id.signUpButton:
                Intent intent3 = new Intent(getApplicationContext(), Register_activity.class);
                startActivity(intent3);
                break;
            default:
                break;

        }

    }

    private void getData() {

        progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading...", true);
        progressDialog.setCancelable(false);


        String url = "http://52.172.155.50/TestAPP/appservice.asmx/loginUser";
        JSONObject jsonBody = null;

        String mRequestBody = "";

        try {
            jsonBody = new JSONObject();
            jsonBody.put("mobileno", phoneno.getText().toString());
            jsonBody.put("apikey", "apischool");

            mRequestBody = jsonBody.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalMRequestBody = mRequestBody;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray obj = new JSONArray(response);
                    Log.e("responce", response);


                    for (int i = 0; i < obj.length(); i++) {
                        JSONObject inData = obj.getJSONObject(i);

                        sCode = Integer.valueOf(inData.getString("StatusCode"));
                        if (sCode==200)
                        {
                            Uid = inData.getString("Usid");
                            Toast.makeText(MainActivity.this,
                                        Uid, Toast.LENGTH_SHORT).show();
                        }
                        JSONObject daa = obj.getJSONObject(i);

                        not_user =daa.getString("d");
                        if (not_user=="null") {
                            Toast.makeText(MainActivity.this, "User Not Registered", Toast.LENGTH_SHORT).show();
                        }
//                        else if (sCode!=200){
//                            Toast.makeText(MainActivity.this,"User Not Registered",Toast.LENGTH_SHORT).show();
//                        }

//                        switch (sCode) {
//                            case 200:
//                                Uid = inData.getString("Usid");
//                                Toast.makeText(MainActivity.this,
//                                        Uid, Toast.LENGTH_SHORT).show();
//                                break;
//                            case 204:
//                                message = inData.getString("Message");
//                                Toast.makeText(MainActivity.this,
//                                        "NOt Registered User", Toast.LENGTH_SHORT).show();
//
//                        }
                    }

                    //we have the array named hero inside the object
                    //so here we are getting that json array
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error", e.getMessage());
                }

                //You will get success response here
                //         Log.e("response", response);
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
                progressDialog.dismiss();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return finalMRequestBody == null ? null : finalMRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", finalMRequestBody, "utf-8");
                    return null;
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }


    private boolean isNetworkAvailable() {
        boolean isConnected = false;
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.isConnected())
                isConnected = true;
            else if (mWifi.isConnected())
                isConnected = true;
            else
                isConnected = false;

        }

        return isConnected;
    }



}





