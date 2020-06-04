package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Register_activity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText mobileno_edt;
    private Button register_btn, login_btn;

    ProgressDialog progressDialog;
    private Integer sCode = 0;
    private String not_user = "null";
    private String Uid = "", message = "";
    private String msg = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        init();


    }

    private void init() {
        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        mobileno_edt = findViewById(R.id.register_mobile_no);
        register_btn = findViewById(R.id.regButton);
        login_btn = findViewById(R.id.login_button);

        register_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.login_button:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.regButton:
                if (mobileno_edt.getText().length() == 0) {
//                    Toast.makeText(getApplicationContext(),"Phone no can't be empty",Toast.LENGTH_SHORT).show();
                    mobileno_edt.setError("Phone no can't be empty");
                    return;
                } else if (mobileno_edt.getText().length() < 10) {
//                    Toast.makeText(getApplicationContext(), "Phone No must be 10 digit.", Toast.LENGTH_SHORT).show();
                    mobileno_edt.setError("Phone no must be 10 digit.");
                    return;
                } else if (mobileno_edt.getText().length() > 10) {
//                    Toast.makeText(getApplicationContext(),"phone No should only have 10 digit",Toast.LENGTH_SHORT).show();
                    mobileno_edt.setError("Phone no should only 10 digit");
                    return;
                } else {
//

                    register_user();

                }
                break;
        }

    }

    private void register_user() {

        progressDialog = ProgressDialog.show(Register_activity.this, "", "Loading...", true);
        progressDialog.setCancelable(false);


        String url = "http://52.172.155.50/TestAPP/appservice.asmx/SignupUser";
        JSONObject jsonBody = null;
        String mRequestBody = "";

        try {
            jsonBody = new JSONObject();
            jsonBody.put("mobileno", mobileno_edt.getText().toString());
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

                        if (sCode == 200) {
                            msg = inData.getString("Message");
                            // Uid = inData.getString("Usid");
                            Toast.makeText(Register_activity.this,
                                    msg, Toast.LENGTH_SHORT).show();
                        }
                        JSONObject daa = obj.getJSONObject(i);

                        //      not_user =daa.getString("d");
                        //    if (not_user=="null") {
                        //      Toast.makeText(Register_activity.this, "User Not Registered", Toast.LENGTH_SHORT).show();
                        //}
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

        RequestQueue requestQueue = Volley.newRequestQueue(Register_activity.this);
        requestQueue.add(stringRequest);

    }


}
