package com.fadhil.finalsubmission.view.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.fadhil.finalsubmission.R
import com.fadhil.finalsubmission.databinding.ActivitySplashBinding
import com.fadhil.finalsubmission.databinding.ActivityWelcomeBinding
import com.fadhil.finalsubmission.storage.pref.PreferenceDataSource
import com.fadhil.finalsubmission.view.main.MainActivity
import com.fadhil.finalsubmission.view.welcome.WelcomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val pref by lazy {
        PreferenceDataSource.invoke(this)
    }
    private lateinit var binding: ActivitySplashBinding
    private val delay = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemUI()
        playAnimation()
        val token = pref.fetchAuthToken()
        Handler(Looper.getMainLooper()).postDelayed({
            if (token.isNullOrEmpty()) {
                startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
            finish()
        }, delay)
    }

    private fun hideSystemUI() {

        supportActionBar?.hide()
    }

    private fun playAnimation(){
        val title = ObjectAnimator.ofFloat(binding.imgView, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                title

            )
            startDelay = 200
        }.start()
    }
}