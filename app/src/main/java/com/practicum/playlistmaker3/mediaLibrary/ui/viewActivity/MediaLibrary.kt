package com.practicum.playlistmaker3.mediaLibrary.ui.viewActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.databinding.ActivityMediaLibraryBinding

class MediaLibrary : AppCompatActivity() {

    private var _binding: ActivityMediaLibraryBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMediaLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = MediaViewPagerAdapter(supportFragmentManager, lifecycle)

        tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.selectedTrack)
                    1 -> tab.text = getString(R.string.playlists)
                }
            }
        tabLayoutMediator.attach()

        binding.backMain.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
    }
}