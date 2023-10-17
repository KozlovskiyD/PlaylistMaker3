package com.practicum.playlistmaker3.mediaLibrary.ui.viewActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.databinding.FragmentMediaLibraryBinding
import com.practicum.playlistmaker3.mediaLibrary.ui.viewActivity.adapter.MediaViewPagerAdapter

class MediaLibraryFragment : Fragment() {

    private var _binding: FragmentMediaLibraryBinding? = null
    private val binding get() = _binding!!

    private var _tabLayoutMediator: TabLayoutMediator? = null
    private val  tabLayoutMediator get() = _tabLayoutMediator!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMediaLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = MediaViewPagerAdapter(childFragmentManager, lifecycle)

        _tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = requireContext().resources.getString(R.string.selectedTrack)
                    1 -> tab.text = requireContext().resources.getString(R.string.playlists)
                }
            }
        tabLayoutMediator.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabLayoutMediator.detach()
        _binding = null
    }
}