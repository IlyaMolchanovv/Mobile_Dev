package com.example.list1110.data

import java.util.UUID

data class Group(
    val id:UUID= UUID.randomUUID(),
    var name: String="",
    var facultyID:UUID?=null
)
