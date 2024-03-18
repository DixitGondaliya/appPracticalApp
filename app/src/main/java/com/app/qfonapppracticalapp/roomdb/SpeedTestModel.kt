package com.app.qfonapppracticalapp.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "speed_test_database")
data class SpeedTestModel(
    @PrimaryKey(autoGenerate = true) var id: Int,
    val networkName: String,
    val downLoadSpeed: String,
    val upLoadSpeed: String,
    val ping: String,
    val downLoadSpeedList: MutableList<Float> = mutableListOf<Float>(),
    val upLoadSpeedList: MutableList<Float> = mutableListOf<Float>(),
    val signalStrength: String,
    val ipAddress : String,
    val frequency : Int,
    val gateway : String,
    val serverHostCity : String,
    val cityName : String,
    val country : String,
    val date: String,
    val time: String
)
