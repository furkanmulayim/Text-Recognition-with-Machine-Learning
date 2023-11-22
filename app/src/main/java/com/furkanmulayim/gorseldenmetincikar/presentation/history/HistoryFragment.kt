package com.furkanmulayim.gorseldenmetincikar.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.furkanmulayim.gorseldenmetincikar.R
import com.furkanmulayim.gorseldenmetincikar.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewmodel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        viewmodel = ViewModelProvider(this)[HistoryViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListeners()
    }

    private fun clickListeners() {
        binding.homeButton.setOnClickListener {
            navigate(R.id.action_historyFragment_to_helloFragment2)
        }
    }

    private fun navigate(action: Int) {
        Navigation.findNavController(requireView()).navigate(action)
    }


}