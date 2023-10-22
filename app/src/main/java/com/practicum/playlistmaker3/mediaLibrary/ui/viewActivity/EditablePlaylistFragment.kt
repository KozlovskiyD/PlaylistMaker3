package com.practicum.playlistmaker3.mediaLibrary.ui.viewActivity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.mediaLibrary.domain.models.Playlist
import com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.EditablePlaylistFragmentViewModel
import com.practicum.playlistmaker3.util.hideTheKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

@Suppress("DEPRECATION")
class EditablePlaylistFragment : PlayListFragment() {

    override val viewModel: EditablePlaylistFragmentViewModel by viewModel()
    var filePathName = ""
    var isCurrentImage = false
    private val backCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            backScreen()
        }
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textNewPlaylist.text = getString(R.string.edit)
        binding.toCreatePlaylistButton.text = getString(R.string.save)
        val filePath = File(
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), PICTURES_DIR
        )

        if (!filePath.exists()) {
            filePath.mkdir()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallBack)

        val playlist =
            arguments?.getSerializable(CurrentPlaylistFragment.BUNGLE_KEY_EDIT) as Playlist
        viewModel.loadPlaylist(playlist)

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    isCurrentImage = true
                    binding.imagePlaylist.setImageURI(uri)
                    saveImageToPrivateStorage(uri, filePath)
                }
            }
        binding.imagePlaylist.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        viewModel.observeState().observe(viewLifecycleOwner) {
            val file = File(filePath, it.filePath!!)
            binding.editNamePlaylist.setText(it.namePlaylist)
            binding.textDescriptionPlaylist.setText(it.description)
            if (!it.filePath.isNullOrEmpty()) {
                Glide
                    .with(binding.root.context)
                    .load(file)
                    .transform(RoundedCorners(requireContext().resources.getDimensionPixelSize(R.dimen.top_8)))
                    .into(binding.imagePlaylist)
            }
        }

        viewModel.exitObserveState().observe(viewLifecycleOwner) {
            backScreen()
        }

        binding.toCreatePlaylistButton.setOnClickListener {
            context?.hideTheKeyboard(binding.toCreatePlaylistButton)
            playlist.namePlaylist = binding.editNamePlaylist.text.toString()
            playlist.description = binding.textDescriptionPlaylist.text.toString()
            if (isCurrentImage) playlist.filePath = filePathName
            viewModel.editPlaylist(playlist)
        }

        binding.back.setOnClickListener {
            backScreen()
        }
    }

    private fun saveImageToPrivateStorage(uri: Uri, filePath: File) {
        filePathName = namePictured()
        val file = File(filePath, filePathName)
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
    }

    private fun namePictured(): String {
        val name = binding.editNamePlaylist.text.toString()
        val random = Random.nextLong(10000000)
        return name + "_" + random + COVER_JPG
    }

    private fun backScreen() {
        backCallBack.isEnabled = false
        findNavController().navigateUp()
    }
}