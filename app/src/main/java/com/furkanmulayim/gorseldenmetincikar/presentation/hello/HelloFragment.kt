package com.furkanmulayim.gorseldenmetincikar.presentation.hello

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.furkanmulayim.gorseldenmetincikar.R
import com.furkanmulayim.gorseldenmetincikar.databinding.FragmentHelloBinding

class HelloFragment : Fragment() {

    private lateinit var binding: FragmentHelloBinding
    private lateinit var viewModel: HelloViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hello, container, false)
        viewModel = ViewModelProvider(this)[HelloViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListeners()
    }

    private fun clickListeners() {
        binding.historyButton.setOnClickListener {
            navigate(R.id.action_helloFragment2_to_historyFragment)
        }
    }

    private fun navigate(action: Int) {
        Navigation.findNavController(requireView()).navigate(action)
    }


}