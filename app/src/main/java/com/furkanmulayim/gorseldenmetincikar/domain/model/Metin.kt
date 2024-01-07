package com.furkanmulayim.gorseldenmetincikar.domain.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Metin(
    @ColumnInfo(name = "gun")var gun:String,
    @ColumnInfo(name = "ay")var ay:String,
    @ColumnInfo(name = "hafta")var hafta:String,
    @ColumnInfo(name = "saat")var saat:String,
    @ColumnInfo(name = "metin")var metin:String
){
    //primary key room için gereklidir.
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}