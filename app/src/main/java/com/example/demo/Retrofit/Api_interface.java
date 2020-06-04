package com.example.demo.Retrofit;

import com.example.demo.Model.Demomodel;
import com.example.demo.Model.SubjectModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface
Api_interface {

   // op=LoginUser

    @Headers("Content-Type: application/json")
    @POST("loginUser")
    Call<List<Demomodel>> login_with_mobile(
            @Query("mobileno")String mobileno,
            @Query("apikey") String apikey);



    @Headers("Content-Type: application/json")
    @POST("SubjectMaster")
    Call<List<SubjectModel>> subject_list(
            @Query("apikey") String apikey
    );

}
