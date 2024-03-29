package com.book.book_a.application

import android.app.Application
import android.os.Build
import android.os.StrictMode
import cn.jpush.android.api.JPushInterface


/**
 * Created by Jin on 10/15/2018
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Instance = this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
        }
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)

    }

    companion object {
        @JvmStatic
        lateinit var Instance: App

        init {
            //启用矢量图兼容
        }
    }

}