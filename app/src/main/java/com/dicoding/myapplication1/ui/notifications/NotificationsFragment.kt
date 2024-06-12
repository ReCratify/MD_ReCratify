package com.dicoding.myapplication1.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.myapplication1.data.response.PostResponse
import com.dicoding.myapplication1.databinding.FragmentNotificationsBinding
import com.dicoding.myapplication1.helper.ViewModelFactory
import com.dicoding.myapplication1.view.adapter.PostAdapter
import com.dicoding.myapplication1.view.add.AddActivity

class NotificationsFragment : Fragment() {
    private val viewModel by viewModels<NotificationsViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvPost.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvPost.addItemDecoration(itemDecoration)

        viewModel.getAllPost()

        viewModel.allpost.observe(viewLifecycleOwner) { postResponse ->
            setStoryData(postResponse)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding.fabAddPost.setOnClickListener {
            val intent = Intent(requireContext(), AddActivity::class.java)
            startActivity(intent)
        }
        return (binding.root)
    }

    private fun setStoryData(postResponse: PostResponse) {
        val consumerStory = postResponse.data
        val adapater = PostAdapter()
        adapater.submitList(consumerStory)
        binding.rvPost.adapter = adapater
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}