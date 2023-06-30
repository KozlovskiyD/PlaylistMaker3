package com.practicum.playlistmaker3.presentation.settingsActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker3.R

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val buttonBack = findViewById<ImageView>(R.id.back_main)
        buttonBack.setOnClickListener {
            finish()
        }

        val switchNight = this.findViewById<Switch>(R.id.switch1)
        switchNight.setOnCheckedChangeListener { _, _ ->
            if (switchNight.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val buttonShare = findViewById<FrameLayout>(R.id.write_to)
        buttonShare.setOnClickListener {
            Intent(Intent.ACTION_SENDTO).apply {
                val message = this@SettingsActivity.getString(R.string.message)
                val messageSubject = this@SettingsActivity.getString(R.string.messageSubject)
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("myEmail"))
                putExtra(Intent.EXTRA_SUBJECT, messageSubject)
                putExtra(Intent.EXTRA_TEXT, message)
                startActivity(this)
            }
        }

        val buttonWriteTo = findViewById<FrameLayout>(R.id.shar)
        buttonWriteTo.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                val message = getString(R.string.uriDeveloper)
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, message)
                startActivity(this)
            }
        }

        val buttonUserAgreement = findViewById<FrameLayout>(R.id.user_agreement)
        buttonUserAgreement.setOnClickListener {
            val message = getString(R.string.uriAgreement)
            val urlUserAgreement = Uri.parse(message)
            val userAgreementIntent = Intent(Intent.ACTION_VIEW, urlUserAgreement)
            startActivity(userAgreementIntent)
        }
    }
}