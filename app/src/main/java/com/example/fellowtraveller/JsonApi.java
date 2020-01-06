package com.example.fellowtraveller;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonApi {


    @GET("getuserauth/{email}/{password}")
    Call<UserAuth> getUserAuth(
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

    @GET("trips/{from}/{to}/{date}/{time}/{creator_id}/{description}/{max_seats}/{max_bags}/{price}")
    Call<Status_handling> createTrip(
            @Path("from") String from,
            @Path("to") String to,
            @Path("date") String date,
            @Path("time") String time,
            @Path("creator_id") int creator_id,
            @Path("description") String description,
            @Path("max_seats") int max_seats,
            @Path("max_bags") int max_bags,
            @Path("price") int price

    );


    @GET("gettripstakespart/{id}")
    Call<List<TripB>> getTripsTakesPart(
            @Path("id") int id
    );


    @GET("getnotification/{id}")
    Call<List<Notification>> getNotificationOfUser(
            @Path("id") int id
    );


    @GET("getnotificationcount/{id}")
    Call<Status_handling> getNotificationOfUserCount(
            @Path("id") int id
    );


    @GET("registerrequesttotrip/{user_id}/{bag}/{target_id}/{trip_id}")
    Call<Status_handling> sendRequest(
            @Path("user_id") int user_id,
            @Path("bag") String bag,
            @Path("target_id") int target_id,
            @Path("trip_id") int trip_id


    );

    @GET ("getUserTrips/{id}")
    Call<List<TripB>> getTripsCreated(
            @Path("id") int id
    );

    @GET("changerequeststatus/{user_id}/{bag}/{trip_id}/{status}")
    Call<Status_handling> ChangeRequestStatus(
            @Path("user_id") int user_id,
            @Path("bag") String bag,
            @Path("trip_id") int trip_id,
            @Path("status") String status
    );

    @GET("changestatusnotification/{id}")
    Call<Status_handling> ChangeNotificationStatus(
            @Path("id") int id
    );

    @GET("registerNotification/{user_id}/{target_id}/{trip_id}/{type}")
    Call<Status_handling> RegisterNotification(
            @Path("user_id") int user_id,
            @Path("target_id") int target_id,
            @Path("trip_id") int trip_id,
            @Path("type") String type
    );

    @GET("getTripsFilter/{from}/{to}/{date_from}/{date_to}/{time_from}/{time_to}/{seats_from}/{seats_to}/{bags_from}/{bags_to}/" +
            "{rate_from}/{rate_to}/{price_from}/{price_to}/{id}")
    Call<List<TripB>> getTripsfilter(
            @Path("from") String from,
            @Path("to") String to,
            @Path("date_from") String date_from,
            @Path("date_to") String date_to,
            @Path("time_from") String time_from,
            @Path("time_to") String time_to,
            @Path("seats_from") int seats_from,
            @Path("seats_to") int seats_to,
            @Path("bags_from") int bags_from,
            @Path("bags_to") int bags_to,
            @Path("rate_from") Double rate_from,
            @Path("rate_to") Double rate_to,
            @Path("price_from") int price_from,
            @Path("price_to") int price_to,
            @Path("id") int id
    );

    @GET("registerRate/{user_id}/{target_id}/{friendlyScore}/{reliableScore}/{carefulScore}/{consistentScore}/{description}")
    Call<Status_handling> RegisterRate(
            @Path("user_id") int user_id,
            @Path("target_id") int target_id,
            @Path("friendlyScore") int friendlyScore,
            @Path("reliableScore") int reliableScore,
            @Path("carefulScore") int carefulScore,
            @Path("consistentScore") int consistentScore,
            @Path("description") String description
    );

    @GET("GetUsersRateAnother/{user_id}")
    Call<List<ReviewItem>> getUsersRateAnother(
            @Path("user_id") int user_id

    );

    @GET("getUserInfo/{user_id}")
    Call<RateUserContainerItem> getUserInfo(
            @Path("user_id") int user_id
    );

    @POST("uploadimage/")
    Call<Status_handling> uploadImage(@Body JsonObject image);


    @GET("CheckUserInfo/{id}")
    Call<UserAuth> CheckUserInfo(
        @Path("id") int id
    );
}

