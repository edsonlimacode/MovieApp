package com.edsonlima.flixapp.domain.model

import android.os.Parcelable
import com.edsonlima.flixapp.utils.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: String? = "",
    val name: String? = "",
    val lastName: String? = "",
    var email: String? = "",
    val genre: String? = ""
) : Parcelable {
    init {
        id = FirebaseHelper.getUserId()
        email = FirebaseHelper.getAuth().currentUser?.email
    }
}