package com.example.mykotlin.app

import android.app.Application
import com.example.mykotlin.utlis.MyMmkv

class MyApp: Application() {
    companion object{
        var instance:MyApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance =this
        MyMmkv.initMMKV()
    }
}