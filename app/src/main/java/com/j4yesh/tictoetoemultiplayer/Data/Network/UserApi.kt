package com.j4yesh.tictoetoemultiplayer.Data.Network

import com.j4yesh.tictoetoemultiplayer.Data.Responses.LoginResponse
import retrofit2.http.GET

interface UserApi {

    @GET("auth/user")
    suspend fun getUser(): LoginResponse
}