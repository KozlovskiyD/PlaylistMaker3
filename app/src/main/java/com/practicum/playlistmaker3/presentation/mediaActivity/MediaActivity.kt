package com.practicum.playlistmaker3.presentation.mediaActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.R.id
import com.practicum.playlistmaker3.R.layout
import com.practicum.playlistmaker3.data.trackRepository.TrackRepositoryImpl
import com.practicum.playlistmaker3.data.trackRepository.createTime.CreateTimeImpl
import com.practicum.playlistmaker3.data.trackRepository.mediaPlayers.*
import com.practicum.playlistmaker3.domain.GetTrackUC
import com.practicum.playlistmaker3.domain.api.SimpleDataFormat
import com.practicum.playlistmaker3.domain.getCreateTime.GetCreateTimeUC
import com.practicum.playlistmaker3.domain.models.Track
import com.practicum.playlistmaker3.domain.setPreparePlayer.SetTrackUC


@Suppress("CAST_NEVER_SUCCEEDS")
class MediaActivity : SimpleDataFormat, GetCoverArtwork, AppCompatActivity() {

    private val getTrackUC = GetTrackUC()
    private val preparePlayer = MediaPlayers()
    private val trackRepositoryImpl = TrackRepositoryImpl(preparePlayer)
    private val setTrackUC = SetTrackUC(trackRepositoryImpl)
    private val createTimeImpl = CreateTimeImpl()
    private val getCreateTimeUC = GetCreateTimeUC(createTimeImpl)
    private lateinit var currentTrack: Track


    private lateinit var timer: TextView
    private lateinit var buttonPlay: ImageView
    private var playerState = STATE_DEFAULT

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_media)

        val trackNameMedia = findViewById<TextView>(id.track_name)
        val artistNameMedia = findViewById<TextView>(id.artist_name_media)
        val duration = findViewById<TextView>(id.content_duration)
        val album = findViewById<TextView>(id.content_album)
        val year = findViewById<TextView>(id.content_year)
        val genre = findViewById<TextView>(id.content_genre)
        val country = findViewById<TextView>(id.content_country)
        val buttonBack = findViewById<ImageView>(id.back_main)
        val cover = findViewById<ImageView>(id.cover)
        buttonPlay = findViewById(id.button_play)
        timer = findViewById(id.timer)

        val activity = intent.getBooleanExtra("activity", false)
        if (activity) {
            currentTrack =
                getTrack()                                                   //получить трек из searchActivity

            Glide
                .with(applicationContext)
                .load(getCoverArtwork(currentTrack.artworkUrl100))
                .placeholder(R.drawable.vector_placeholder_big)
                .centerCrop()
                .transform(RoundedCorners(applicationContext.resources.getDimensionPixelSize(R.dimen.top_8)))
                .into(cover)

            artistNameMedia.text = currentTrack.artistName
            trackNameMedia.text = currentTrack.trackName
            year.text = currentTrack.releaseDate
            genre.text = currentTrack.primaryGenreName
            country.text = currentTrack.country
            duration.text = simpleDateFormat(currentTrack.trackTimeMillis)
            timer.text = simpleDateFormat("0")
            if (currentTrack.collectionName.isNotEmpty()) album.text = currentTrack.collectionName

            preparePlayer(currentTrack)                                                               //подготовить MediaPlayer


            buttonPlay.setOnClickListener {
                playbackControl(playerState)                                                             //управление MediaPlayer
                createTime()                                                                                     //таймер
            }
        }
        buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun getTrack(): Track {
        return getTrackUC.execute(Intent(intent))
    }

    private fun preparePlayer(currentTrackPreviewUrl: Track) {
        if (!setTrackUC.sendTrackInData(currentTrackPreviewUrl)) {
            buttonPlay.setImageResource(R.drawable.button_play)
            timer.text = simpleDateFormat("0")
            playerState = STATE_PREPARED
        }
    }

    private fun playbackControl(statePlayer: Int) {
        setTrackUC.playbackControl(statePlayer)
        when (statePlayer) {
            STATE_PLAYING -> {
                buttonPlay.setImageResource(R.drawable.button_play)
                playerState = STATE_PAUSED
            }
            STATE_PREPARED, STATE_PAUSED -> {
                buttonPlay.setImageResource(R.drawable.vector_pause)
                playerState = STATE_PLAYING
            }
        }
    }

    private fun createTime() {
        val second = getCreateTimeUC.getCurrentTime()
        timer.text = String.format("%02d:%02d", second / 60, second % 60)
    }

    override fun onPause() {
        super.onPause()
        playbackControl(STATE_PLAYING)
    }

    override fun onStop() {
        super.onStop()
        playbackControl(STATE_HANDLER)
    }

    override fun onDestroy() {
        super.onDestroy()
        playbackControl(STATE_RELEASE)
    }
}











