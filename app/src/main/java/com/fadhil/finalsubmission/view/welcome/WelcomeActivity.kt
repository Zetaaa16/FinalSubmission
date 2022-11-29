package com.fadhil.finalsubmission.view.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.fadhil.finalsubmission.R
import com.fadhil.finalsubmission.databinding.ActivityWelcomeBinding
import com.fadhil.finalsubmission.storage.pref.PreferenceDataSource
import com.fadhil.finalsubmission.view.login.LoginActivity
import com.fadhil.finalsubmission.view.main.MainActivity
import com.fadhil.finalsubmission.view.register.RegisterActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class WelcomeActivity : AppCompatActivity() {
    private val prefHelper by lazy {
        PreferenceDataSource.invoke(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        hideSystemUI()
        val token = prefHelper.fetchAuthToken()
        lifecycleScope.launch {
            delay(1000)
            if (token.isNullOrEmpty()) {
                startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}