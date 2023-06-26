package com.practicum.playlistmaker3.presentation.mainActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.presentation.mediaActivity.MediaActivity
import com.practicum.playlistmaker3.presentation.searchActivity.SearchActivity
import com.practicum.playlistmaker3.presentation.settingsActivity.SettingsActivity

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
            Intent(this, MediaActivity::class.java).apply {
            putExtra("activity", false)
                startActivity(this)
            }
        }

        val buttonSetting = findViewById<Button>(R.id.setting_button)
        buttonSetting.setOnClickListener {
            Intent(this, SettingsActivity::class.java).apply { startActivity(this) }
        }
    }
}