package com.practicum.playlistmaker3.mediaLibrary.ui.viewActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.practicum.playlistmaker3.databinding.FragmentSelectedTrackBinding
import com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.TrackFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectedTrackFragment : Fragment() {
    companion object {
        fun newInstance() = SelectedTrackFragment()
    }

    private val vm by viewModel<TrackFragmentViewModel>()
    private var _binding: FragmentSelectedTrackBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentSelectedTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}