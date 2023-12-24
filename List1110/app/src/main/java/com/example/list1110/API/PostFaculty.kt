package com.example.list1110.API

import com.example.list1110.data.Faculty
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class PostFaculty (
    @SerializedName("action") val action:Int,
    @SerializedName ("faculty") val faculty: Faculty
)