package com.dicoding.myapplication1.view.main.ui.profil

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.myapplication1.R
import com.dicoding.myapplication1.data.response.PostUserResponse
import com.dicoding.myapplication1.databinding.FragmentProfilBinding
import com.dicoding.myapplication1.helper.ViewModelFactory
import com.dicoding.myapplication1.view.adapter.UserAdapter
import com.dicoding.myapplication1.view.splashscreen.SplashActivity

class ProfilFragment : Fragment() {

    private val viewModel by viewModels<ProfilViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var binding: FragmentProfilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfilBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvPost.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvPost.addItemDecoration(itemDecoration)

        viewModel.getUserPost()

        viewModel.userpost.observe(viewLifecycleOwner) {  postUserResponse->
            setUserData(postUserResponse )
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.username.observe(viewLifecycleOwner) { username ->
            binding.tvuser.text = username
        }

        return (binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setUserData(postUserResponse: PostUserResponse) {
        val consumerUser = postUserResponse.data
        val adapater = UserAdapter()
        adapater.submitList(consumerUser)
        binding.rvPost.adapter = adapater
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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