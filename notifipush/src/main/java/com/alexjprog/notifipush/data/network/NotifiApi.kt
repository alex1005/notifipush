package com.alexjprog.notifipush.data.network

import com.alexjprog.notifipush.data.model.MainUserData
import com.alexjprog.notifipush.data.model.UserAppTag
import com.alexjprog.notifipush.data.model.UserAppToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

internal interface NotifiApi {

    @POST("adduser")
    suspend fun postUser(@Body user: MainUserData): Response<Void>

    @PUT("updatetoken/{userAdId}")
    suspend fun putUserToken(@Path("userAdId") userAdId: String, @Body userToken: UserAppToken): Response<Void>

    @PUT("updatetag/{userAdId}")
    suspend fun putUserTag(@Path("userAdId") userAdId: String, @Body userTag: UserAppTag): Response<Void>

}