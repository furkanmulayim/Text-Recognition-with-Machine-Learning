package com.furkanmulayim.gorseldenmetincikar.presentation.detail

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.furkanmulayim.gorseldenmetincikar.R
import com.furkanmulayim.gorseldenmetincikar.data.service.metin.MetinDAO
import com.furkanmulayim.gorseldenmetincikar.data.service.metin.MetinDatabase
import com.furkanmulayim.gorseldenmetincikar.domain.model.Metin
import com.furkanmulayim.gorseldenmetincikar.presentation.BaseViewModel
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {

    var metinId = MutableLiveData<Int>()
    val metin = MutableLiveData<Metin?>()

    private val metinDao: MetinDAO = MetinDatabase(getApplication()).metinDao()

    fun veriSil(id: Int, view: View) {
        deleteSQLiteDataFromRoom(id, view)
    }

    fun veriGetir() {
        getSQLiteDataFromRoom()
    }

    private fun getSQLiteDataFromRoom() {
        launch {
            val dao = metinId.value?.let { metinDao.getMetin(it) }
            metin.value = dao
        }
    }

    private fun deleteSQLiteDataFromRoom(id: Int, view: View) {
        launch {
            metinDao.deleteMetin(id)
        }.invokeOnCompletion {
            Navigation.findNavController(view)
                .navigate(R.id.action_detailHistoryFragment_to_historyFragment)
        }
    }


}