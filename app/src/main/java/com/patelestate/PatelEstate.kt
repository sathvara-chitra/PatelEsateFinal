package com.patelestate

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class PatelEstate : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        var width = 0
        var isBackground = false
        fun isNetworkConnected(mContext: Context): Boolean {
            val cm = mContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null
        }
    }
}