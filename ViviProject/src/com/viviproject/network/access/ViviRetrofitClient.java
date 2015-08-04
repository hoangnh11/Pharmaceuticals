package com.viviproject.network.access;

import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ViviRetrofitClient {

	private static String ROOT = HttpFunctionFactory.viviHostURLshort;
	private static RestAdapter restAdapter;
	public final static ViviRetrofitClient Instance = new ViviRetrofitClient();
	
	public static <T> void doRequest(final Class<T> clazz,
			final String category, final Context context,
			final Map<String, String> dicMap, final Callback<T> callback) {
		final Class<T> _clazz = clazz;
		
		Gson gson = new GsonBuilder().create();

        if(null == restAdapter ){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ROOT)
                    .build();
        }
        
        final Callback myCallback = new Callback<T>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void success(T arg0, Response arg1) {
				// TODO Auto-generated method stub
				
			}
        	
        };
	}
	
}
