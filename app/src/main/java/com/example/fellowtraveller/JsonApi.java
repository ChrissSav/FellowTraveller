package com.example.fellowtraveller;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonApi {

    @GET("getusers")
    Call<List<User>> getUsers();

    @GET("getuser/{email}")
    Call<List<User>> getUserById(
            @Path("email") String email
    );

    @POST("adduser/{name}/{email}/{password}")
    Call<List<User>> createUser(
            @Path("name") String name,
            @Path("email") String email,
            @Path("password") String password
    );


}
