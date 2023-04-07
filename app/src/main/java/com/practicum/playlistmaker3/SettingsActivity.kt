package com.practicum.playlistmaker3

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val buttonBack = findViewById<ImageView>(R.id.back_main)
        buttonBack.setOnClickListener {
            finish()
        }

        val switchNight = this.findViewById<Switch>(/* id = */ R.id.switch1)
        switchNight.setOnCheckedChangeListener{ _, isChecked ->
            if (switchNight.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
        }

        val buttonShared = findViewById<ImageView>(R.id.write_to)
         buttonShared.setOnClickListener {
             Intent(Intent.ACTION_SENDTO).apply {
                 val massage = this@SettingsActivity.getString (R.string.massage)
                 val massageSubject = this@SettingsActivity.getString(R.string.massageSubject)
                 data = Uri.parse("mailto:")
                 putExtra(Intent.EXTRA_EMAIL, arrayOf("myEmail"))
                 putExtra(Intent.EXTRA_SUBJECT, massageSubject)
                 putExtra(Intent.EXTRA_TEXT, massage)
                 startActivity(this)
             }
         }

        val buttonWriteTo = findViewById<ImageView>(R.id.shar)
        buttonWriteTo.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                val massage = getString(R.string.uriDeveloper)
                setType("text/plain")
                putExtra(Intent.EXTRA_TEXT, massage)
                startActivity(this)
            }
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