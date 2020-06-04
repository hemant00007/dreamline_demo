package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Login extends AppCompatActivity {



    ProgressDialog progressDialog;
    private Integer sCode = 0;
    private String Uid = "", message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (isNetworkAvailable())
            getData();

    }


    private void getData() {

        progressDialog = ProgressDialog.show(Login.this, "", "Loading...", true);
        progressDialog.setCancelable(false);


        String url = "http://52.172.155.50/TestAPP/appservice.asmx/loginUser";
        JSONObject jsonBody = null;

        String mRequestBody = "";

        try {
            jsonBody = new JSONObject();
            jsonBody.put("mobileno", "8983627381");
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
                        switch (sCode) {
                            case 200:
                                Uid = inData.getString("Usid");
                                Toast.makeText(Login.this,
                                        Uid, Toast.LENGTH_SHORT).show();
                                break;
                            case 204:
                                message = inData.getString("Message");
                                Toast.makeText(Login.this,
                                        message, Toast.LENGTH_SHORT).show();

                        }
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

        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
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