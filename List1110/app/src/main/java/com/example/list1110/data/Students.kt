package com.example.list1110.data

import com.google.gson.annotations.SerializedName

class Students {
    @SerializedName("items") lateinit var items: List<Student>
}