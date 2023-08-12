package com.practicum.playlistmaker3.main.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.main.ui.viewModel.MainViewModel
import com.practicum.playlistmaker3.mediaLibrary.ui.viewActivity.MediaLibrary
import com.practicum.playlistmaker3.search.ui.viewActivity.SearchActivity
import com.practicum.playlistmaker3.settings.ui.viewActivity.SettingsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm.setTheme()

        val buttonSearch = findViewById<Button>(R.id.search_button)
        buttonSearch.setOnClickListener {
            Intent(this, SearchActivity::class.java).apply {
                startActivity(this)
            }
        }

        val buttonMedia = findViewById<Button>(R.id.media_button)
        buttonMedia.setOnClickListener {
            Intent(this, MediaLibrary::class.java).apply {
                startActivity(this)
            }
        }

        val buttonSetting = findViewById<Button>(R.id.setting_button)
        buttonSetting.setOnClickListener {
            Intent(this, SettingsActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}