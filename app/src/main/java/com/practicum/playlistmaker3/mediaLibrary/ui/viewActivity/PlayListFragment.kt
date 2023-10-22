package com.practicum.playlistmaker3.mediaLibrary.ui.viewActivity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.databinding.FragmentPlaylistBinding
import com.practicum.playlistmaker3.mediaLibrary.ui.viewModelMediaLibrary.PlaylistFragmentViewModel
import com.practicum.playlistmaker3.player.ui.viewActivity.MediaActivity
import com.practicum.playlistmaker3.util.hideTheKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random


@Suppress("DEPRECATION")
open class PlayListFragment : Fragment() {

    protected open val viewModel by viewModel<PlaylistFragmentViewModel>()

    companion object {
        fun newInstance() = PlayListFragment()
        const val PICTURES_DIR = "myPlaylistPictures"
        const val COVER_JPG = "_cover.jpg"
    }

    private var _binding: FragmentPlaylistBinding? = null
    val binding get() = _binding!!


    private var namePlaylist = ""
    private var descriptionPlaylist = ""
    private var filePathName = ""
    private var isActiveNameText = false
    private var isActiveDescriptionText = false
    private var isActiveImageView = false
    private var isClickAllowed = true
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (thereDataPlaylist()) openDialog()
            else navigateController()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding.toCreatePlaylistButton.isEnabled = false


        val editTextName = binding.editNamePlaylist
        val watcherName = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isActiveNameText = s?.isNotEmpty() == true
                activationButton()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        watcherName.let { editTextName.addTextChangedListener(it) }

        val editTextDescription = binding.textDescriptionPlaylist
        val watcherDescription = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isActiveDescriptionText = s?.isNotEmpty() == true
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        watcherDescription.let { editTextDescription.addTextChangedListener(it) }

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    binding.imagePlaylist.setImageURI(uri)
                    saveImageToPrivateStorage(uri)
                }
            }
        binding.imagePlaylist.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        viewModel.observeClickDebounce().observe(viewLifecycleOwner) {
            isClickAllowed = it
        }

        binding.toCreatePlaylistButton.setOnClickListener {
            context?.hideTheKeyboard(binding.toCreatePlaylistButton)
            if (isActiveNameText && isClickAllowed) {
                viewModel.clickDebounce()
                namePlaylist = binding.editNamePlaylist.text.toString()
                descriptionPlaylist = binding.textDescriptionPlaylist.text.toString()
                viewModel.collectingPlaylist(namePlaylist, descriptionPlaylist, filePathName)
                Toast.makeText(requireContext(), "Плейлист $namePlaylist создан", Toast.LENGTH_LONG)
                    .show()
                navigateController()
            }
        }

        binding.back.setOnClickListener {
            if (thereDataPlaylist()) openDialog()
            else navigateController()
        }

    }

    private fun saveImageToPrivateStorage(uri: Uri) {
        val filePath = File(
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            PICTURES_DIR
        )
        if (!filePath.exists()) {
            filePath.mkdir()
        }
        filePathName = namePictured()
        val file = File(filePath, filePathName)

        Glide
            .with(binding.root.context)
            .load(uri)
            .transform(RoundedCorners(requireContext().resources.getDimensionPixelSize(R.dimen.top_8)))
            .into(binding.imagePlaylist)

        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
    }

    private fun namePictured(): String {
        isActiveImageView = true
        val name = binding.editNamePlaylist.text.toString()
        val random = Random.nextLong(10000000)
        return name + "_" + random + COVER_JPG
    }

    @SuppressLint("SuspiciousIndentation", "ResourceAsColor")
    private fun activationButton() {
        binding.toCreatePlaylistButton.isEnabled = isActiveNameText
    }

    @SuppressLint("ResourceType")
    private fun openDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.heading_dialog_playlist))
            .setMessage(resources.getString(R.string.description_dialog_playlist))
            .setNeutralButton(resources.getString(R.string.cancel)) { _, _ -> }
            .setNegativeButton(resources.getString(R.string.complete)) { _, _ ->
                navigateController()
            }
            .show()
    }

    private fun thereDataPlaylist(): Boolean {
        return isActiveNameText || isActiveDescriptionText || isActiveImageView
    }

    private fun navigateController() {
        callback.isEnabled = false
        if (activity is MediaActivity) {
            requireActivity().findViewById<ConstraintLayout>(R.id.constraint).isVisible = true
            requireActivity().findViewById<FragmentContainerView>(R.id.fragment_container).isVisible =
                false
            parentFragmentManager.popBackStack()
        } else {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}