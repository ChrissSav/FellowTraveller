package com.example.fellowtraveller;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonApi {

    @GET("getusers")
    Call<List<User>> getUsers();

    @GET("getuserauth/{email}/{password}")
    Call<User> getUserAuth(
            @Path("email") String email,
            @Path("password") String password
    );

    @GET("registeruser/{name}/{birthday}/{email}/{password}/{phone}")
    Call<Status_handling> createUser(
            @Path("name") String name,
            @Path("birthday") String birthday,
            @Path("email") String email,
            @Path("password") String password,
            @Path("phone") String phone
    );

    @GET("trips/{from}/{to}/{date}/{time}/{creator_id}/{description}/{max_seats}/{max_bags}")
    Call<Status_handling> createTrip(
            @Path("from") String from,
            @Path("to") String to,
            @Path("date") String date,
            @Path("time") String time,
            @Path("creator_id") int creator_id,
            @Path("description") String description,
            @Path("max_seats") int max_seats,
            @Path("max_bags") int max_bags

    );


    @GET("gettripbyfilter/{from}/{to}")
    Call<List<Trip>> createTripByFilter(
            @Path("from") String from,
            @Path("to") String to
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

    @GET("gettripstakespart/{id}")
    Call<List<Trip>> getTripsTakesPart(
            @Path("id") int id
    );


    @GET("getnotification/{id}")
    Call<List<Notification>> getNotigicationOfUser(
            @Path("id") int id
    );

}
