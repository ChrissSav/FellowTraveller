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

    @GET("gettrip/{id}")
    Call<exampleTrip> getTripById(
            @Path("id") String id
    );

    @GET("gettrip/{id}")
    Call<List<exampleTrip>> getTrip(
            @Path("id") String id
    );
    @GET("gettrips")
    Call<List<exampleTrip>> getTrips();

}
