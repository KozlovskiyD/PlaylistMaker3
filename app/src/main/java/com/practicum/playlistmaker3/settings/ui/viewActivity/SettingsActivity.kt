package com.practicum.playlistmaker3.settings.ui.viewActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.settings.ui.viewModelSettings.SettingsViewModel
import com.practicum.playlistmaker3.settings.ui.viewModelSettings.SettingsViewModelFactory

class SettingsActivity : AppCompatActivity() {

    private lateinit var vm: ViewModel

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        vm = ViewModelProvider(this, SettingsViewModelFactory(this))[SettingsViewModel::class.java]

        val buttonBack = findViewById<ImageView>(R.id.back_main)
        val switchNight = findViewById<Switch>(R.id.switch1)
        val buttonShare = findViewById<FrameLayout>(R.id.write_to)
        val buttonWriteTo = findViewById<FrameLayout>(R.id.shar)
        val buttonUserAgreement = findViewById<FrameLayout>(R.id.user_agreement)

        buttonBack.setOnClickListener {
            finish()
        }
        (vm as SettingsViewModel).switchCheck()

        (vm as SettingsViewModel).switchCheckedLiveData.observe(this) { check ->
            switchNight.isChecked = check
        }

        switchNight.setOnCheckedChangeListener { _, checked ->
            (vm as SettingsViewModel).saveSwitch(checked)

        }

        buttonShare.setOnClickListener {
            (vm as SettingsViewModel).write()
        }

        buttonWriteTo.setOnClickListener {
            (vm as SettingsViewModel).share()
        }

        buttonUserAgreement.setOnClickListener {
            (vm as SettingsViewModel).agreement()
        }
    }
}