package com.furkanmulayim.gorseldenmetincikar.presentation.recognize

import android.app.Application
import android.net.Uri
import android.view.View
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.furkanmulayim.gorseldenmetincikar.data.service.metin.MetinDAO
import com.furkanmulayim.gorseldenmetincikar.data.service.metin.MetinDatabase
import com.furkanmulayim.gorseldenmetincikar.domain.model.Metin
import com.furkanmulayim.gorseldenmetincikar.presentation.BaseViewModel
import com.furkanmulayim.gorseldenmetincikar.utils.SharedPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.SQLOutput

class RecognitionViewModel(application: Application) : BaseViewModel(application) {

    //shared preferences nesnesi
    private var sp = SharedPrefs(getApplication())
    val metinList = mutableListOf<Metin>()
    private val metinDao: MetinDAO = MetinDatabase(getApplication()).metinDao()

    //gelen urlyi parse etmek
    fun parsUrl(): Uri {
        //Uri türünden görselimi ver
        return Uri.parse(sp.getImageUriInShared())
    }

    fun navigate(view: View, pageId: Int) {
        Navigation.findNavController(view).navigate(pageId)
    }
    fun kayitBaslat(){
        diseaseDepolaSQLite(metinList)
    }

    private fun diseaseDepolaSQLite(list: MutableList<Metin>) {
        //hastalık verilerini Room ile sqlde depolar
        viewModelScope.launch(Dispatchers.IO) {
            val listLong = metinDao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i++
            }
        }
    }

}