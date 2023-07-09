package com.practicum.playlistmaker3.main.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.player.ui.viewActivity.ACTIVITY
import com.practicum.playlistmaker3.search.ui.viewActivity.SearchActivity
import com.practicum.playlistmaker3.settings.ui.viewActivity.SettingsActivity

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
            Intent(this, MainActivity::class.java).apply {
                putExtra(ACTIVITY, false)
                startActivity(this)
            }
        }

        val buttonSetting = findViewById<Button>(R.id.setting_button)
        buttonSetting.setOnClickListener {
            Log.e("TEST", "лользоват8")
            Intent(this, SettingsActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}