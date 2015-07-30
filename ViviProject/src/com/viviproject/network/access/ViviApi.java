package com.viviproject.network.access;

import com.viviproject.entities.UserInformation;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ViviApi {
	@GET("/v1/user/info")
	void getUserInfo(@Query("access-token") String cityName, 
	          Callback<UserInformation> callback);
}
