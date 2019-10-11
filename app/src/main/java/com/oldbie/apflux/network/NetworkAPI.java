package com.oldbie.apflux.network;

import com.oldbie.apflux.model.ModelUser;
import com.oldbie.apflux.model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkAPI {

    @GET("api_check_user.php")
    Call<ServerResponse> checkLogin(@Query("user") String user,
                                    @Query("pass") String pass);

    @GET("api_get_user.php")
    Call<ModelUser> getUser(@Query("user") String user);
}