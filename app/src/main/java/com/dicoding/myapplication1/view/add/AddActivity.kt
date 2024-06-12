package com.dicoding.myapplication1.view.add

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.dicoding.myapplication1.R
import com.dicoding.myapplication1.databinding.ActivityAddBinding
import com.dicoding.myapplication1.di.getImageUri
import com.dicoding.myapplication1.di.reduceFileImage
import com.dicoding.myapplication1.di.uriToFile
import com.dicoding.myapplication1.helper.ViewModelFactory

class AddActivity : AppCompatActivity() {
    private val viewModel by viewModels<AddViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityAddBinding

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.uploadButton.setOnClickListener {uploadImage()}
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
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri,this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val title = binding.EditTexttitle.text.toString().trim()
            if (title.isEmpty()) {
                showToast(getString(R.string.empty_text_warning))
                return
            }
            val description = binding.EditTextdescription.text.toString().trim()
            if (description.isEmpty()) {
                showToast(getString(R.string.empty_text_warning))
                return
            }

            showLoading(true)
            viewModel.uploadImage(imageFile, title, description)
        }?:showToast(getString(R.string.empty_image_warning))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }
}