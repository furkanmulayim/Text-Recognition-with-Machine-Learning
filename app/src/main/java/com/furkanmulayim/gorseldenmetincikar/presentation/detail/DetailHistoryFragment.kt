package com.furkanmulayim.gorseldenmetincikar.presentation.detail

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.furkanmulayim.gorseldenmetincikar.R
import com.furkanmulayim.gorseldenmetincikar.databinding.FragmentDetailHistoryBinding
import com.furkanmulayim.gorseldenmetincikar.utils.showMessage

class DetailHistoryFragment : Fragment() {

    private lateinit var binding: FragmentDetailHistoryBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_history, container, false)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        bundleVeriEsitle()
        observe()
        clickListener()
    }

    private fun bundleVeriEsitle() {
        val bundle: DetailHistoryFragmentArgs by navArgs()
        viewModel.metinId.value = bundle.metinId
        viewModel.veriGetir()
    }

    private fun clickListener() {
        binding.copyButton.setOnClickListener {
            copyText()
        }

        binding.silButton.setOnClickListener {
            viewModel.metinId.value?.let { it1 -> viewModel.veriSil(it1,requireView()) }
        }

        binding.backButton.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_detailHistoryFragment_to_historyFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observe() {
        viewModel.metin.observe(viewLifecycleOwner) {
            it?.let { me ->
                binding.tarih.text = "${me.gun} ${me.ay} ${me.hafta} ${me.saat}"
                binding.metin.text = me.metin
            }
        }
    }

    private fun copyText() {
        viewModel.metin.value?.metin.let {
            if (!it.isNullOrEmpty()) {
                val clipboardManager =
                    activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", it)
                clipboardManager.setPrimaryClip(clipData)
                requireActivity().showMessage("Metin Panoya Kopyalandı 📝")
            }
        }
    }
}