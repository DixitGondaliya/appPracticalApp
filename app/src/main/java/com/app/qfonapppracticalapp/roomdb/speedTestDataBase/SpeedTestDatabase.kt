package com.app.speedtestmaster.roomDataBase.speedTestDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.qfonapppracticalapp.roomdb.SpeedTestModel
import com.app.qfonapppracticalapp.roomdb.speedTestDao.SpeedTestDao

@Database(
    entities = [SpeedTestModel::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class SpeedTestDatabase:RoomDatabase() {
    abstract fun speedTestDao() : SpeedTestDao

    companion object{
        @Volatile
        private var INSTANCE : SpeedTestDatabase? = null

        fun getDatabase(context: Context):SpeedTestDatabase{
            val tempInstance = INSTANCE

            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SpeedTestDatabase::class.java,
                    "speed_test_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}