package com.practicum.playlistmaker3

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker3.R.*
import java.text.SimpleDateFormat
import java.util.*

class MediaActivity() : AppCompatActivity() {

    private lateinit var currentTrack: Track
    private lateinit var buttonPlay: ImageView
    private lateinit var timer: TextView
    var mediaPlayer = MediaPlayer()
    var playerState = STATE_DEFAULT
    private var mainThreadHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_media)

        val cover = findViewById<ImageView>(id.cover)
        val trackNameMedia = findViewById<TextView>(id.track_name)
        val artistNameMedia = findViewById<TextView>(id.artist_name_media)
        val duration = findViewById<TextView>(id.content_duration)
        val album = findViewById<TextView>(id.content_album)
        val year = findViewById<TextView>(id.content_year)
        val genre = findViewById<TextView>(id.content_genre)
        val country = findViewById<TextView>(id.content_country)
        val buttonBack = findViewById<ImageView>(id.back_main)
        timer = findViewById(id.timer)
        buttonPlay = findViewById(id.button_play)


        buttonBack.setOnClickListener {
            finish()
        }
        val sharedPrefs = getSharedPreferences(SearchActivity.DATA, MODE_PRIVATE)
        val loadGson = sharedPrefs.getString(SearchActivity.KEY_TRACK, "")
        if (loadGson != "") {
            currentTrack = Gson().fromJson(loadGson, object : TypeToken<Track>() {}.type)

            Glide
                .with(applicationContext)
                .load(getCoverArtwork(currentTrack.artworkUrl100))
                .placeholder(drawable.vector_placeholder_big)
                .centerCrop()
                .transform(RoundedCorners(applicationContext.resources.getDimensionPixelSize(dimen.top_8)))
                .into(cover)

            artistNameMedia.text = currentTrack.artistName
            trackNameMedia.text = currentTrack.trackName
            year.text = currentTrack.releaseDate
            genre.text = currentTrack.primaryGenreName
            country.text = currentTrack.country
            duration.text = simpleDateFormat(currentTrack.trackTimeMillis)
            timer.text = simpleDateFormat("0")
            if (currentTrack.collectionName.isNotEmpty()) album.text = currentTrack.collectionName

            mainThreadHandler = Handler(Looper.getMainLooper())
            preparePlayer(currentTrack.previewUrl)

            buttonPlay.setOnClickListener {
                playbackControl()
                mainThreadHandler?.post(createTime())
            }
        }
    }

    private fun getCoverArtwork(art: String): String {
        return art.replaceAfterLast('/', "512x512bb.jpg")
    }

    private fun simpleDateFormat(time: String): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(time.toLong())
    }

    private fun createTime(): Runnable {
        return object : Runnable {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun run() {
                val duration = mediaPlayer.currentPosition.toLong()
                var second = mediaPlayer.duration.toLong()
                if (second > 0) {
                    second = duration / 1000L
                    timer.text = String.format("%02d:%02d", second / 60, second % 60)
                    mainThreadHandler?.postDelayed(this, DELAY_DEFAULT)
                }
            }
        }
    }

    private fun preparePlayer(previewUrl: String) {
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            mainThreadHandler?.removeCallbacksAndMessages(null)
            buttonPlay.setImageResource(drawable.button_play)
            timer.text = simpleDateFormat("0")
            playerState = STATE_PREPARED
        }
    }

    private fun startPlayer() {
        buttonPlay.setImageResource(drawable.vector_pause)
        mediaPlayer.start()
        playerState = STATE_PLAYING
    }

    private fun pausePlayer() {
        buttonPlay.setImageResource(drawable.button_play)
        mediaPlayer.pause()
        playerState = STATE_PAUSED
    }

    private fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onStop() {
        super.onStop()
        mainThreadHandler?.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DELAY_DEFAULT = 500L
    }
}