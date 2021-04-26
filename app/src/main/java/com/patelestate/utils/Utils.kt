package com.patelestate.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

object Utils {
    fun hideSoftKeyboard(con: Context, view: View) {
        view?.let {
            val inputMethodManager =
                ContextCompat.getSystemService(con, InputMethodManager::class.java)!!
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}