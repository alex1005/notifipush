package com.alexjprog.notifipush.data.model

import com.google.gson.annotations.SerializedName

internal data class MainUserData(
    @SerializedName("gadid")
    val userAdId: String,
    @SerializedName("country")
    val countryCode: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("app_package")
    val appId: String
)