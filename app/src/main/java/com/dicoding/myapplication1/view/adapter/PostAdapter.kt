package com.dicoding.myapplication1.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.myapplication1.data.response.DataItem
import com.dicoding.myapplication1.databinding.ItemPostBinding

class PostAdapter : ListAdapter<DataItem, PostAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder (val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(post: DataItem) {
            binding.tvname.text = post.username
            binding.tvtitle.text = post.title
            binding.tvdescription.text = post.description
            Glide.with(binding.root.context)
                .load(post.uRLImage)
                .into(binding.ivItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}