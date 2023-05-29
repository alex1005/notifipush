package com.alexjprog.notifipush.data.model

import com.google.gson.annotations.SerializedName

internal data class UserAppTag(
    @SerializedName("tag")
    val tag: String,
    @SerializedName("app_package")
    val appId: String
)