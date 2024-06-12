package com.dicoding.myapplication1.view.forgot

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import com.dicoding.myapplication1.databinding.ActivityVerifBinding
import com.dicoding.myapplication1.helper.ViewModelFactory

class VerifActivity : AppCompatActivity() {
    private val viewModel by viewModels<VerifViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityVerifBinding
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = intent.getStringExtra("email") ?: ""

        viewModel.verif.observe(this) { result ->
            if (result?.error == true) {
            } else {
                navigateToReset(email,)
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
        binding.resetButton.setOnClickListener {
            val resetcode = binding.verifyEditText.text.toString()
            viewModel.verifycode( email , resetcode )
        }
    }

    private fun navigateToReset(email: String) {
        val intent = Intent(this, CreatePasswordActivity::class.java)
        intent.putExtra("email", email)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}