package com.practicum.playlistmaker3.mediaLibrary.ui.viewActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.databinding.FragmentNewPlaylistBinding
import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist
import com.practicum.playlistmaker3.mediaLibrary.ui.viewActivity.adapter.PlaylistAdapter
import com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.NewPlaylistFragmentViewModel
import com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.states.ListPlaylistState
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewPlaylistFragment : Fragment() {

    companion object {
        const val BUNDLE_KEY = "bundle_key"
        fun newInstance() = NewPlaylistFragment()
        }

    private val viewModel by viewModel<NewPlaylistFragmentViewModel>()
    private var _binding: FragmentNewPlaylistBinding? = null
    private val binding get() = _binding!!

    private val playlistAdapter = PlaylistAdapter {
        val bundle = Bundle()
        bundle.putSerializable(BUNDLE_KEY, it)
        findNavController().navigate(R.id.action_mediaLibraryFragment_to_currentPlaylistFragment, bundle)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNewPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadListPlaylist()

        val newPlaylistButton = binding.newPlaylistButton
        newPlaylistButton.setOnClickListener {
            findNavController().navigate(R.id.action_mediaLibraryFragment_to_playListFragment)
        }

        viewModel.observeState().observe(viewLifecycleOwner) {
            playlistAdapter.setPlaylistItem(null)
            render(it)
        }
    }

    private fun render(state: ListPlaylistState) = when (state) {
        is ListPlaylistState.Content -> {
            showContent(state.playlist)
        }
        is ListPlaylistState.Empty -> {
            showEmpty()
        }
    }

    private fun showEmpty() {
        binding.imageError.isVisible = true
        binding.messageEmpty.isVisible = true
    }

    private fun showContent(playlist: List<Playlist>) {
        binding.imageError.isVisible = false
        binding.messageEmpty.isVisible = false
        binding.recyclerViewPlaylist.layoutManager = GridLayoutManager(context, 2)
        playlistAdapter.setPlaylistItem(playlist)
        binding.recyclerViewPlaylist.adapter = playlistAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}