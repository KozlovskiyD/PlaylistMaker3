package com.practicum.playlistmaker3.sharing.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.practicum.playlistmaker3.R

class UserAgreement(private val context: Context) {
    fun openUserAgreement() {
        Intent(Intent.ACTION_VIEW).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val message = context.getString(R.string.uriAgreement)
            Uri.parse(message)
            context.startActivity(this)
        }
    }
}