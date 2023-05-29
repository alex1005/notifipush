package com.alexjprog.notifipush

import com.alexjprog.notifipush.data.NetworkSource
import com.alexjprog.notifipush.data.model.UserAppToken
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

internal class NotifiPushService: FirebaseMessagingService() {

    private val job = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO + job)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val appId = NotifiPush.appId
        val userAdId = NotifiPush.userAdId
        if(appId == null || userAdId == null) throw IllegalStateException("Token received, but user was not registered!")
        serviceScope.launch {
            NetworkSource.updateUserToken(userAdId, UserAppToken(token, appId))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}