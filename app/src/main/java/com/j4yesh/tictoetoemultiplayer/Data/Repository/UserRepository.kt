package com.j4yesh.tictoetoemultiplayer.Data.Repository

import com.j4yesh.tictoetoemultiplayer.Data.Network.AuthApi
import com.j4yesh.tictoetoemultiplayer.Data.Network.UserApi
import com.j4yesh.tictoetoemultiplayer.Data.Request.LoginRequest
import com.j4yesh.tictoetoemultiplayer.Data.UserPreferences

class UserRepository (
    private val api: UserApi
): BaseRepository() {
    suspend fun getUser()=safeApiCall {
        api.getUser()
    }
}