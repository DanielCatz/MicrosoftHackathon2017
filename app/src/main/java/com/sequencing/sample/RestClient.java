package com.sequencing.sample;

import android.support.v4.util.LruCache;
import android.util.Log;

import com.sequencing.sample.models.AccessToken;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kylie on 4/6/2017.
 */

public class RestClient {
    private static final String YELP = "https://api.yelp.com/";
    private static RestClient _instance;
    private final YelpAPI YELP_API;
    private LruCache<Class<?>, Observable<?>> apiObservables;

    public static RestClient getInstance() {
        if (_instance == null){
            synchronized (RestClient.class){
                if(_instance==null) {
                    _instance = new RestClient();
                }
            }
        }
        return _instance;
    }

    private RestClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        //Uncomment these lines below to start logging each request.

//
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        httpClient.addInterceptor(logging);

        apiObservables = new LruCache<>(10);
        Retrofit retrofit = new Retrofit.Builder()                
                .baseUrl(YELP)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();


        YELP_API = retrofit.create(YelpAPI.class);
    }

    public YelpAPI getYELP_API() {
        return YELP_API;
    }

    public void requestYelpToken(String credentials, String id, String secret) {

        YELP_API.requestYelpToken(credentials, id, secret).enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                Log.e("THINGS",response.body().getAccessToken());
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {

            }
        });

    }

    public Observable<?> getPreparedObservable(Observable<?> rawObservable, Class<?> theClass, boolean cacheObservable, boolean useCache){
        Observable<?> preparedObservable = null;

        if(useCache)//this way we don't reset anything in the cache if this is the only instance of us not wanting to use it.
            preparedObservable = apiObservables.get(theClass);

        if(preparedObservable!=null)
            return preparedObservable;



        //we are here because we have never created this observable before or we didn't want to use the cache...

        preparedObservable = rawObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        if(cacheObservable){
            preparedObservable = preparedObservable.cache();
            apiObservables.put(theClass, preparedObservable);
        }


        return preparedObservable;


    }





}
