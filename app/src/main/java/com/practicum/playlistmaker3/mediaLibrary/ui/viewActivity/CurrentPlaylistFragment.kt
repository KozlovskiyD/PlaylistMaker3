package com.practicum.playlistmaker3.mediaLibrary.ui.viewActivity

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.databinding.FragmentCurrentPlaylistBinding
import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist
import com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.CurrentPlaylistFragmentViewModel
import com.practicum.playlistmaker3.player.ui.viewActivity.MediaActivity
import com.practicum.playlistmaker3.search.domain.models.Track
import com.practicum.playlistmaker3.search.ui.viewActivity.TrackListAdapter
import com.practicum.playlistmaker3.util.getTrackNumber
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

@Suppress("CAST_NEVER_SUCCEEDS", "DEPRECATION")
class CurrentPlaylistFragment : Fragment() {

    companion object {
        const val BUNGLE_KEY = "bungle_key"
        const val BUNGLE_KEY_EDIT = "bungle_key_edit"
        fun newInstance() = NewPlaylistFragment()
    }

    private val viewModel by viewModel<CurrentPlaylistFragmentViewModel>()
    private var _binding: FragmentCurrentPlaylistBinding? = null
    private val binding get() = _binding!!
    private var playlistId = 0

    private val trackListAdapter = TrackListAdapter { track, isDialog ->
        if (isDialog) openDialogDeleteTrack(track)
        else {
            Intent(requireContext(), MediaActivity::class.java).apply {
                putExtra(MediaActivity.TRACK, track)
                startActivity(this)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playlist = arguments?.getSerializable(BUNGLE_KEY) as Playlist
        playlistId = playlist.id!!

        val tracks = mutableListOf<Track>()

        val filePath = File(
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            PlayListFragment.PICTURES_DIR
        )
        val file = File(filePath, playlist.filePath!!)

        if (playlist.trackCount == 0) binding.textNoTrackPlaylist.isVisible = true

        viewModel.loadListTrack(playlist.trackList)
        viewModel.observeStateTime().observe(viewLifecycleOwner) {
            binding.textTrackTimeCurrentPlaylist.text = it
        }
        viewModel.observeStateTrack().observe(viewLifecycleOwner) {
            binding.textTrackNumberCurrentPlaylist.text = it
        }

        binding.textTrackNameCurrentPlaylist.text = playlist.namePlaylist
        binding.textTrackDescriptionCurrentPlaylist.text = playlist.description
        binding.imageCurrentPlaylist.setImageURI(file.toUri())

        binding.textBottomSheetEditPlaylist.text = playlist.namePlaylist
        binding.textTrackSheetEditPlaylist.text =
            String.format("%d %s", playlist.trackCount, getTrackNumber(playlist.trackCount))

        Glide
            .with(binding.root.context)
            .load(file)
            .placeholder(R.drawable.vector_placeholder)
            .transform(RoundedCorners(requireContext().resources.getDimensionPixelSize(R.dimen.top_2)))
            .into(binding.imageBottomSheetEditPlaylist)

        viewModel.observeState().observe(viewLifecycleOwner) {
            binding.textNoTrackPlaylist.isVisible = it.isNullOrEmpty()
            tracks.addAll(it)
            trackListAdapter.setTracks(it, true)
            binding.rvBottomSheetPlaylist.adapter = trackListAdapter
        }

        val bottomSheetEdit = binding.bottomSheetEditPlaylist
        val overlayView = binding.overlayPlaylist
        val boomSheetEdit = BottomSheetBehavior.from(bottomSheetEdit).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        boomSheetEdit.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> overlayView.visibility = View.GONE
                    else -> overlayView.visibility = View.VISIBLE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        val bottomSheetTrack = binding.bottomSheetTrackPlaylist
        val boomSheetTrack = BottomSheetBehavior.from(bottomSheetTrack).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        boomSheetTrack.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        binding.buttonMenuCurrentPlaylist.setOnClickListener {
            boomSheetEdit.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.buttonToShareCurrentPlaylist.setOnClickListener {
            if (playlist.trackCount > 0) {
                viewModel.showMessage(playlist, tracks)
                viewModel.observeStateMessage().observe(viewLifecycleOwner) { message ->
                    shareToApp(message)
                }
            } else showToast()
        }

        binding.textToShareCurrentPlaylist.setOnClickListener {
            if (playlist.trackCount > 0) {
                viewModel.showMessage(playlist, tracks)
                viewModel.observeStateMessage().observe(viewLifecycleOwner) { message ->
                    shareToApp(message)
                }
            } else showToast()
        }

        binding.textEditInfoCurrentPlaylist.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(BUNGLE_KEY_EDIT, playlist)
            findNavController().navigate(
                R.id.action_currentPlaylistFragment_to_editablePlaylistFragment,
                bundle
            )
        }

        binding.textDeleteCurrentPlaylist.setOnClickListener {
            boomSheetEdit.state = BottomSheetBehavior.STATE_HIDDEN
            openDialogDeletePlaylist(playlist)
        }

        buttonMenuLocation()

        viewModel.observeStateExit().observe(viewLifecycleOwner) {
            if (it) viewModel.deletePlaylist(playlist)
                else backScreen()
        }

        binding.back.setOnClickListener {
            backScreen()
        }
    }

    private fun buttonMenuLocation() {
        binding.buttonMenuCurrentPlaylist.post {
            val buttonLocation = IntArray(2)
            binding.buttonMenuCurrentPlaylist.getLocationOnScreen(buttonLocation)
            val buttonMenuHeightFromBottom =
                binding.root.height - buttonLocation[1] - resources.getDimensionPixelSize(R.dimen.top_8)
            val bottomSheetBehavior =
                BottomSheetBehavior.from(binding.bottomSheetTrackPlaylist)
            bottomSheetBehavior.peekHeight = buttonMenuHeightFromBottom
        }
    }

    private fun openDialogDeleteTrack(track: Track) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_track))
            .setMessage(getString(R.string.sure_delete_track))
            .setNeutralButton(getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                viewModel.deleteTrack(track, playlistId)
            }
            .show()
    }

    private fun openDialogDeletePlaylist(playlist: Playlist) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_playlist))
            .setMessage(getString(R.string.want_delete_playlist))
            .setNegativeButton("Нет") { _, _ -> }
            .setPositiveButton("Да") { _, _ ->
                viewModel.deleteTracksPlaylist(playlist)
            }
            .show()
    }

    private fun shareToApp(message: String) {
        Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
            requireContext().startActivity(this)
        }
    }

    private fun showToast() {
        val message = getString(R.string.do_not_share_message)
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun backScreen() {
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}