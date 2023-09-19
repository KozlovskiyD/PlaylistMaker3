package com.practicum.playlistmaker3.mediaLibrary.ui.viewActivity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.practicum.playlistmaker3.databinding.FragmentSelectedTrackBinding
import com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.TrackFavoriteState
import com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.TrackIsFavoriteViewModel
import com.practicum.playlistmaker3.player.ui.viewActivity.MediaActivity
import com.practicum.playlistmaker3.player.ui.viewActivity.MediaActivity.Companion.TRACK
import com.practicum.playlistmaker3.search.domain.models.Track
import com.practicum.playlistmaker3.search.ui.viewActivity.TrackListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTrackFragment : Fragment() {
    companion object {
        fun newInstance() = FavoriteTrackFragment()
    }

    private val viewModel by viewModel<TrackIsFavoriteViewModel>()

    private var _binding: FragmentSelectedTrackBinding? = null
    private val binding get() = _binding!!

    private val trackListAdapter = TrackListAdapter {
        Intent(requireContext(), MediaActivity::class.java).apply {
            putExtra(TRACK, it)
            startActivity(this)
        }
    }

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
        viewModel.loadFavoriteList()
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.loadFavoriteList()
    }

    private fun render(state: TrackFavoriteState) {
        when (state) {
            is TrackFavoriteState.Empty -> showEmpty()
            is TrackFavoriteState.Content -> showContent(state.FavoriteTracks)
        }
    }

    private fun showEmpty() {
        binding.imageError.isVisible = true
        binding.messageEmpty.isVisible = true
    }

    private fun showContent(tracks: List<Track>) {
        binding.imageError.isVisible = false
        binding.messageEmpty.isVisible = false
        trackListAdapter.setTracks(tracks)
        binding.favoriteList.adapter = trackListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}