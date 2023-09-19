package com.practicum.playlistmaker3.player.ui.viewActivity


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.R.id
import com.practicum.playlistmaker3.R.layout
import com.practicum.playlistmaker3.player.ui.viewModelMediaPlayer.TrackViewModel
import com.practicum.playlistmaker3.search.domain.models.Track
import com.practicum.playlistmaker3.search.domain.models.getCoverArtwork
import com.practicum.playlistmaker3.util.simpleDateFormat
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("CAST_NEVER_SUCCEEDS")
class MediaActivity : AppCompatActivity() {

    private val viewModel by viewModel<TrackViewModel>()

    private lateinit var currentTrack: Track

    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_media)

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
        val buttonIsFavorite = findViewById<ImageView>(id.button_Is_favorite)
        cover.setImageResource(R.drawable.vector_placeholder_big)

        currentTrack =
            intent.getSerializableExtra(TRACK) as Track                                                                   //получить трек из searchActivity

        viewModel.preparePlayer(currentTrack)                                                                            //подготовить MediaPlayer

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

        viewModel.favoriteTrackListId(currentTrack.trackId)

        viewModel.isFavoriteLiveData.observe(this) { screen ->
            if (screen.isFavorite) {
                buttonIsFavorite.setImageResource(R.drawable.button_favorite_on)
                currentTrack.isFavorite = true
            } else buttonIsFavorite.setImageResource(R.drawable.button_favorite_off)
        }

        viewModel.mediaLiveData.observe(this) { screen ->
            if (screen.playButton) buttonPlay.setImageResource(R.drawable.button_play)                   //управление воспроизведением
            else buttonPlay.setImageResource(R.drawable.vector_pause)

            timer.text = String.format(
                "%02d:%02d",
                (screen.time / THOUSAND_L) / 60,
                (screen.time / THOUSAND_L) % 60
            )                                                                                                            //время воспроизведения
        }

        buttonPlay.setOnClickListener {
            viewModel.playbackControl()
            viewModel.startCreateTime()
        }

        buttonIsFavorite.setOnClickListener {
            if (currentTrack.isFavorite) {
                buttonIsFavorite.setImageResource(R.drawable.button_favorite_off)
                currentTrack.isFavorite = false
            } else {
                buttonIsFavorite.setImageResource(R.drawable.button_favorite_on)
                currentTrack.isFavorite = true
            }
            viewModel.onFavoriteClicked(currentTrack)
        }


        buttonBack.setOnClickListener {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.playerStateChange(STATE_PLAYING)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.playerStateChange(STATE_RELEASE)
    }

    companion object {
        private const val THOUSAND_L = 1000L
        const val TRACK = "track"
       private const val STATE_PLAYING = 2
        private const val STATE_RELEASE = 4
    }
}