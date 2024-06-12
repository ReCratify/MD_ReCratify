package com.dicoding.myapplication1.view.main.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.dicoding.myapplication1.R
import com.dicoding.myapplication1.databinding.FragmentHomeBinding
import com.dicoding.myapplication1.helper.ViewModelFactory
import com.dicoding.myapplication1.view.splashscreen.SplashActivity

class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return (binding.root)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                AlertDialog.Builder(requireContext())
                    .setMessage("Apakah anda yakin ingin keluar")
                    .setPositiveButton("Iya") {dialog, _ ->
                        viewModel.logout()
                        startActivity(Intent(requireActivity(), SplashActivity::class.java))
                        requireActivity().finish()
                        dialog.dismiss()
                    }
                    .setNegativeButton("Tidak") {dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}