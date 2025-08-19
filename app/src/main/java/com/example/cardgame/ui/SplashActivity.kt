package com.example.cardgame.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cardgame.R
import com.example.cardgame.databinding.StartupSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: StartupSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StartupSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the fade-in animation
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.textViewJudgement.startAnimation(fadeIn)

        // Use a Handler to delay the transition to the main screen
        lifecycleScope.launch {
            // This is the modern replacement for Handler().postDelayed()
            delay(3000) // 3000ms delay

            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}