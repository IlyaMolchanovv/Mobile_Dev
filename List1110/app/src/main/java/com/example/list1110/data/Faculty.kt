package com.example.list1110.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.UUID

@Entity(
    indices = [Index("id")]
)


data class Faculty(
    @SerializedName("id") @PrimaryKey val id: Int=0,
    @SerializedName("name") @ColumnInfo(name = "faculty_name") var name:String =""
)
