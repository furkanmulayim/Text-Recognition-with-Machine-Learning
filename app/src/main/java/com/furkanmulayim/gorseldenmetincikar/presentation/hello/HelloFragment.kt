package com.furkanmulayim.gorseldenmetincikar.presentation.hello

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.furkanmulayim.gorseldenmetincikar.R
import com.furkanmulayim.gorseldenmetincikar.databinding.FragmentHelloBinding
import com.furkanmulayim.gorseldenmetincikar.utils.showMessage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class HelloFragment : Fragment() {

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
        private const val GALLERY_PERMISSION_REQUEST_CODE = 101
        private const val CROP_IMAGE_REQUEST_CODE = 1001
    }

    private lateinit var cameraActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryActivityResultLauncher: ActivityResultLauncher<Intent>

    private var imageUri: Uri? = null

    private lateinit var binding: FragmentHelloBinding
    private lateinit var viewModel: HelloViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hello, container, false)
        viewModel = ViewModelProvider(this)[HelloViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        cameraResultListener()
        galleryResultListener()
        observResponse()
        clickListeners()
    }

    private fun clickListeners() {
        binding.historyButton.setOnClickListener {
            navigate(R.id.action_helloFragment2_to_historyFragment)
        }

        binding.cekButton.setOnClickListener {
            pickImageCamera()
        }

        binding.yukleButton.setOnClickListener {
            pickImageGallery()
        }

        binding.aiButton.setOnClickListener {
            viewModel.checkInternetConnection()
        }
    }

    private fun observResponse() {

        viewModel.isInternetAvailable.observe(viewLifecycleOwner, Observer { connect ->
            if (connect == true) {
                //eğer bağlantı varsa
                viewModel.baglantiVar(requireView())
            } else {
                //eğer bağlantı yoksa
                requireActivity().showMessage("İnternet Bağlantısı Yok İnternete Bağlanarak Yeniden Deneyin...")
            }

        })
    }

    private fun navigate(action: Int) {
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun imageUriKaydet(imageUri: String) {
        viewModel.gorselUriKaydet(imageUri)
    }

    private fun message(message: String) {
        requireContext().showMessage(message)
    }


    private fun requestPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                requireContext(), permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val rationale = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), permission
            )

            val requestPermission = ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(permission), requestCode
            )

            if (rationale) {
                requestPermission
            } else {
                requestPermission
            }
        }
    }


    private fun cameraResultListener() {
        cameraActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                imageUriKaydet(imageUri.toString())
                goingForCrop()
            } else {
                message(getString(R.string.cekilmedi))
            }
        }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        galleryActivityResultLauncher.launch(intent)
    }

    private fun galleryResultListener() {
        galleryActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = result.data
                    if (data != null) {
                        imageUri = data.data
                        imageUriKaydet(imageUri.toString())
                        goingForCrop()
                    }
                } else {
                    message(getString(R.string.secilmedi))
                }
            }
    }

    private fun requestPermissionCamera() {
        requestPermission(
            Manifest.permission.CAMERA, CAMERA_PERMISSION_REQUEST_CODE
        )
    }


    private fun pickImageCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "Sample Title")
            values.put(MediaStore.Images.Media.DESCRIPTION, "Sample Description")
            imageUri = activity?.contentResolver?.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            cameraActivityResultLauncher.launch(intent)
        } else {
            requestPermissionCamera()
            message(getString(R.string.izin))
        }
    }


    private fun goingForCrop() {
        val cropImageIntent =
            CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true).getIntent(requireContext())
        startActivityForResult(cropImageIntent, CROP_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CROP_IMAGE_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val croppedUri = result.uri
                imageUriKaydet(croppedUri.toString())
                navigate(R.id.action_helloFragment2_to_recognitionFragment)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                requireActivity().showMessage("Resim Kırpılamadı Tekrar Deneyin! " + error.localizedMessage)
            }
        }
    }


}
