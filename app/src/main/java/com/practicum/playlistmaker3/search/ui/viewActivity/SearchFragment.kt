package com.practicum.playlistmaker3.search.ui.viewActivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.databinding.FragmentSearchBinding
import com.practicum.playlistmaker3.player.ui.viewActivity.MediaActivity
import com.practicum.playlistmaker3.player.ui.viewActivity.MediaActivity.Companion.TRACK
import com.practicum.playlistmaker3.search.domain.models.Track
import com.practicum.playlistmaker3.search.hideTheKeyboard
import com.practicum.playlistmaker3.search.ui.viewModelSearch.SearchViewModel
import com.practicum.playlistmaker3.search.ui.viewModelSearch.TracksSearchState
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val viewModel by viewModel<SearchViewModel>()

    private val trackListAdapter = TrackListAdapter {
        viewModel.saveHistory(it)
        viewModel.clickDebounce()
        youSearchClear = true
        if (isClickAllowed) {
            Intent(requireContext(), MediaActivity::class.java).apply {
                putExtra(TRACK, it)
                startActivity(this)
            }
        }
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var youSearchClear = false
    private var textRequest = ""
    private var isClickAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clearButton = binding.clearIcon
        val inputEditText = binding.inputEditText
        viewModel.loadHistory()

        clearButton.setOnClickListener {
            inputEditText.setText("")
            trackListAdapter.setTracks(null)
            context?.hideTheKeyboard(clearButton)
        }

        binding.clearHistory.setOnClickListener {
            trackListAdapter.setTracks(null)
            youSearchClear = false
            binding.youSearchText.isVisible = false
            binding.clearHistory.isVisible = false
            binding.trackList.isVisible = true
            viewModel.clearHistory()
        }

        inputEditText.setOnFocusChangeListener { _, hasFocus ->
            viewModel.loadHistory()
            binding.youSearchText.isVisible =
                hasFocus && youSearchClear && inputEditText.text.isEmpty()
            binding.clearHistory.isVisible =
                hasFocus  && youSearchClear && inputEditText.text.isEmpty()
            binding.trackList.isVisible = hasFocus && inputEditText.text.isEmpty()
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isEmpty() == true) {
                    viewModel.loadHistory()
                }
                textRequest = inputEditText.text.toString()
                viewModel.searchDebounce(s?.toString() ?: "")
                clearButtonAndListsVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        simpleTextWatcher.let { inputEditText.addTextChangedListener(it) }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.observeShowToast().observe(viewLifecycleOwner) {
            showToast(it)
        }

        viewModel.observeClickDebounce().observe(viewLifecycleOwner) {
            isClickAllowed = it
        }

        binding.updateButton.setOnClickListener {
            viewModel.requestOnListTrack(textRequest)
        }
    }

    private fun showMessage() {
        trackListAdapter.setTracks(null)
        binding.imageError.isVisible = true
        binding.errorMessage.isVisible = true
        binding.trackList.isVisible = false
        binding.progressBar.isVisible = false
    }

    private fun render(state: TracksSearchState) = when (state) {
        is TracksSearchState.Content -> {
            showContent(state.currentTracks)
        }
        is TracksSearchState.Empty -> {
            showEmpty()
        }
        is TracksSearchState.Error -> {
            showError()
        }
        is TracksSearchState.Loading -> {
            showLoading()
        }
        is TracksSearchState.History -> {
            showHistory(state.historyTracks)
        }
    }

    private fun showContent(tracks: List<Track>) {
        trackListAdapter.setTracks(tracks)
        binding.trackList.adapter = trackListAdapter
        binding.trackList.isVisible = true
        binding.imageError.isVisible = false
        binding.errorMessage.isVisible = false
        binding.updateButton.isVisible = false
        binding.progressBar.isVisible = false
    }

    private fun showEmpty() {
        showMessage()
        binding.imageError.setImageResource(R.drawable.vector_not_found)
        binding.errorMessage.text = getString(R.string.not_found)
        binding.updateButton.isVisible = false
    }

    private fun showError() {
        showMessage()
        binding.imageError.setImageResource(R.drawable.vector_no_signal)
        binding.errorMessage.text = getString(R.string.no_signal)
        binding.updateButton.isVisible = true
        showToast(getString(R.string.no_internet))
    }

    private fun showLoading() {
        binding.errorMessage.isVisible = false
        binding.trackList.isVisible = false
        binding.imageError.isVisible = false
        binding.progressBar.isVisible = true
        binding.updateButton.isVisible = false
    }

    private fun showHistory(listHistory: List<Track>) {
        if (listHistory.isNotEmpty()) youSearchClear = true
        binding.progressBar.isVisible = false
        trackListAdapter.setTracks(listHistory)
        binding.trackList.adapter = trackListAdapter
    }

    private fun showToast(additionalMessage: String) {
        Toast.makeText(requireContext(), additionalMessage, Toast.LENGTH_LONG)
            .show()
    }

    private fun clearButtonAndListsVisibility(s: CharSequence?) {
        binding.clearIcon.isVisible = !s.isNullOrEmpty()
        if (!binding.imageError.isVisible) {
            binding.youSearchText.isVisible = s.isNullOrEmpty() && youSearchClear
            binding.clearHistory.isVisible = s.isNullOrEmpty() && youSearchClear
            binding.trackList.isVisible = s.isNullOrEmpty() && youSearchClear
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val saveEditText = ""
        outState.putString(SAVE_TEXT, saveEditText)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.removeLoadingObserver()
        _binding = null
    }

    companion object {
        const val SAVE_TEXT = "SAVE_TEXT"
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}