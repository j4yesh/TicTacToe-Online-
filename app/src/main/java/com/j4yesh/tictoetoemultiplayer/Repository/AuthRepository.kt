package com.j4yesh.tictoetoemultiplayer.Repository

import com.j4yesh.tictoetoemultiplayer.Network.AuthApi
import com.j4yesh.tictoetoemultiplayer.Request.LoginRequest
import com.j4yesh.tictoetoemultiplayer.Responses.LoginResponse

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