package com.maytemur.besinlerkitabiroom.servis

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.maytemur.besinlerkitabiroom.model.Besin

@Dao
interface BesinDAO {
    // Data Access Object - veri iletim objesi oluşturuyoruz
    @Insert
    suspend fun insertAll(vararg besin: Besin) : List<Long>
    //Insert - room dan geliyor insert into işlemini yapmamız için gerekli
    //suspend-> coroutine scope'unda suspend yaparak çağırabiliyoruz uzun sürecek sürebilecek
    //işlemler için
    //vararg- birden fazla besin objesini vermemizi sağlıyor
    //List<Long> -> Long döndürüyor Long döndürmesinin nedeni ID ler
    //veri çekme işleminide query ile yapıyoruz
    @Query ("SELECT * FROM besin")
    suspend fun getAllBesin() : List<Besin>

    @Query ("SELECT * FROM besin WHERE uuid= :besinId")
    suspend fun getBesin(besinId: Int) : Besin

    @Query ("DELETE FROM besin")
    suspend fun deleteAllBesin()


}