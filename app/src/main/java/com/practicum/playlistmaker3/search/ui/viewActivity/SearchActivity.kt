package com.practicum.playlistmaker3.search.ui.viewActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.databinding.ActivitySearchBinding
import com.practicum.playlistmaker3.player.ui.viewActivity.ACTIVITY
import com.practicum.playlistmaker3.player.ui.viewActivity.MediaActivity
import com.practicum.playlistmaker3.player.ui.viewActivity.TRACK
import com.practicum.playlistmaker3.search.domain.models.Track
import com.practicum.playlistmaker3.search.hideTheKeyboard
import com.practicum.playlistmaker3.search.ui.viewModelSearch.SearchViewModel
import com.practicum.playlistmaker3.search.ui.viewModelSearch.TracksSearchState
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var errorMessage: TextView
    private lateinit var clearButton: ImageView
    private lateinit var imageError: ImageView
    private lateinit var updateButton: Button
    private lateinit var clearHistory: Button
    private lateinit var inputEditText: EditText
    private lateinit var rvTrackList: RecyclerView
    private lateinit var youSearch: LinearLayout
    private lateinit var rvSaveList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var binding: ActivitySearchBinding

    private var youSearchClear = false
    private var textRequest = ""
    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    private val vm by viewModel<SearchViewModel>()

    private val trackListAdapter = TrackListAdapter {
        vm.saveHistory(it)
        if (clickDebounce()) {
            Intent(this, MediaActivity::class.java).apply {
                putExtra(ACTIVITY, true)
                putExtra(TRACK, it)
                startActivity(this)
            }
        }
    }

    private val saveListAdapter = TrackListAdapter {
        vm.saveHistory(it)
        if (clickDebounce()) {
            Intent(this, MediaActivity::class.java).apply {
                putExtra(ACTIVITY, true)
                putExtra(TRACK, it)
                startActivity(this)
            }
        }
    }

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        binding = ActivitySearchBinding.inflate((layoutInflater))
        setContentView(binding.root)

        errorMessage = findViewById(R.id.errorMessage)
        clearButton = findViewById(R.id.clearIcon)
        updateButton = findViewById(R.id.update_button)
        clearHistory = findViewById(R.id.clear_history)
        inputEditText = findViewById(R.id.inputEditText)
        imageError = findViewById(R.id.image_error)
        rvTrackList = findViewById(R.id.trackList)
        youSearch = findViewById(R.id.you_search)
        rvSaveList = findViewById(R.id.save_list)
        progressBar = findViewById(R.id.progressBar)

        val buttonBack = findViewById<ImageView>(R.id.back_main)
        buttonBack.setOnClickListener {
            finish()
        }

        clearButton.setOnClickListener {
            inputEditText.setText("")
            trackListAdapter.setTracks(null)
            hideTheKeyboard(clearButton)
        }

        clearHistory.setOnClickListener {
            saveListAdapter.setTracks(null)
            youSearchClear = false
            youSearch.visibility = View.GONE
            rvTrackList.visibility = View.VISIBLE
            vm.clearHistory()
        }

        inputEditText.setOnFocusChangeListener { _, hasFocus ->
            vm.loadHistory()
            youSearch.visibility =
                if (hasFocus && youSearchClear && inputEditText.text.isEmpty()) View.VISIBLE else View.GONE
            rvTrackList.visibility =
                if (hasFocus && inputEditText.text.isEmpty()) View.GONE else View.VISIBLE
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isEmpty() == true) vm.loadHistory()
                textRequest = inputEditText.text.toString()
                vm.searchDebounce(s?.toString() ?: "")
                clearButtonVisibility(s)
                listsVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        simpleTextWatcher.let { inputEditText.addTextChangedListener(it) }

        vm.observeState().observe(this) {
            render(it)
        }

        vm.observeShowToast().observe(this) {
            showToast(it)
        }

        updateButton.setOnClickListener {
            vm.requestOnListTrack(textRequest)
        }
    }

    private fun showMessage() {
        trackListAdapter.setTracks(null)
        imageError.visibility = View.VISIBLE
        errorMessage.visibility = View.VISIBLE
        rvTrackList.visibility = View.GONE
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
        rvTrackList.adapter = trackListAdapter
        rvTrackList.visibility = View.VISIBLE
        imageError.visibility = View.GONE
        errorMessage.visibility = View.GONE
        updateButton.visibility = View.GONE
    }

    private fun showEmpty() {
        showMessage()
        imageError.setImageResource(R.drawable.vector_not_found)
        errorMessage.text = getString(R.string.not_found)
        updateButton.visibility = View.GONE
    }

    private fun showError() {
        showMessage()
        imageError.setImageResource(R.drawable.vector_no_signal)
        errorMessage.text = getString(R.string.no_signal)
        updateButton.visibility = View.VISIBLE
        showToast(getString(R.string.no_internet))
    }

    private fun showLoading() {
        errorMessage.visibility = View.GONE
        rvTrackList.visibility = View.GONE
        imageError.visibility = View.GONE
        binding.progressBar.isVisible = true
        binding.updateButton.isVisible = false
    }

    private fun showHistory(listHistory: List<Track>) {
        binding.progressBar.isVisible = false
        saveListAdapter.setTracks(listHistory)
        rvSaveList.adapter = saveListAdapter
        youSearchClear = true
    }

    private fun showToast(additionalMessage: String) {
        Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
            .show()
    }

    private fun listsVisibility(s: CharSequence?) {
        youSearch.visibility =
            if (youSearchClear && s?.isEmpty() == true) View.VISIBLE else View.GONE
        rvTrackList.visibility = if (s?.isEmpty() == true) View.GONE else View.VISIBLE
    }

    private fun clearButtonVisibility(s: CharSequence?) {
        return if (s.isNullOrEmpty()) {
            clearButton.visibility = View.GONE
        } else clearButton.visibility = View.VISIBLE
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val saveEditText = ""
        outState.putString(SAVE_TEXT, saveEditText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString(SAVE_TEXT).toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.removeLoadingObserver()
    }

    companion object {
        const val SAVE_TEXT = "SAVE_TEXT"
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}