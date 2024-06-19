package com.dicoding.myapplication1.view.main.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AppCompatActivity
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide the ActionBar
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvPost.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvPost.addItemDecoration(itemDecoration)

        viewModel.getAllPost()

        viewModel.allpost.observe(viewLifecycleOwner) { postResponse ->
            setPostData(postResponse)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding.fabAddPost.setOnClickListener {
            val intent = Intent(requireContext(), AddActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setPostData(postResponse: PostResponse) {
        val consumerStory = postResponse.data
        val adapter = PostAdapter()
        adapter.submitList(consumerStory)
        binding.rvPost.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Show the ActionBar again when the Fragment is destroyed
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}
