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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.furkanmulayim.gorseldenmetincikar.R
import com.furkanmulayim.gorseldenmetincikar.databinding.FragmentHelloBinding
import com.furkanmulayim.gorseldenmetincikar.utils.showMessage

@Suppress("UNUSED_EXPRESSION")
class HelloFragment : Fragment() {


    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
        private const val GALLERY_PERMISSION_REQUEST_CODE = 101
    }

    private lateinit var cameraActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryActivityResultLauncher: ActivityResultLauncher<Intent>

    private var imageUri: Uri? = null

    private lateinit var binding: FragmentHelloBinding
    private lateinit var viewModel: HelloViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hello, container, false)
        viewModel = ViewModelProvider(this)[HelloViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        cameraResultListener()
        galleryResultListener()
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



    private fun cameraResultListener() {
        cameraActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                imageUriKaydet(imageUri.toString())

                navigate(R.id.action_helloFragment2_to_cropFragment)
            } else {
                message(getString(R.string.cekilmedi))
            }
        }
    }


    private fun requestPermissionCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val izin = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), Manifest.permission.CAMERA
            )

            val izinGoster = ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
            // Eğer izin daha önce reddedildiyse
            if (izin) {
                // Daha sonra izni tekrar istiyoruz
                izinGoster
            } else {
                izinGoster
            }
        }
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

    private fun galleryResultListener() {
        galleryActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = result.data
                    if (data != null) {
                        imageUri = data.data
                        imageUriKaydet(imageUri.toString())
                        navigate(R.id.action_helloFragment2_to_cropFragment)
                    }
                } else {
                    message(getString(R.string.secilmedi))
                }
            }
    }

    private fun requestPermissionGallery() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val izin = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE
            )

            val izinGoster = ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                GALLERY_PERMISSION_REQUEST_CODE
            )
            // Eğer izin daha önce reddedildiyse
            if (izin) {
                // Daha sonra izni tekrar istiyoruz
                izinGoster
            } else {
                // İzin daha önce hiç istenmediyse
                izinGoster
            }
        }
    }


    private fun pickImageGallery() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            galleryActivityResultLauncher.launch(intent)
        } else {
            requestPermissionGallery()
            message(getString(R.string.izin))
        }
    }


}