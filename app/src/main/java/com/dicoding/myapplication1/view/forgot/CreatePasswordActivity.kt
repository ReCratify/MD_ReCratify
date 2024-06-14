package com.dicoding.myapplication1.view.forgot

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.myapplication1.R
import com.dicoding.myapplication1.databinding.ActivityCreatePasswordBinding
import com.dicoding.myapplication1.helper.ViewModelFactory
import com.dicoding.myapplication1.view.login.LoginActivity
const val MIN_PASSWORD_LENGTH = 8

class CreatePasswordActivity : AppCompatActivity() {
    private val viewModel by viewModels<CreatePasswordViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityCreatePasswordBinding
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = intent.getStringExtra("email") ?: ""

        viewModel.create.observe(this) { result ->
            if (result?.error == true) {
                Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
            } else {
                navigateToLogin()
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        setupView()
        setupAction()
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
        binding.saveButton.setOnClickListener {
            val newpassword = binding.newEditText.text.toString()
            val resetcode = binding.confrimEditText.text.toString()
            if (isPasswordValid(newpassword) && isResetCodeValid(resetcode)) {
                viewModel.resetpassword(email, newpassword, resetcode)
            } else {
                Toast.makeText(this, "Reset code atau password baru tidak valid", Toast.LENGTH_SHORT).show()
            }
        }
        binding.newPasswordBtn.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isResetCodeValid(resetcode: String): Boolean {
        return resetcode.isNotBlank()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH
    }


    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}