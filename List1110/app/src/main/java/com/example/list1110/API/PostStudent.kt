package com.example.list1110.API

import com.example.list1110.data.Group
import com.example.list1110.data.Student
import com.google.gson.annotations.SerializedName

class PostStudent (
    @SerializedName("action") val action:Int,
    @SerializedName("student") val student: Student
)