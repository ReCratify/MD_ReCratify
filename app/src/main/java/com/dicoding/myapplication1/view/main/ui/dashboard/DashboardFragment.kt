package com.dicoding.myapplication1.view.main.ui.dashboard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.myapplication1.R
import com.dicoding.myapplication1.databinding.FragmentDashboardBinding
import com.dicoding.myapplication1.di.getImageUri
import com.dicoding.myapplication1.di.reduceFileImage
import com.dicoding.myapplication1.di.uriToFile
import com.dicoding.myapplication1.helper.ViewModelFactory1

class DashboardFragment : Fragment() {
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory1.getInstance(requireActivity())
    }

    private lateinit var binding: FragmentDashboardBinding

    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Permission request granted")
            } else {
                showToast("Permission request denied")
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.analyzeButton.setOnClickListener { analyze() }

        viewModel.analyzeResult.observe(viewLifecycleOwner){ modelResponse ->
            modelResponse.label?.let { label ->
                handleLabelResult(label)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        return (binding.root)
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }


    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun analyze() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri,requireContext()).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")

            viewModel.analyzeImage(imageFile)
            showLoading(true)
        }?:showToast(getString(R.string.empty_image_warning))
    }

    private fun handleLabelResult(label: String) {
        val intent = Intent(requireContext(), ResultActivity::class.java)

        // Mengirim label dan URI gambar sebagai data ekstra
        intent.putExtra("label", label)
        intent.putExtra("imageUri", currentImageUri.toString())

        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}