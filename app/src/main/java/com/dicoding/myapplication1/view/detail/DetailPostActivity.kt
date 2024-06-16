package com.dicoding.myapplication1.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dicoding.myapplication1.R
import com.dicoding.myapplication1.data.response.DetailPostResponse
import com.dicoding.myapplication1.databinding.ActivityDetailPostBinding
import com.dicoding.myapplication1.helper.ViewModelFactory

class DetailPostActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailPostViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityDetailPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postId = intent.getStringExtra(EXTRA_POST_ID)
        if (postId != null) {
            viewModel.getDetailId(postId)
        }

        viewModel.detailpost.observe(this) {DetailPostResponse ->
            setDetailData(DetailPostResponse)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setDetailData(detailpostResponse: DetailPostResponse) {
        detailpostResponse.data?.let { data ->
            binding.tvName.text = data.username
            binding.tvtitle.text = data.title
            binding.tvDescription.text = data.description
            Glide.with(this)
                .load(data.uRLImage)
                .into(binding.ivDetail)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_POST_ID = "extra_post_id"
    }
}