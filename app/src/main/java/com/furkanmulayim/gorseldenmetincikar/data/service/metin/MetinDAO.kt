package com.furkanmulayim.gorseldenmetincikar.data.service.metin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.furkanmulayim.gorseldenmetincikar.domain.model.Metin

@Dao
interface MetinDAO {
    //Data Access Object

    //suspend -> coroutine içerisinde çağırılır ve durdurulup başlatılabilir.
    // vararg -> bir model objeyi farklı sayılarda alacağız sayısını bilmiyoruz
    // list long ise primary keyi döndürür int olduğu için long türünden..

    @Insert
    fun insertAll(vararg metin: Metin): Array<Long>

    //Roomda saklarken model sınıf ismiyle kaydolduğu için model ismi ile giriyoruz.
    @Query("SELECT * FROM metin")
    suspend fun getAllMetins(): List<Metin>

    @Query("SELECT * FROM metin WHERE uuid = :metinId")
    suspend fun getMetin(metinId: Int): Metin

    @Query("DELETE FROM metin WHERE uuid = :metinId")
    suspend fun deleteMetin(metinId: Int)

    //kullanıcı verileri temizlemesi için
    @Query("DELETE FROM metin")
    fun deleteAllMetins()

}