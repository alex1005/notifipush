package com.alexjprog.notifipush.data

import com.alexjprog.notifipush.data.model.MainUserData
import com.alexjprog.notifipush.data.model.UserAppTag
import com.alexjprog.notifipush.data.model.UserAppToken
import com.alexjprog.notifipush.data.network.NotifiApi
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object NetworkSource {

    private val api: NotifiApi by lazy {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor { chain ->
                    val request = chain.request()
                    val requestFunc = fun(request: Request): Response? {
                        return try {
                            chain.proceed(request)
                        } catch (_: Exception) {
                            null
                        }
                    }
                    var response = requestFunc(request)

                    while (response == null || !response.isSuccessful) {
                        response = requestFunc(request)
                    }
                    response
                }
                .build())
            .baseUrl("https://aff-demo-push.herokuapp.com/")
            .build()

        retrofit.create(NotifiApi::class.java)
    }

    suspend fun registerUser(user: MainUserData) {
        api.postUser(user)
    }

    suspend fun updateUserToken(userAdId: String, token: UserAppToken) {
        api.putUserToken(userAdId, token)
    }

    suspend fun updateUserTag(userAdId: String, tag: UserAppTag) {
        api.putUserTag(userAdId, tag)
    }
}