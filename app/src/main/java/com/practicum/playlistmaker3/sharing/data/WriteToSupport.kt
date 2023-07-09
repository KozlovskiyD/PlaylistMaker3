package com.practicum.playlistmaker3.sharing.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.practicum.playlistmaker3.R

class WriteToSupport {
    fun writeToSupportPlaylistMaker(context: Context) {
        val message = context.getString(R.string.message)
        val messageSubject = context.getString(R.string.messageSubject)
        val myEmail = context.getString(R.string.myEmail)
        Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(myEmail))
            putExtra(Intent.EXTRA_SUBJECT, messageSubject)
            putExtra(Intent.EXTRA_TEXT, message)
            context.startActivity(this)
        }
    }
}