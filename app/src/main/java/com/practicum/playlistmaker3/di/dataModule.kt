package com.practicum.playlistmaker3.di

import android.content.Context
import android.media.MediaPlayer
import androidx.room.Room
import com.google.gson.Gson
import com.practicum.playlistmaker3.mediaLibrary.data.db.AppDatabase
import com.practicum.playlistmaker3.player.data.SharedPrefs.SharedPrefs
import com.practicum.playlistmaker3.player.data.mediaPlayer.GetMediaPlayer
import com.practicum.playlistmaker3.player.data.mediaPlayer.MediaPlayers
import com.practicum.playlistmaker3.search.data.NetworkClient
import com.practicum.playlistmaker3.search.data.SharedPrefClient
import com.practicum.playlistmaker3.search.data.connecting.IsConnected
import com.practicum.playlistmaker3.search.data.network.Itunes
import com.practicum.playlistmaker3.search.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker3.search.data.sharedPreferences.SharedPreferencesHistory
import com.practicum.playlistmaker3.search.data.sharedPreferences.SharedPreferencesHistory.Companion.SAVE_TRACKS
import com.practicum.playlistmaker3.settings.data.SaveThemeNight
import com.practicum.playlistmaker3.sharing.data.ToShare
import com.practicum.playlistmaker3.sharing.data.UserAgreement
import com.practicum.playlistmaker3.sharing.data.WriteToSupport
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val HTTP_ITUNES = "http://itunes.apple.com"
val dataModule = module {

    //search

    single<Itunes> {
        Retrofit.Builder()
            .baseUrl(HTTP_ITUNES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Itunes::class.java)
    }
    single {
        androidContext().getSharedPreferences(SAVE_TRACKS, Context.MODE_PRIVATE)
    }
    factory {
        Gson()
    }
    single {
        IsConnected(get())
    }
    single<SharedPrefClient> {
        SharedPreferencesHistory(get(), get())
    }
    single<NetworkClient> {
        RetrofitNetworkClient(get(), get())
    }

    //mediaLibrary

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db").build()
    }

    //player

    factory {
        MediaPlayer()
    }
    factory<GetMediaPlayer> {
        MediaPlayers(get(), get(), get())
    }
    single {
        SharedPrefs(get(), get())
    }

    //setting

    single {
        SaveThemeNight(get())
    }

    //sharing

    single {
        ToShare(get())
    }
    single {
        UserAgreement(get())
    }
    single {
        WriteToSupport(get())
    }
}
