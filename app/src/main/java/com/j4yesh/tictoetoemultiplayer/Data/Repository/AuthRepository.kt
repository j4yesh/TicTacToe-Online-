package com.j4yesh.tictoetoemultiplayer.Data.Repository

import com.j4yesh.tictoetoemultiplayer.Data.Network.AuthApi
import com.j4yesh.tictoetoemultiplayer.Data.Request.LoginRequest

class AuthRepository (
    private val api: AuthApi
): BaseRepository() {
    suspend fun login(
        username: String,
        password: String
    )=safeApiCall {
        api.login(LoginRequest(username,password))
    }
}