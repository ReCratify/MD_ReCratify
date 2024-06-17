package com.dicoding.myapplication1.view.main.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.myapplication1.data.response.YoutubeResponse
import com.dicoding.myapplication1.databinding.ActivityResultBinding
import com.dicoding.myapplication1.helper.ViewModelFactory
import com.dicoding.myapplication1.view.adapter.YoutubeAdapter

class ResultActivity : AppCompatActivity() {
    private val viewModel by viewModels<YoutubeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvPost.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvPost.addItemDecoration(itemDecoration)

        val label = intent.getStringExtra("label")

        if (label != null ) {
            Log.d("ResultActivity", "Label: $label")
            viewModel.getAllVideos(label)
        } else {
            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show()
            finish()
        }

        viewModel.allvideos.observe(this) { youtubeResponse ->
            setyoutubeData(youtubeResponse)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setyoutubeData(youtubeResponse: YoutubeResponse) {
        val consumerStory = youtubeResponse.videos
        val adapater = YoutubeAdapter()
        adapater.submitList(consumerStory)
        binding.rvPost.adapter = adapater
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}