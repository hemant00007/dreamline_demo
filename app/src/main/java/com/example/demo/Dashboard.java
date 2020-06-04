package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.demo.Model.Data;
import com.example.demo.adapter.Recyclerviewadapter;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewFlipper viewFlipper;
    private RecyclerView recyclerView;

    private final String android_version_names[] = {
            "Online Live Class",
            "Study Material",
            "Video Lession",
            "Notification",

    };

    private final int android_image_urls[] = {
           R.drawable.banner1,R.drawable.banner2,R.drawable.banner3,R.drawable.banner4
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
    }

    private void init() {
        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        viewFlipper = findViewById(R.id.viewflipper);

        recyclerView = findViewById(R.id.recycler_dashboard);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        ArrayList<Data> androidVersions = prepareData();
        Recyclerviewadapter adapter = new Recyclerviewadapter(getApplicationContext(),androidVersions);
        recyclerView.setAdapter(adapter);






        int image[] ={R.drawable.banner1,R.drawable.banner2,R.drawable.banner3,R.drawable.banner4};



        for (int i=0;i<image.length;i++){
            flipper(image[i]);
        }
    }

    private ArrayList<Data> prepareData(){

        ArrayList<Data> android_version = new ArrayList<>();
        for(int i=0;i<android_version_names.length;i++){
            Data androidVersion = new Data();
            androidVersion.setName(android_version_names[i]);
            androidVersion.setAndroid_image_url(android_image_urls[i]);
            android_version.add(androidVersion);
        }
        return android_version;
    }

    public void flipper(int Image){
        ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(Image);
            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.setAutoStart(true);

    }
}
