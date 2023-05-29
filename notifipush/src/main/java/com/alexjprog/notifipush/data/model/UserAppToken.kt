package com.alexjprog.notifipush.data.model

import com.google.gson.annotations.SerializedName

internal data class UserAppToken(
    @SerializedName("fcmtoken")
    val token: String,
    @SerializedName("app_package")
    val appId: String
)
