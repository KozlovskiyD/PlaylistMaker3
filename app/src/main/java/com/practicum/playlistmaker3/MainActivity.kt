package com.practicum.playlistmaker3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSearch = findViewById<Button>(R.id.search_button)
        buttonSearch.setOnClickListener {
            Intent(this, SearchActivity::class.java).apply {
                startActivity(this)
            }
        }

        val buttonMedia = findViewById<Button>(R.id.media_button)
        buttonMedia.setOnClickListener {
            Intent(this, MediaActivity::class.java).apply { startActivity(this) }
        }

        val buttonSetting = findViewById<Button>(R.id.setting_button)
        buttonSetting.setOnClickListener {
            Intent(this, SettingsActivity::class.java).apply { startActivity(this) }
        }
    }
}