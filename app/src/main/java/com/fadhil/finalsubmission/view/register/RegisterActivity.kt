package com.fadhil.finalsubmission.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import com.fadhil.finalsubmission.data.Result
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.fadhil.finalsubmission.databinding.ActivityRegisterBinding
import com.fadhil.finalsubmission.utils.ViewModelFactory
import com.fadhil.finalsubmission.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClick()
        playAnimation()

        binding.apply {
            signupButton.setOnClickListener {
                val name = nameEditText.text.toString()
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                viewModel.register(name, email, password).observe(this@RegisterActivity) { result ->
                    when (result) {
                        is Result.Loading -> {
                            binding.signupButton.isEnabled = false
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            binding.signupButton.isEnabled = true

                                AlertDialog.Builder(this@RegisterActivity).apply {
                                    setTitle("Yeah!")
                                    setMessage("Akunnya sudah jadi nih. Yuk, Kita Login")
                                    setPositiveButton("Lanjut") { _, _ ->
                                        finish()
                                    }
                                    create()
                                    show()
                                }

                        }
                        is Result.Error -> {
                            binding.signupButton.isEnabled = true
                            showLoading(false)

                            message(result.error)
                        }
                    }
                }



            }
            login.setOnClickListener {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }





    private fun message(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setOnClick() = with(binding) {


    }

    private fun playAnimation(){
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)
        val already = ObjectAnimator.ofFloat(binding.textalready, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.login, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(already,login)
        }

        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup,
                together

            )
            startDelay = 500
        }.start()
    }
    private fun showLoading(isLoading: Boolean){
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.bgDim.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}