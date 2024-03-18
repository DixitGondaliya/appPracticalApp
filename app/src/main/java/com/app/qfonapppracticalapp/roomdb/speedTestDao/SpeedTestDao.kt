package com.app.qfonapppracticalapp.roomdb.speedTestDao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.qfonapppracticalapp.roomdb.SpeedTestModel

@Dao
interface SpeedTestDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSpeedTest(speedTestModel: SpeedTestModel)

    @Delete
    suspend fun deleteSpeedTest(speedTestModel: SpeedTestModel)

    @Query("SELECT * FROM speed_test_database ORDER BY id DESC")
    fun getAllSpeedTestsLiveData(): LiveData<List<SpeedTestModel>>

    @Query("DELETE FROM speed_test_database WHERE id = :id")
    suspend fun deleteSpeedTestById(id: Int)

    @Delete
    suspend fun deleteSpeedTests(speedTestModels: List<SpeedTestModel>)

}