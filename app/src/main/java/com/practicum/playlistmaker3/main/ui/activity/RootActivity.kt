package com.practicum.playlistmaker3.main.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.practicum.playlistmaker3.R
import com.practicum.playlistmaker3.databinding.ActivityRootBinding
import com.practicum.playlistmaker3.main.ui.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RootActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private var _binding: ActivityRootBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.setTheme()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val navigationViewBottom = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navigationViewBottom.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.playListFragment -> navigationViewBottom.visibility = View.GONE
                R.id.currentPlaylistFragment -> navigationViewBottom.visibility = View.GONE
                R.id.editablePlaylistFragment -> navigationViewBottom.visibility = View.GONE
                else -> navigationViewBottom.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}