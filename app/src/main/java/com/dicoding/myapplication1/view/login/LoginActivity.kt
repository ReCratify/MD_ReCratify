package com.dicoding.myapplication1.view.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dicoding.myapplication1.data.pref.UserModel
import com.dicoding.myapplication1.data.response.LoginResponse
import com.dicoding.myapplication1.databinding.ActivityLoginBinding
import com.dicoding.myapplication1.helper.ViewModelFactory
import com.dicoding.myapplication1.view.forgot.ForgotPasswordActivity
import com.dicoding.myapplication1.view.main.MainActivity
import com.dicoding.myapplication1.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

        viewModel.loginResult.observe(this) {loginResponse ->
            handleLoginResult(loginResponse)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }
    private fun setupView() {
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

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.login(email, password)
        }
        binding.daftarTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.forgotpasswordTextView.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleLoginResult(loginResponse: LoginResponse) {
        if (loginResponse.error == false) {
            val token = loginResponse.loginResult?.token ?: ""
            val username = loginResponse.loginResult?.username?: ""
            val userModel = UserModel(email = String(), token, username)
            viewModel.saveSession(userModel)
            AlertDialog.Builder(this).apply {
                setTitle("Yeah!")
                setMessage("Success")
                setPositiveButton("Lanjut") { _, _ ->
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
        } else {
            val errorMessage = loginResponse.message ?: "Unknown Error"
            AlertDialog.Builder(this).apply {
                setTitle("Login gagal")
                setMessage(errorMessage)
                setPositiveButton("OK", null)
                create()
                show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}