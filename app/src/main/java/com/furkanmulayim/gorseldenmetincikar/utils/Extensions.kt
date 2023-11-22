package com.furkanmulayim.gorseldenmetincikar.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun View.hideKeyboard() {
//Klavye Kapatmak için
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.showMessage(message: String) {
    //kullanıcıya mesaj göstermek için
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
