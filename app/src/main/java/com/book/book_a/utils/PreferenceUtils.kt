package com.book.book_a.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.book.book_a.application.App
import kotlin.reflect.KProperty


class PreferenceUtils<T>(private val key: String, val context: Context, private val default: T) {

    private val prefs: SharedPreferences by lazy { App.Instance.applicationContext.getSharedPreferences("wocai_sp", Context.MODE_PRIVATE) }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        Log.i("info", "调用$this 的getValue()")
        return getSharePreferences(key, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        Log.i("info", "调用$this 的setValue() value参数值为：$value")
        putSharePreferences(key, value)
    }

    @SuppressLint("CommitPrefEdits")
    private fun putSharePreferences(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type of data cannot be saved!")
        }.apply()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getSharePreferences(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type of data cannot be saved!")
        }
        return res as T
    }
}
