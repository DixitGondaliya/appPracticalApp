package com.app.qfonapppracticalapp.roomdb.speedTestRepository

import androidx.lifecycle.LiveData
import com.app.qfonapppracticalapp.roomdb.SpeedTestModel
import com.app.qfonapppracticalapp.roomdb.speedTestDao.SpeedTestDao

class SpeedTestRepository(private val speedTestDao : SpeedTestDao) {

    val allSpeedTests: LiveData<List<SpeedTestModel>> = speedTestDao.getAllSpeedTestsLiveData()

    suspend fun insertSpeedTest(speedTestModel: SpeedTestModel){
        speedTestDao.insertSpeedTest(speedTestModel)
    }

    suspend fun deleteSelectedSpeedTests(speedTestModels: List<SpeedTestModel>) {
        speedTestDao.deleteSpeedTests(speedTestModels)
    }
}