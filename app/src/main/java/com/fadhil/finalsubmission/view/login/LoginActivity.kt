package com.fadhil.finalsubmission.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.fadhil.finalsubmission.data.Result
import android.widget.Toast
import androidx.activity.viewModels
import com.fadhil.finalsubmission.R
import com.fadhil.finalsubmission.databinding.ActivityLoginBinding

import com.fadhil.finalsubmission.storage.pref.PreferenceDataSource
import com.fadhil.finalsubmission.utils.ViewModelFactory
import com.fadhil.finalsubmission.view.main.MainActivity
import com.fadhil.finalsubmission.view.register.RegisterActivity


class LoginActivity : AppCompatActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
       ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val prefHelper by lazy {
        PreferenceDataSource.invoke(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClick()
        playAnimation()
       // setAnimation()
    }



    private fun login(email: String, password: String) {
        viewModel.login(email, password).observe(this) { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                   // UtilsUi.closeDialog()
                    binding.loginButton.isEnabled = true
                    result.data.let {
                        if (!it.error) {
                            prefHelper.saveAuthToken(it.loginResult.token)
                            message(it.message)
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            message(it.message)
                        }
                    }
                }
                is Result.Error -> {
                   // UtilsUi.closeDialog()
                   // binding.btnLogin.isEnabled = true
                    message(result.error)
                }
            }

        }
    }

    private fun message(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setOnClick() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            login(email, password)
        }

        binding.register.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun playAnimation(){

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val register= ObjectAnimator.ofFloat(binding.register,View.ALPHA,1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title,message,emailTextView,emailEditTextLayout,passwordTextView,passwordEditTextLayout,login,register)
            startDelay = 500
        }.start()
    }

}