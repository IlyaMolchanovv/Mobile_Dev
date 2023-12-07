package com.example.list1110.interfaces

import com.example.list1110.NameOfFragment
import com.example.list1110.data.Student

interface MainActivityInterface {
    fun updateTitle(newTitle:String)
    fun showFragment(fragmentType: NameOfFragment, student: Student? = null)
}