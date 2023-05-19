package com.practicum.playlistmaker3

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker3.R.id
import java.text.SimpleDateFormat
import java.util.*

class MediaActivity() : AppCompatActivity() {

    lateinit var currentTrack: Track

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        val cover = findViewById<ImageView>(id.cover)
        val trackNameMedia = findViewById<TextView>(id.track_name)
        val artistNameMedia = findViewById<TextView>(id.artist_name_media)
        val duration = findViewById<TextView>(id.content_duration)
        val album = findViewById<TextView>(id.content_album)
        val year = findViewById<TextView>(id.content_year)
        val genre = findViewById<TextView>(id.content_genre)
        val country = findViewById<TextView>(id.content_country)
        val buttonBack = findViewById<ImageView>(id.back_main)
        val timer = findViewById<TextView>(id.timer)

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
            timer.text = simpleDateFormat(currentTrack.trackTimeMillis)
            if (currentTrack.collectionName.isNotEmpty()) album.text = currentTrack.collectionName
        }
    }

    private fun getCoverArtwork(art: String): String {
        return art.replaceAfterLast('/', "512x512bb.jpg")
    }

    private fun simpleDateFormat(time: String): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(293000L)
            .format(time)
    }
}