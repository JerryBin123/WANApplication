package com.example.wanapplication.mine

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wanapplication.bean.mine.NewRecordData

@Database(entities = [NewRecordData::class], version = 1, exportSchema = false)
abstract class ReadRecordDatabase : RoomDatabase() {
    abstract fun getReadRecordDao() : ReadRecordDao

    companion object{
        private var instance : ReadRecordDatabase? = null
        @Synchronized
        fun getInstance(context : Context) : ReadRecordDatabase{
            return instance?.let { it }
                ?: Room.databaseBuilder(context.applicationContext, ReadRecordDatabase::class.java
                    ,"read_recordc.db")
                    .allowMainThreadQueries()
                    .build()
                    .apply { instance = this }
        }
    }
}