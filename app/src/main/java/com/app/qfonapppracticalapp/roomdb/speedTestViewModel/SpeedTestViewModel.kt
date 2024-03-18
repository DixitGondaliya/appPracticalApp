package com.app.qfonapppracticalapp.roomdb.speedTestViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.app.qfonapppracticalapp.roomdb.SpeedTestModel
import com.app.speedtestmaster.roomDataBase.speedTestDataBase.SpeedTestDatabase
import com.app.qfonapppracticalapp.roomdb.speedTestRepository.SpeedTestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SpeedTestViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : SpeedTestRepository

    init {
        val speedTestDao = SpeedTestDatabase.getDatabase(application).speedTestDao()
        repository = SpeedTestRepository(speedTestDao)
    }

    fun insertSpeedTest(speedTestModel: SpeedTestModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertSpeedTest(speedTestModel)
        }
    }

    fun getAllSpeedTests(): LiveData<List<SpeedTestModel>> {
        return repository.allSpeedTests
    }

    fun deleteSelectedSpeedTests(speedTestModels: List<SpeedTestModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSelectedSpeedTests(speedTestModels)
        }
    }

}