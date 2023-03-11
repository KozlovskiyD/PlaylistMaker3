package com.practicum.playlistmaker3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val buttonMedia = findViewById<ImageView>(R.id.back_main)
        buttonMedia.setOnClickListener {
            finish()
        }
    }
}