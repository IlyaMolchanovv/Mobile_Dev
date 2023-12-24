package com.example.list1110.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.UUID

@Entity(tableName = "groups",
    indices = [Index("id"), Index("faculty_id")],
    foreignKeys = [
        ForeignKey(
            entity = Faculty::class,
            parentColumns = ["id"],
            childColumns = ["faculty_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Group(
    @SerializedName("id") @PrimaryKey val id: Int=0,
    @SerializedName("name") @ColumnInfo(name = "group_name")var name: String="",
    @SerializedName("facultyID") @ColumnInfo(name = "faculty_id")var facultyID: Int=0
)
