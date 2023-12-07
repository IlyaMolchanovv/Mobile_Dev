package com.example.list1110

import android.app.Application
import android.content.Context
import com.example.list1110.repository.AppRepository

class ApplicationList1110:Application() {
    override fun onCreate() {
        super.onCreate()
        AppRepository.getInstance().loadData()
    }
    init {
        instance=this
    }
    companion object {
        private var instance: ApplicationList1110?=null
        val context
            get()=applicationContext()
        private fun applicationContext(): Context {
            return instance !!.applicationContext
        }
    }

}