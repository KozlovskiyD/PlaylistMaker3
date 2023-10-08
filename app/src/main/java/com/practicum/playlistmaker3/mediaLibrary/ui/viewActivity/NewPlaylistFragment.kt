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
import com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.ListPlaylistState
import com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.NewPlaylistFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewPlaylistFragment : Fragment() {

    companion object {
        fun newInstance() = NewPlaylistFragment()
    }

    private val viewModel by viewModel<NewPlaylistFragmentViewModel>()
    private var _binding: FragmentNewPlaylistBinding? = null
    private val binding get() = _binding!!

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
        binding.recyclerViewPlaylist.adapter = PlaylistAdapter(playlist)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}