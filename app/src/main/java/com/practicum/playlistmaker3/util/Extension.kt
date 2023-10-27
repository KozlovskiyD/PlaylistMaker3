package com.practicum.playlistmaker3.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.hideTheKeyboard(currentView: View) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(currentView.windowToken, 0)
}