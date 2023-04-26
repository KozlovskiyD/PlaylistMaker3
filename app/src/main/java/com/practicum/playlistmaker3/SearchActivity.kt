package com.practicum.playlistmaker3

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker3.R.drawable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {

    val modelTrack = ArrayList<Track>()
    private lateinit var errorMessage: TextView
    private lateinit var clearButton: ImageView
    private lateinit var imageError: ImageView
    private lateinit var updateButton: Button
    private lateinit var trackListAdapter: TrackListAdapter
    private lateinit var inputEditText: EditText
    private lateinit var rvTrackList: RecyclerView

    private val baseUrl = "http://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val trackServer = retrofit.create(Itunes::class.java)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val buttonBack = findViewById<ImageView>(R.id.back_main)
        buttonBack.setOnClickListener {
            finish()
        }

        errorMessage = findViewById(R.id.errorMessage)
        clearButton = findViewById(R.id.clearIcon)
        updateButton = findViewById(R.id.update_button)
        inputEditText = findViewById(R.id.inputEditText)
        imageError = findViewById(R.id.image_error)
        rvTrackList = findViewById(R.id.trackList)

        clearButton.setOnClickListener {
            inputEditText.setText("")
            modelTrack.clear()
            hideTheKeyboard(clearButton)
            rvTrackList.visibility = View.GONE
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)

        trackListAdapter = TrackListAdapter(modelTrack)
        rvTrackList.adapter = trackListAdapter

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                request()
            }
            false
        }
    }

    private fun request() {
        trackServer.search(inputEditText.text.toString())
            .enqueue(object : Callback<DataTrackResponse> {
                override fun onResponse(
                    call: Call<DataTrackResponse>,
                    response: Response<DataTrackResponse>
                ) {
                    if (response.code() == 200) {
                        modelTrack.clear()
                        if (response.body()?.results?.isNotEmpty() == true) {
                            modelTrack.addAll(response.body()?.results!!)
                            trackListAdapter.notifyDataSetChanged()
                            rvTrackList.visibility = View.VISIBLE
                            imageError.visibility = View.GONE
                        }
                        if (modelTrack.isEmpty()) {
                            showMessage(getString(R.string.not_found), "")
                            imageError.setImageResource(drawable.vector_not_found)
                            imageError.visibility = View.VISIBLE
                            updateButton.visibility = View.GONE
                        } else {
                            showMessage("", "")
                        }
                    } else {
                        showMessage(
                            getString(R.string.not_found),
                            response.code().toString()
                        )
                        imageError.setImageResource(drawable.vector_not_found)
                        imageError.visibility = View.VISIBLE
                        updateButton.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<DataTrackResponse>, t: Throwable) {
                    showMessage(getString(R.string.no_signal), t.message.toString())
                    imageError.setImageResource(drawable.vector_no_signal)
                    imageError.visibility = View.VISIBLE
                    updateButton.visibility = View.VISIBLE
                    updateButton.setOnClickListener {
                        request()
                    }
                }
            })
        true
    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            modelTrack.clear()
            errorMessage.visibility = View.VISIBLE
            trackListAdapter.notifyDataSetChanged()
            errorMessage.text = text
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG).show()
            }
        } else {
            errorMessage.visibility = View.GONE
            updateButton.visibility = View.GONE
        }
    }

    private fun clearButtonVisibility(s: CharSequence?) {
        return if (s.isNullOrEmpty()) {
            clearButton.visibility = View.GONE
        } else clearButton.visibility = View.VISIBLE
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
    }
}






