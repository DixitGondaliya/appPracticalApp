package com.app.qfonapppracticalapp.net

import com.app.qfonapppracticalapp.model.AirwaysModelClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("qfonapp-passenger")
    fun getAirWaysList(@Query("page") page: Int,
                       @Query("size") size: Int): Call<AirwaysModelClass?>?
}