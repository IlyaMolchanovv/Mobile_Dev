package com.example.list1110

import android.app.Application
import android.content.Context
import com.example.list1110.repository.AppRepository

class ApplicationList : Application() {

    override fun onCreate() {
        super.onCreate()
        AppRepository.getInstance()
    }

    init {
        instance = this
    }

    companion object{
        private var instance: ApplicationList? = null

        val context
            get() = applicationContext()

        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}