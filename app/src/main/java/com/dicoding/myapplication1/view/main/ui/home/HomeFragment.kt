package com.dicoding.myapplication1.view.main.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.myapplication1.R
import com.dicoding.myapplication1.databinding.FragmentHomeBinding
import com.dicoding.myapplication1.helper.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var binding: FragmentHomeBinding
    private lateinit var timeTv: TextView
    private val handler = Handler(Looper.getMainLooper())
    private val timeFormat = SimpleDateFormat("HH mm", Locale.getDefault())

    private val updateTimeTask = object : Runnable {
        override fun run() {
            val currentTime = timeFormat.format(Date())
            timeTv.text = currentTime
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        timeTv = binding.root.findViewById(R.id.currentTime)
        return (binding.root)
    }

    override fun onResume() {
        super.onResume()
        handler.post(updateTimeTask)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updateTimeTask)
    }
}