package com.practicum.playlistmaker3.settings.ui.viewActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.settings.ui.viewModelSettings.SettingsViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var vm: SettingsViewModel

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        vm = ViewModelProvider(this,
            SettingsViewModel.getViewModelFactory())[SettingsViewModel::class.java]

        val buttonBack = findViewById<ImageView>(R.id.back_main)
        val switchNight = findViewById<Switch>(R.id.switch1)
        val buttonShare = findViewById<FrameLayout>(R.id.write_to)
        val buttonWriteTo = findViewById<FrameLayout>(R.id.shar)
        val buttonUserAgreement = findViewById<FrameLayout>(R.id.user_agreement)

        buttonBack.setOnClickListener {
            finish()
        }
        vm.switchCheck()

        vm.switchCheckedLiveData.observe(this) { check ->
            switchNight.isChecked = check
        }

        switchNight.setOnCheckedChangeListener { _, checked ->
            vm.saveSwitch(checked)

        }

        buttonShare.setOnClickListener {
            vm.write()
        }

        buttonWriteTo.setOnClickListener {
            vm.share()
        }

        buttonUserAgreement.setOnClickListener {
            vm.agreement()
        }
    }
}