package com.edsonlima.flixapp.presenter.main

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        //installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        initBottomNavigation()
    }

    private fun initBottomNavigation() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcvMain) as NavHostFragment

        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bnvMain, navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.bnvMain.isVisible = destination.id == R.id.menuHome ||
                    destination.id == R.id.menuSearch ||
                    destination.id == R.id.menuFavorite ||
                    destination.id == R.id.menuDownload ||
                    destination.id == R.id.menuProfile
        }
    }

}