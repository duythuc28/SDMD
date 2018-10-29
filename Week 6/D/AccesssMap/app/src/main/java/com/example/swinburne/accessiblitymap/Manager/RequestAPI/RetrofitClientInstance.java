package com.example.swinburne.accessiblitymap.Manager.RequestAPI;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://data.melbourne.vic.gov.au/resource/";
    private static final String APP_TOKEN = "GxusT0nELv09s1GmHnb1osV1d";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.addInterceptor(new MyInterceptor());
            OkHttpClient client = builder.build();
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}


class MyInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        String stringurl = request.url().toString();
        stringurl = stringurl.replace("%26", "&");
        stringurl = stringurl.replace("%24", "$");
        stringurl = stringurl.replace("%3D", "=");
        stringurl = stringurl.replace("%21", "!");
        stringurl = stringurl.replace("%28", "(");
        stringurl = stringurl.replace("%29", ")");
        stringurl = stringurl.replace("%2C", ",");
        stringurl = stringurl.replace("%20", " ");
        stringurl = stringurl.replace("%27", "'");

        Request newRequest = new Request.Builder()
                .url(stringurl)
                .build();

        Log.d("Request", stringurl);

        return chain.proceed(newRequest);
    }
}


