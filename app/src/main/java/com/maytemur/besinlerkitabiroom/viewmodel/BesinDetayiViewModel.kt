package com.maytemur.besinlerkitabiroom.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maytemur.besinlerkitabiroom.model.Besin
import com.maytemur.besinlerkitabiroom.servis.BesinDatabase
import kotlinx.coroutines.launch

class BesinDetayiViewModel(application: Application): BaseViewModel(application) {
    val besinLiveData = MutableLiveData<Besin>()

    fun roomVerisiniAl(uuid: Int){
        //val muz = Besin("Muz","40","3","2","1","www.test.com")
        //besinLiveData.value = muz
        launch {
            val dao= BesinDatabase(getApplication()).besinDao()
            val besin = dao.getBesin(uuid)
            besinLiveData.value = besin
        }

    }
}