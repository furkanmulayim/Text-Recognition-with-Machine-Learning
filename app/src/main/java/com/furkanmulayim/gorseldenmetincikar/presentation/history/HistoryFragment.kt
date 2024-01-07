package com.furkanmulayim.gorseldenmetincikar.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkanmulayim.gorseldenmetincikar.ItemSwipeListener
import com.furkanmulayim.gorseldenmetincikar.R
import com.furkanmulayim.gorseldenmetincikar.SwipeToDeleteCallback
import com.furkanmulayim.gorseldenmetincikar.databinding.FragmentHistoryBinding
import java.sql.SQLOutput

class HistoryFragment : Fragment(), ItemSwipeListener {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewmodel: HistoryViewModel
    private lateinit var adapter:HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        viewmodel = ViewModelProvider(this)[HistoryViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.verileriYukle()
        adapter = HistoryAdapter(arrayListOf(),requireContext(),binding.rcycHistory,this)
        adapterAyarla()
        observeData()
        clickListeners()
    }

    private fun clickListeners() {
        binding.homeButton.setOnClickListener {
            navigate(R.id.action_historyFragment_to_helloFragment2)
        }
        binding
    }

    private fun adapterAyarla() {
        binding.rcycHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rcycHistory.adapter = adapter
        val swipeToDeleteCallback = SwipeToDeleteCallback(adapter,requireContext())
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.rcycHistory)
    }

    private fun observeData() {
        viewmodel.metinlist.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.updateList(it.reversed())
            }
        })
    }

    private fun navigate(action: Int) {
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun onItemSwiped(id: Int) {
        viewmodel.veriSil(id)
    }


}