package com.example.mydiary.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardUtil(private val view: View) {

    fun showSoftKeyboard() {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(view, 0)
    }

    fun closeSoftKeyboard(): Boolean {
        val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.let {
            if (it.isActive) {
                return it.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
        return false
    }
}