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

    @GET("trips/{from}/{to}/{date}/{time}/{creator_id}/{description}/{max_seats}/{max_bags}")
    Call<List<Status_handling>> createTrip(
            @Path("from") String from,
            @Path("to") String to,
            @Path("date") String date,
            @Path("time") String time,
            @Path("creator_id") int creator_id,
            @Path("description") String description,
            @Path("max_seats") int max_seats,
            @Path("max_bags") int max_bags

    );

    @GET("gettrip/{id}")
    Call<Trip> getTripById(
            @Path("id") String id
    );

    @GET("gettrip/{id}")
    Call<List<Trip>> getTrip(
            @Path("id") String id
    );
    @GET("gettrips")
    Call<List<Trip>> getTrips();

}
