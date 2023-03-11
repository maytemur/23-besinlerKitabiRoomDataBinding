package com.maytemur.besinlerkitabiroom.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.maytemur.besinlerkitabiroom.model.Besin
import com.maytemur.besinlerkitabiroom.servis.BesinAPIServis
import com.maytemur.besinlerkitabiroom.servis.BesinDatabase
import com.maytemur.besinlerkitabiroom.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application): BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()
    private var guncellemeZamani = 10*60*1000*1000*1000L //dakikanin nano time a çevrilmiş hali



    private val besinApiServis = BesinAPIServis()
    private val disposable = CompositeDisposable () // kullan at veri
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())

    fun refreshData() {
//        val muz = Besin("Muz","40","3","2","1","www.test.com")
//        val cilek = Besin("Çilek","45","3","2","1","www.test.com")
//        val elma = Besin("Elma","50","3","2","1","www.test.com")
//
//        val besinListesi = arrayListOf<Besin>(muz,cilek,elma)
//
//        besinler.value = besinListesi
//        besinHataMesaji.value = false
//        besinYukleniyor.value = false
        // retrofit sonrası örnek verilere ihtiyaç kalmadı

        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()
        if (kaydedilmeZamani != null && kaydedilmeZamani !=0L && System.nanoTime()-kaydedilmeZamani<guncellemeZamani){
            //Sqlite tan çek
            verileriSqlitetanAl()

        }else {
            verileriInternettenAl()
        }

    }
        fun refreshfromInternet (){
            verileriInternettenAl()
        }
        private fun verileriSqlitetanAl (){
            besinYukleniyor.value = true

          launch {
              val besinListesi= BesinDatabase(getApplication()).besinDao().getAllBesin()
              besinleriGoster(besinListesi)
              Toast.makeText(getApplication(),"Besinleri Room'dan aldık",Toast.LENGTH_LONG).show()

          }
        }


        private fun verileriInternettenAl (){
            besinYukleniyor.value = true
        //IO thread -input output ver, alış verişi için genelde thread -
        // default -daha çok cpu yoğun görsel işlerlerde - UI kullanıcının arayüzü ile ilgili işlemleri yaptığımız
            disposable.add( //kullan at data
                besinApiServis.getData()
                    .subscribeOn(Schedulers.newThread())  //yeni bir threadde asenkron olarak
                    .observeOn(AndroidSchedulers.mainThread()) //single gözlemlenebilir objesine bununla kayıt(subscribe)
                    // oluyoruz-bunu main threadde kullanıcya göstermemiz gerekiyor
                    //bunuda observe on ile yapıyoruz, yani önce arka planda
                    // yeni threadde kayıt olup gözlemledik sonra ana main threadde kullanıcıya gösteriyoruz
                    .subscribeWith(object: DisposableSingleObserver<List<Besin>>() {//Disposable abstract class olduğundan
                        //object: ile object olarak tanımlamamız gerekiyor. Soyut sınıfın uygulaması gereken öğeler var
                        //succes ve error
                        override fun onSuccess(t: List<Besin>) {
                            //Başarılı Olursa
//                            besinler.value = t
//                            besinHataMesaji.value = false
//                            besinYukleniyor.value = false
                            sqliteSakla(t)
                        Toast.makeText(getApplication(),"Besinleri Internetten aldık",Toast.LENGTH_LONG).show()

                    }
                        override fun onError(e: Throwable) {
                            //Hata Alırsak
                            besinHataMesaji.value = true
                            besinYukleniyor.value = false
                            e.printStackTrace()
                        }
                    })
            )
        }
    private fun besinleriGoster (besinlerListesi: List<Besin>){
        besinler.value = besinlerListesi
        besinHataMesaji.value = false
        besinYukleniyor.value = false
    }
    private fun sqliteSakla (besinListesi: List<Besin>){

        launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()
        val uuidListesi=dao.insertAll(*besinListesi.toTypedArray())  // başına yıldız koyunca verdiğimiz listeyi tek tek veriyor
            var i=0
            while (i < besinListesi.size){
                besinListesi[i].uuid = uuidListesi[i].toInt()//sqlite oluşturulan id leri modelin içine koyuyoruz burada
                i=i+1
            }
            besinleriGoster(besinListesi)
        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())

    }
}