package com.app.qfonapppracticalapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.qfonapppracticalapp.model.AirwaysModelClass
import com.app.qfonapppracticalapp.ui.repository.AirWaysListRepository
import retrofit2.Response

class AirWaysListViewModel:ViewModel() {
    private val airWaysListRepository: AirWaysListRepository = AirWaysListRepository()
    fun airWaysList(page : Int,size : Int): MutableLiveData<Response<AirwaysModelClass?>> {
       return airWaysListRepository.airWaysList(page,size)
    }
}