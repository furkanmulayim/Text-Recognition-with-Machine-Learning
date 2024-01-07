package com.furkanmulayim.gorseldenmetincikar.data.service.metin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.furkanmulayim.gorseldenmetincikar.domain.model.Metin

/**
 * Database sınıfı birden fazla modeli yazmak isteyebilir o yüzden bizden
 * dizi istiyor mutlaka arrayOf içine modelimizi giriyoruz.
 *
 * Not: Room için versiyon değiştirmeye gerek yoktur. sadece veritabanı
 * değişikliği olduğu zaman versiyon değiştirilmelidir.
 *
 * Database sınıfı abstract olmaloıdır.
 *
 * */

@Database(entities = [Metin::class], version = 1)
abstract class MetinDatabase : RoomDatabase() {

    abstract fun metinDao(): MetinDAO

    /**
     * Database için farklı threadlerden conflictic olmasın diye
     * SİNGELTON mantığı ile oluşturacağız.
     * */

    companion object {
        // farklı scopelerden ulaşabiliriz
        @Volatile //farklı threadlerden çalıştırabilmek için "Volatile"
        private var instance: MetinDatabase? = null

        private val lock = Any()

        /**
         * instance var mı yok mu kontrol et ve senkronize bir şekilde yap.
         * bir threadin işi bitince sıradakine geçer.
         * */
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                //instance oluşturarak database'e eşitliyoruz
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, MetinDatabase::class.java, "metindatabase"
        ).build()
    }
}