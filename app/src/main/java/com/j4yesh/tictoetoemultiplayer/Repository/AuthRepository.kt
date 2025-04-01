package com.j4yesh.tictoetoemultiplayer.Repository

import com.j4yesh.tictoetoemultiplayer.Network.AuthApi

class AuthRepository (
    private val api: AuthApi
): BaseRepository() {
    suspend fun login(
        username: String,
        password: String
    )=safeApiCall {
        api.login(username,password)
    }
}