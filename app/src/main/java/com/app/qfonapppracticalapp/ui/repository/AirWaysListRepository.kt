package com.app.qfonapppracticalapp.ui.repository

import androidx.lifecycle.MutableLiveData
import com.app.qfonapppracticalapp.model.AirwaysModelClass
import com.app.qfonapppracticalapp.net.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AirWaysListRepository {


    fun airWaysList(page : Int,size : Int): MutableLiveData<Response<AirwaysModelClass?>> {
        val res: MutableLiveData<Response<AirwaysModelClass?>> = MutableLiveData()
        RetrofitClient.getApiInterfaceWithoutAuth()?.getAirWaysList(page,size)
            ?.enqueue(object : Callback<AirwaysModelClass?> {
                override fun onResponse(call: Call<AirwaysModelClass?>, response: Response<AirwaysModelClass?>) {
                    res.value = response
                }

                override fun onFailure(call: Call<AirwaysModelClass?>, t: Throwable) {
                    res.value = null
                }
            })
        return res
    }
}