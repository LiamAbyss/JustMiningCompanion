package com.pauk.justminingcompanion

import android.app.Application
import android.content.Context

class JustMiningApplication : Application(){

    init {
        instance = this
    }

    companion object {
        private var instance: JustMiningApplication? = null

        fun getContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any

        // Use ApplicationContext.
        // example: SharedPreferences etc...
        val context: Context = JustMiningApplication.getContext()
    }
}