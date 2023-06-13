package com.alexjprog.notifipush

import com.alexjprog.notifipush.data.NetworkSource
import com.alexjprog.notifipush.data.model.MainUserData
import com.alexjprog.notifipush.data.model.UserAppTag
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.Locale

object NotifiPush {

    private val job = SupervisorJob()
    private val singletonScope = CoroutineScope(Dispatchers.IO + job)
    private var userTagChannel: Channel<String> = Channel()

    internal var userAdId: String? = null
    internal var appId: String? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    fun initApi(userAdId: String, appId: String) {
        this.userAdId = userAdId
        this.appId = appId

        singletonScope.launch {
            val user = MainUserData(
                userAdId,
                Locale.getDefault().country,
                Locale.getDefault().language,
                appId
            )
            NetworkSource.registerUser(user)

            Firebase.messaging.isAutoInitEnabled = true

            launch {
                while (!userTagChannel.isClosedForReceive) {
                    val tag = userTagChannel.receive()
                    completeSettingUserTag(tag)
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun setUserTag(tag: String) {
        singletonScope.launch {
            if(!userTagChannel.isClosedForSend) userTagChannel.send(tag)
        }
    }

    private suspend fun completeSettingUserTag(tag: String) {
        val userAdId = this@NotifiPush.userAdId
        val appId = this@NotifiPush.appId
        if(userAdId == null || appId == null) throw IllegalStateException("Tag received, but user was not registered!")
        NetworkSource.updateUserTag(userAdId, UserAppTag(tag, appId))
    }
}