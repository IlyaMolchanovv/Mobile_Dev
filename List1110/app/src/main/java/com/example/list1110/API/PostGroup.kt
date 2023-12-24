package com.example.list1110.API

import com.example.list1110.data.Group
import com.google.gson.annotations.SerializedName

class PostGroup (
    @SerializedName("action") val action:Int,
    @SerializedName("group") val group: Group
)