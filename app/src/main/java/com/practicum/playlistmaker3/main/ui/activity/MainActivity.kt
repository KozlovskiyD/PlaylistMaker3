package com.practicum.playlistmaker3.main.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.main.ui.viewModel.MainViewModel
import com.practicum.playlistmaker3.player.ui.viewActivity.ACTIVITY
import com.practicum.playlistmaker3.player.ui.viewActivity.MediaActivity
import com.practicum.playlistmaker3.search.ui.viewActivity.SearchActivity
import com.practicum.playlistmaker3.settings.ui.viewActivity.SettingsActivity
import com.practicum.playlistmaker3.util.Creator

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = ViewModelProvider(this,
            MainViewModel.getViewModelFactory(settingIteractor = Creator.provideSettingIterator(
                application)))[MainViewModel::class.java]

        vm.setTheme()

        val buttonSearch = findViewById<Button>(R.id.search_button)
        buttonSearch.setOnClickListener {
            Intent(this, SearchActivity::class.java).apply {
                startActivity(this)
            }
        }

        val buttonMedia = findViewById<Button>(R.id.media_button)
        buttonMedia.setOnClickListener {
            Intent(this, MediaActivity::class.java).apply {
                putExtra(ACTIVITY, false)
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