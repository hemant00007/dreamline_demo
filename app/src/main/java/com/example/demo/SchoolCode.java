package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SchoolCode extends AppCompatActivity implements View.OnClickListener {

   private Toolbar toolbar;
   private Button submit;
   private EditText edt_school_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_code);

  init();
    }

    private void init() {
        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("School Name");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        edt_school_code = findViewById(R.id.edit_school_code);
        submit= findViewById(R.id.submit_button);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.submit_button:
               if (edt_school_code.getText().length()==0)
               {
                   edt_school_code.setError("School code can't be empty");
                   return;
               }else  {
                   Intent intent3 = new Intent(getApplicationContext(), Dashboard.class);
                   startActivity(intent3);
                   finish();
               }
        }

    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }


}
