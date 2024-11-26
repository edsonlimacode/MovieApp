package com.edsonlima.flixapp.presenter.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.edsonlima.flixapp.R
import com.edsonlima.flixapp.presenter.main.MainActivity
import com.edsonlima.flixapp.utils.EXTRA_START_NAV_RES_ID
import com.edsonlima.flixapp.utils.FirebaseHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()

        if (FirebaseHelper.isAuthenticated()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2)!!.findNavController()

        intent.extras?.getInt(EXTRA_START_NAV_RES_ID)?.let {
            navHostFragment.navigate(it)
        }
    }
}

