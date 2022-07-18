package com.example.wanapplication.mine

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.wanapplication.bean.mine.NewRecordData

@Dao
interface ReadRecordDao {
    @Query("select * from records")
    fun getAll():List<NewRecordData>

    @Insert
    fun insert(vararg records : NewRecordData)

    @Delete
    fun delete(records : NewRecordData)

    @Query("delete from records")
    fun deleteAll()
}