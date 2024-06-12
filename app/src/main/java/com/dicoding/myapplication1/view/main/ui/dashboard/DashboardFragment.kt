package com.dicoding.myapplication1.view.main.ui.dashboard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.myapplication1.databinding.FragmentDashboardBinding
import com.dicoding.myapplication1.di.getImageUri
import com.dicoding.myapplication1.helper.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    private var currentImageUri: Uri? = null

    private var predictionScore: String = ""

    private var predictionResult: String = ""

    private lateinit var imageClassifierHelper: ImageClassifierHelper

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
        binding.analyzeButton.setOnClickListener { analyzeImage() }

        imageClassifierHelper = ImageClassifierHelper(context = requireContext(), classifierListener = object : ImageClassifierHelper.ClassifierListener {
            override fun onError(error: String) {
                showToast(error)
            }

            override fun onResults(results: List<Classifications>?, confidence: String) {
                generateResult(results)
            }
        })
        return (binding.root)
    }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) {uri: Uri? ->
        uri?.let {
            currentImageUri = uri
            showImage()
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherCamera.launch(currentImageUri)
    }

    private val launcherCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
        currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
        if (currentImageUri != null){
            currentImageUri?.let {
                imageClassifierHelper.classifyStaticImage(it)
            }
        } else {
            showToast("Image Classifier not Initialized")
        }
    }

    private fun moveToResult() {
        val intent = Intent(requireContext(), ResultActivity::class.java).apply {
            putExtra(ResultActivity.EXTRA_IMAGE_URI, currentImageUri.toString())
            putExtra(ResultActivity.EXTRA_PREDICTION_RESULT, predictionResult)
            putExtra(ResultActivity.EXTRA_PREDICTION_SCORE, predictionScore)
        }
        startActivity(intent)
    }

    private fun generateResult(data: List<Classifications>?) {
        data?.let { it ->
            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                val highestResult = it[0].categories.maxBy { it?.score ?: 0.0f }
                predictionResult = highestResult.label
                predictionScore = NumberFormat.getPercentInstance().format(highestResult.score)

                moveToResult()
            }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}