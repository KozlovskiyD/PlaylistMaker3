package com.practicum.playlistmaker3.player.ui.viewActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.R.id
import com.practicum.playlistmaker3.R.layout
import com.practicum.playlistmaker3.search.domain.models.Track
import com.practicum.playlistmaker3.search.domain.models.getCoverArtwork
import com.practicum.playlistmaker3.player.data.mediaPlayer.STATE_PLAYING
import com.practicum.playlistmaker3.player.data.mediaPlayer.STATE_RELEASE
import com.practicum.playlistmaker3.player.ui.viewModelMediaPlayer.TrackViewModel
import com.practicum.playlistmaker3.player.ui.viewModelMediaPlayer.TrackViewModelFactory
import com.practicum.playlistmaker3.simpleDateFormat

const val DELAY_DEFAULT = 500L
const val THOUSAND_L = 1000L
const val ACTIVITY = "activity"
const val TRACK = "track"

@Suppress("CAST_NEVER_SUCCEEDS")
class MediaActivity : AppCompatActivity() {

    private lateinit var vm: ViewModel
    private lateinit var currentTrack: Track

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_media)

        vm = ViewModelProvider(this, TrackViewModelFactory(this))[(TrackViewModel::class.java)]

        val trackNameMedia = findViewById<TextView>(id.track_name)
        val artistNameMedia = findViewById<TextView>(id.artist_name_media)
        val durationTrack = findViewById<TextView>(id.content_duration)
        val album = findViewById<TextView>(id.content_album)
        val year = findViewById<TextView>(id.content_year)
        val genre = findViewById<TextView>(id.content_genre)
        val country = findViewById<TextView>(id.content_country)
        val buttonBack = findViewById<ImageView>(id.back_main)
        val cover = findViewById<ImageView>(id.cover)
        val buttonPlay = findViewById<ImageView>(id.button_play)
        val timer = findViewById<TextView>(id.timer)
        cover.setImageResource(R.drawable.vector_placeholder_big)

        val activity = intent.getBooleanExtra(ACTIVITY, false)
        if (activity) {
            currentTrack =
                intent.getSerializableExtra(TRACK) as Track                               //получить трек из searchActivity

            artistNameMedia.text = currentTrack.artistName
            trackNameMedia.text = currentTrack.trackName
            year.text = currentTrack.releaseDate
            genre.text = currentTrack.primaryGenreName
            country.text = currentTrack.country
            durationTrack.text = simpleDateFormat(currentTrack.trackTimeMillis)
            timer.text = simpleDateFormat("0")
            if (currentTrack.collectionName.isNotEmpty()) album.text = currentTrack.collectionName

            Glide
                .with(applicationContext)
                .load(getCoverArtwork(currentTrack.artworkUrl100))
                .placeholder(R.drawable.vector_placeholder_big)
                .centerCrop()
                .transform(RoundedCorners(applicationContext.resources.getDimensionPixelSize(R.dimen.top_8)))
                .into(cover)

            (vm as TrackViewModel).preparePlayer(currentTrack)                                                //подготовить MediaPlayer

            (vm as TrackViewModel).playerLiveData.observe(this) { playButton ->          //управление воспроизведением
                if (playButton) buttonPlay.setImageResource(R.drawable.button_play)
                else buttonPlay.setImageResource(R.drawable.vector_pause)
            }

            (vm as TrackViewModel).timerLiveData.observe(this) { time ->                  //время воспроизведения
                timer.text = String.format("%02d : %02d", time / 60, time % 60)
            }
            /*mediaPlayers.stopPlayLiveData.observe(this) {
                Log.e("AAA", "stop player")
                (vm as TrackViewModel).stopTimer()
                (vm as TrackViewModel).playerStateChange(STATE_PREPARED)
                buttonPlay.setImageResource(R.drawable.button_play)
                timer.text = simpleDateFormat("0")
            }*/

            buttonPlay.setOnClickListener {
                (vm as TrackViewModel).playbackControl()
                (vm as TrackViewModel).startCreateTime()
            }
        }
        buttonBack.setOnClickListener {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        (vm as TrackViewModel).playerStateChange(STATE_PLAYING)
    }

    override fun onStop() {
        super.onStop()
        (vm as TrackViewModel).stopTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        (vm as TrackViewModel).playerStateChange(STATE_RELEASE)
    }
}