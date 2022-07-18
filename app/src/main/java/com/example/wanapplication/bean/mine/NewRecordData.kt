package com.example.wanapplication.bean.mine

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class NewRecordData(
    val title : String,
    val date : String,
    val url : String
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}