package com.dicoding.myapplication1.view.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.dicoding.myapplication1.data.response.VideosItem
import com.dicoding.myapplication1.databinding.ItemYoutubeBinding

@GlideModule
class YoutubeAdapter : ListAdapter<VideosItem, YoutubeAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder (val binding: ItemYoutubeBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(youtube: VideosItem) {
            binding.title.text = youtube.title
            Glide.with(binding.root.context)
                .load(youtube.uRLThumbnail)
                .into(binding.ivItem)

            binding.root.setOnClickListener {
                val videoUrl = youtube.uRLVideo
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemYoutubeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val youtube = getItem(position)
        holder.bind(youtube)
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VideosItem>() {
            override fun areItemsTheSame(oldItem: VideosItem, newItem: VideosItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: VideosItem, newItem: VideosItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}