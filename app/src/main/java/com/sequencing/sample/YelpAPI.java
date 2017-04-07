package com.sequencing.sample;

import com.sequencing.sample.models.AccessToken;
import com.sequencing.sample.models.Businesses;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Kylie on 4/6/2017.
 */

public interface YelpAPI {
    @FormUrlEncoded
    @POST("oauth2/token")
    Call<AccessToken> requestYelpToken(@Field("grant_type") String credentials,
                                       @Field("client_id") String id,
                                       @Field("client_secret") String secret);


    @FormUrlEncoded
    @POST("oauth2/token")
    Observable<AccessToken> getYelpToken(@Field("grant_type") String credentials,
                                             @Field("client_id") String id,
                                             @Field("client_secret") String secret);

    @GET("https://api.yelp.com/v3/businesses/search")
    Observable<Businesses> getBusinessesWithSearch(@Query("term") final String searchTerm,
                                                @Query("latitude") final String latitude,
                                                @Query("longitude") final String longitude,
                                                @Header("Authorization") String authHeader);




}
