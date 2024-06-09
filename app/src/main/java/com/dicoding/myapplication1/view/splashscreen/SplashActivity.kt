package com.dicoding.myapplication1.view.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.myapplication1.R
import com.dicoding.myapplication1.databinding.ActivitySplashBinding
import com.dicoding.myapplication1.view.login.LoginActivity
import com.dicoding.myapplication1.view.onboarding.OnboardingActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            goToOnboardingActivity()
        }, 1000L)

        supportActionBar?.hide()
    }

    private fun goToOnboardingActivity(){
        val intent = Intent(this, OnboardingActivity::class.java)
        startActivity(intent)
        finish()
    }
}