package com.furkanmulayim.gorseldenmetincikar.presentation.story

import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkanmulayim.gorseldenmetincikar.R
import com.furkanmulayim.gorseldenmetincikar.databinding.FragmentStoryBinding
import com.furkanmulayim.gorseldenmetincikar.model.Message
import com.furkanmulayim.gorseldenmetincikar.utils.hideKeyboard
import com.furkanmulayim.gorseldenmetincikar.utils.showMessage


class StoryFragment : Fragment() {
    private lateinit var binding: FragmentStoryBinding
    private lateinit var vm: StoryViewModel
    private lateinit var storyAdapter: StoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_story, container, false)
        vm = ViewModelProvider(this)[StoryViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = vm
        clickListener()
        setAdapter()
    }

    private fun setAdapter() {
        storyAdapter = StoryAdapter(arrayListOf(), requireContext())
        binding.storyRcyc.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.storyRcyc.adapter = storyAdapter
    }

    private fun clickListener() {
        binding.sorButton.setOnClickListener {
            val metin:String = girilenMetinKontrol()
            if (metin.isNotEmpty()) {
                sendMessageSetScreen(metin)
                //vm.messageList.add(Message("afaefwe",true))
                //vm.messageList.add(Message("saffaısjfsoıjfoısjefısejfosıejofısjeoıjfsoıejsıofjsıeffazf",false))
                //storyAdapter.updateList(vm.messageList)

            } else {
                requireActivity().showMessage("Lütfen Bir Metin Giriniz 🥲")
            }
        }

        binding.backButton.setOnClickListener {
            setNavigation(R.id.action_storyFragment_to_helloFragment2)
        }

        binding.girilenMetin.setOnLongClickListener {
            binding.girilenMetin.setText("")
            binding.girilenMetin.setText(getClipboardText())
            true
        }
    }

    private fun girilenMetinKontrol(): String {
        return binding.girilenMetin.text.toString().trim()
            .replace("\n", "")
            .replace("\"", "").take(1000)
    }

    private fun getClipboardText(): CharSequence {
        // Bir ClipboardManager nesnesi oluşturun
        val clipboard = activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = clipboard.primaryClip
        return clip?.getItemAt(0)?.text.toString()
    }

    private fun sendMessageSetScreen(metin: String) {
        binding.sorButton.isEnabled = false
        addMessage(metin, Message(metin, true))
        binding.girilenMetin.setText("")
        requireView().hideKeyboard()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.shimmerFrameLayout.startShimmer()
    }


    private fun addMessage(metin: String, mesaj: Message) {
        vm.getGPTresponse(metin)
        vm.messageList.add(mesaj)
        storyAdapter.updateList(vm.messageList)
        binding.storyRcyc.scrollToPosition(vm.messageList.size - 1)
        observResponse()
    }

    private fun observResponse() {
        vm.dataGeldiMi.observe(viewLifecycleOwner, Observer {
            it.let {
                storyAdapter.updateList(vm.messageList)
                binding.storyRcyc.scrollToPosition(vm.messageList.size - 1)
                binding.shimmerFrameLayout.visibility = View.GONE
                binding.shimmerFrameLayout.stopShimmer()
                binding.sorButton.isEnabled = true
            }
        })
    }

    private fun setNavigation(act: Int) {
        binding.shimmerFrameLayout.stopShimmer()
        Navigation.findNavController(requireView()).navigate(act)
    }


}