package com.practicum.playlistmaker3.presentation.searchActivity

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.R.drawable
import com.practicum.playlistmaker3.data.dto.DataTrackResponse
import com.practicum.playlistmaker3.data.network.Itunes
import com.practicum.playlistmaker3.domain.models.Track
import com.practicum.playlistmaker3.ui.search.TrackListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private lateinit var errorMessage: TextView
    private lateinit var clearButton: ImageView
    private lateinit var imageError: ImageView
    private lateinit var updateButton: Button
    private lateinit var clearHistory: Button
    private lateinit var inputEditText: EditText
    private lateinit var rvTrackList: RecyclerView
    private lateinit var youSearch: LinearLayout
    private lateinit var rvSearchList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var trackListAdapter: TrackListAdapter
    private lateinit var saveListAdapter: TrackListAdapter
    private lateinit var sharedPrefs: SharedPreferences

    private var listHistory: ArrayList<Track> = arrayListOf()
    private var youSearchClear = false
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { request() }

    private val baseUrl = "http://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val trackServer = retrofit.create(Itunes::class.java)

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        sharedPrefs = getSharedPreferences(DATA, MODE_PRIVATE)
        trackListAdapter = TrackListAdapter(sharedPrefs)
        saveListAdapter = TrackListAdapter(sharedPrefs)
        errorMessage = findViewById(R.id.errorMessage)
        clearButton = findViewById(R.id.clearIcon)
        updateButton = findViewById(R.id.update_button)
        clearHistory = findViewById(R.id.clear_history)
        inputEditText = findViewById(R.id.inputEditText)
        imageError = findViewById(R.id.image_error)
        rvTrackList = findViewById(R.id.trackList)
        youSearch = findViewById(R.id.you_search)
        rvSearchList = findViewById(R.id.save_list)
        progressBar = findViewById(R.id.progressBar)

        rvSearchList.adapter = saveListAdapter

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
            sharedPrefs.edit().clear().apply()
            youSearchClear = false
            youSearch.visibility = View.GONE
            rvTrackList.visibility = View.VISIBLE
        }

        sharedPrefs.registerOnSharedPreferenceChangeListener { _, _ ->
            loadHistory()
        }

        inputEditText.setOnFocusChangeListener { _, hasFocus ->
            loadHistory()
            youSearch.visibility =
                if (hasFocus && youSearchClear && inputEditText.text.isEmpty()) View.VISIBLE else View.GONE
            rvTrackList.visibility =
                if (hasFocus && inputEditText.text.isEmpty()) View.GONE else View.VISIBLE

        }


        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isEmpty() == true) loadHistory()
                searchDebounce(s)
                clearButtonVisibility(s)
                listsVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                request()
            }
            false
        }
    }

    private fun request() {
        progressBarVisibility()
        trackServer.search(inputEditText.text.toString())
            .enqueue(object : Callback<DataTrackResponse> {
                override fun onResponse(
                    call: Call<DataTrackResponse>,
                    response: Response<DataTrackResponse>
                ) {
                    progressBar.visibility = View.GONE
                    if ((response.code() == 200) and (response.body()?.results?.isNotEmpty() == true)) {
                        trackListAdapter = TrackListAdapter(sharedPrefs)
                        trackListAdapter.currentLists(true)
                        trackListAdapter.setTracks(response.body()?.results!!)
                        rvTrackList.adapter = trackListAdapter
                        rvTrackList.visibility = View.VISIBLE
                        imageError.visibility = View.GONE
                        errorMessage.visibility = View.GONE
                        updateButton.visibility = View.GONE
                    } else {
                        showMessage()
                        imageError.setImageResource(drawable.vector_not_found)
                        errorMessage.text = getString(R.string.not_found)
                        updateButton.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<DataTrackResponse>, t: Throwable) {
                    showMessage()
                    val additionalMessage = t.message.toString()
                    imageError.setImageResource(drawable.vector_no_signal)
                    errorMessage.text = getString(R.string.no_signal)
                    updateButton.visibility = View.VISIBLE
                    if (additionalMessage.isNotEmpty()) {
                        Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                            .show()
                    }
                    updateButton.setOnClickListener {
                        request()
                    }
                }
            })
        true
    }

    private fun showMessage() {
        trackListAdapter.setTracks(null)
        imageError.visibility = View.VISIBLE
        errorMessage.visibility = View.VISIBLE
        rvTrackList.visibility = View.GONE
    }

    private fun progressBarVisibility() {
        if (inputEditText.text.isNotEmpty()) {
            errorMessage.visibility = View.GONE
            rvTrackList.visibility = View.GONE
            imageError.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun loadHistory() {
        val loadGson = sharedPrefs.getString(KEY, "")
        if (loadGson != "") {
            listHistory = Gson().fromJson(loadGson, object : TypeToken<ArrayList<Track>>() {}.type)
            saveListAdapter.currentLists(false)
            saveListAdapter.setTracks(listHistory)
            youSearchClear = true
        }
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

    private fun searchDebounce(s: CharSequence?) {
        if (s?.isNotEmpty() == true) {
            handler.removeCallbacks(searchRunnable)
            handler.postDelayed(searchRunnable, SEARCH_DELAY)
        }
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

    companion object {
        const val SAVE_TEXT = "SAVE_TEXT"
        const val DATA = "data"
        const val KEY = "key"
        const val CLICK_DEBOUNCE_DELAY = 1000L
        const val SEARCH_DELAY = 2000L
    }
}








