package com.dicoding.myapplication1.view.detail

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.myapplication1.R
import com.dicoding.myapplication1.databinding.ActivityDetailYoutubeBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

class DetailYoutubeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailYoutubeBinding

    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoUrl = intent.getStringExtra("VIDEO_URL")
        initializePlayer(videoUrl)
    }

    private fun initializePlayer(videoUrl: String?) {
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player

        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()
    }

    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
    }
}