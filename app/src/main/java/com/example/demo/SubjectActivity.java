package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.demo.Model.SubjectModel;
import com.example.demo.Retrofit.Api_client;
import com.example.demo.Retrofit.Api_interface;
import com.example.demo.adapter.SubjectAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private String apikey ="apischool";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        recyclerView = findViewById(R.id.subject_name);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        call_subject();
    }

    private void call_subject() {

        Api_interface api_interface = Api_client.getClient().create(Api_interface.class);
        Call<List<SubjectModel>> call = api_interface.subject_list(apikey);
        call.enqueue(new Callback<List<SubjectModel>>() {
            @Override
            public void onResponse(Call<List<SubjectModel>> call, Response<List<SubjectModel>> response) {
                List<SubjectModel> result =response.body();
                recyclerView.setAdapter(new SubjectAdapter(result,getApplicationContext()));

            }

            @Override
            public void onFailure(Call<List<SubjectModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }
}