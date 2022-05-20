package com.febryan.e_market.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.febryan.e_market.model.Data
import com.google.gson.Gson

class SharedPreference(activity: Context){

    val login = "Login"
    val myPref = "Main_Pref"

    val user = "User"
    val sharedPreference: SharedPreferences

    init {
        sharedPreference = activity.getSharedPreferences(myPref, Context.MODE_PRIVATE)
    }

    fun setStatusLogin(status: Boolean){
        sharedPreference.edit().putBoolean(login, status).apply()
    }

    fun getStatusLogin():Boolean{
        return sharedPreference.getBoolean(login, false)
    }

    fun setUser(value: Data){
        // ubah dari data object ke data string
        val data = Gson().toJson(value, Data::class.java)
        sharedPreference.edit().putString(user, data).apply()
    }

    fun getUser(): Data? {
        val data = sharedPreference.getString(user, null)
        return if (data != null) {
            // ubah dari data string ke data object
            Gson().fromJson(data, Data::class.java)
        } else {
            null
        }
    }
}