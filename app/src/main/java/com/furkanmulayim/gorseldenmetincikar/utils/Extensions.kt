package com.furkanmulayim.gorseldenmetincikar.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat

fun View.hideKeyboard() {
//Klavye Kapatmak için
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.showMessage(message: String) {
    //kullanıcıya mesaj göstermek için
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// Context sınıfını genişleten bir extension fonksiyon tanımı


