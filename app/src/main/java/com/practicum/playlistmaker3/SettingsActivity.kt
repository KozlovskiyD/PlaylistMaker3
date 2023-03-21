package com.practicum.playlistmaker3

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val buttonBack = findViewById<ImageView>(R.id.back_main)
        buttonBack.setOnClickListener {
            finish()
        }

        val switchNight = findViewById<Switch>(R.id.switch1)
        switchNight.setOnCheckedChangeListener{ _, isChecked ->
            if (switchNight.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
        }

        val buttonShared = findViewById<ImageView>(R.id.write_to)
         buttonShared.setOnClickListener {
             val massage = getString (R.string.massage)
             val massageSubject = getString(R.string.massageSubject)
             val writeToIntent = Intent(Intent.ACTION_SENDTO)
             writeToIntent.data = Uri.parse("mailto:")
             writeToIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("myEmail"))
             writeToIntent.putExtra(Intent.EXTRA_SUBJECT, massageSubject)
             writeToIntent.putExtra(Intent.EXTRA_TEXT, massage)
             startActivity(writeToIntent)
         }

        val buttonWriteTo = findViewById<ImageView>(R.id.shar)
        buttonWriteTo.setOnClickListener {
            val massage = getString(R.string.uriDeveloper)
            val sharIntent = Intent(Intent.ACTION_SEND)
            sharIntent.setType("text/plain")
            sharIntent.putExtra(Intent.EXTRA_TEXT, massage)
            startActivity(sharIntent)
        }

        val buttonUserAgreement = findViewById<ImageView>(R.id.user_agreement)
        buttonUserAgreement.setOnClickListener{
            val massage = getString(R.string.uriAgreement)
            val urlUserAgreement = Uri.parse(massage)
            val userAgreementIntent = Intent(Intent.ACTION_VIEW, urlUserAgreement)
            startActivity(userAgreementIntent)
        }
    }
}