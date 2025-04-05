package com.j4yesh.tictoetoemultiplayer.Data.Repository

import com.j4yesh.tictoetoemultiplayer.Data.Network.AuthApi
import com.j4yesh.tictoetoemultiplayer.Data.Request.LoginRequest
import com.j4yesh.tictoetoemultiplayer.Data.UserPreferences

class AuthRepository (
    private val api: AuthApi,
    private val preferences: UserPreferences
): BaseRepository() {
    suspend fun login(
        username: String,
        password: String
    )=safeApiCall {
        api.login(LoginRequest(username,password))
    }

    suspend fun register(
        username: String,
        password: String
    )=safeApiCall {
        api.register(LoginRequest(username,password)) // we can still use same object
    }

    suspend fun saveAuthToken(token: String){
        preferences.saveAuthToken(token)
    }

    suspend fun remoteAuthToken(){
        preferences.deleteAuthToken()
    }
}