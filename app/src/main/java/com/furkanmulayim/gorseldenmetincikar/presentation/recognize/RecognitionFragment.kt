package com.furkanmulayim.gorseldenmetincikar.presentation.recognize

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.furkanmulayim.gorseldenmetincikar.R
import com.furkanmulayim.gorseldenmetincikar.databinding.FragmentRecognitionBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import jp.wasabeef.blurry.Blurry

class RecognitionFragment : Fragment() {

    private lateinit var binding: FragmentRecognitionBinding
    private lateinit var viewModel: RecognitionViewModel
    private lateinit var textRecognizer: TextRecognizer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recognition, container, false)
        viewModel = ViewModelProvider(this)[RecognitionViewModel::class.java]
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getImageUri()
        recognizeTextFromImage(viewModel.parsUrl())
    }


    private fun getImageUri() {
        var imageUri = viewModel.parsUrl()
        bulanikYap(imageUri,binding.buttonBackground)
    }

    fun bulanikYap(url: Uri, imageView: ImageView) {
        val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, url)
        // Blurry kütüphanesi kullanarak bulanıklaştırma işlemi
        Blurry.with(requireContext()).radius(10).sampling(4).from(bitmap).into(imageView)

    }

    private fun recognizeTextFromImage(resim: Uri) {
        try {
            val inputImage = resim.let { InputImage.fromFilePath(requireContext(), it) }
            val textTaskResult =
                inputImage.let { textRecognizer.process(it) }.addOnSuccessListener { _text ->
                    binding.recognizedTextEt.setText(_text.text)
                    //veriyi kullan burda
                }.addOnFailureListener { ex ->
                    //showToast("Lütfen Tekrar Deneyin.. !")
                }
        } catch (e: java.lang.Exception) {
            //showToast("Bir Sorun Çıktı.. !")
        }
    }




}