package com.practicum.playlistmaker3.sharing.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.practicum.playlistmaker3.R

class UserAgreement {
    fun openUserAgreement(context: Context) {
        val message = context.getString(R.string.uriAgreement)
        val urlUserAgreement = Uri.parse(message)
        val userAgreementIntent = Intent(Intent.ACTION_VIEW, urlUserAgreement)
        context.startActivity(userAgreementIntent)
    }
}