package com.example.list1110.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date
import java.util.UUID

@Entity(tableName = "students",
    indices = [Index("id"), Index("group_id", "id")],
    foreignKeys = [
        ForeignKey(
            entity = Group::class,
            parentColumns = ["id"],
            childColumns = ["group_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
   )
data class Student(
    @SerializedName("id") @PrimaryKey val id : Int=0,
    @SerializedName("lastName") var lastName: String="",
    @SerializedName("firstName") var firstName: String="",
    @SerializedName("middleName") var middleName: String="",
    @SerializedName("birthDate") @ColumnInfo(name = "birth_date")var birthDate : Date = Date(),
    @SerializedName("groupID") @ColumnInfo(name = "group_id")var groupID : Int=0,
    @SerializedName("phone") var phone: String="",
    @SerializedName("sex") var sex: Int=0
){
    val shortName
        get()=lastName+
                (if (firstName.length>0) {" ${firstName.subSequence(1,2)}. "} else "") +
                (if (middleName.length>0) {" ${middleName.subSequence(1,2)}. "} else "")
}
