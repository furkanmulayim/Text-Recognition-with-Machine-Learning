package com.furkanmulayim.gorseldenmetincikar.presentation.history

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.furkanmulayim.gorseldenmetincikar.data.service.metin.MetinDAO
import com.furkanmulayim.gorseldenmetincikar.data.service.metin.MetinDatabase
import com.furkanmulayim.gorseldenmetincikar.domain.model.Metin
import com.furkanmulayim.gorseldenmetincikar.presentation.BaseViewModel
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : BaseViewModel(application) {

    val metinlist = MutableLiveData<List<Metin>>()

    private val metinDao: MetinDAO = MetinDatabase(getApplication()).metinDao()

    fun navigate(view: View, pageId: Int) {
        Navigation.findNavController(view).navigate(pageId)
    }

    fun verileriYukle() {
        getSQLiteDataFromRoom()
    }

    fun veriSil(id: Int) {
        deleteSQLiteDataFromRoom(id)
    }

    private fun getSQLiteDataFromRoom() {
        launch {
            val dao = metinDao.getAllMetins()
            metinlist.value = dao
        }
    }

    private fun deleteSQLiteDataFromRoom(id: Int) {
        launch {
            metinDao.deleteMetin(id)
            getSQLiteDataFromRoom()
        }
    }


}