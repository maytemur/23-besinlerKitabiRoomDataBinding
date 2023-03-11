package com.maytemur.besinlerkitabiroom.servis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.maytemur.besinlerkitabiroom.model.Besin

@Database(entities = arrayOf(Besin::class), version = 1)
abstract class BesinDatabase: RoomDatabase() {

    abstract fun besinDao() : BesinDAO

    //Singleton - olarak yapmak istiyoruz ,farklı treadlerden aynı anda sadece tek bir objeye
    //ulaşılsın istiyoruz
    companion object {
        @Volatile private var instance : BesinDatabase? = null //Volatile kelimesi normalde
        //thread lerle coroutine lerle çalışmıyorken gerekli değil.Bunu eklediğimizde
        //bu instance diğer thread lere görünür kılıyor
        private val lockkilit = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lockkilit) {              //?: elvis operatörü bu şu demek
            //instance null değilse direkt daha önce bir nesne oluşturulduysa BesinDatabase nesnesi
            //bunu döndür eğer oluştulmadıysa syncronized başlat diyoruz
            instance ?: databaseOlustur(context).also { //instance ?: ile bir daha konrol
                instance = it       //ediyoruz sonra database'e eşitliyoruz
            }
        }

            private fun databaseOlustur(context: Context) = Room.databaseBuilder(context.applicationContext,
            BesinDatabase::class.java,"besindatabase").build()

    }



}