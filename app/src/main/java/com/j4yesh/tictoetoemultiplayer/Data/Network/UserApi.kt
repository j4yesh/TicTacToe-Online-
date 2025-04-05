package com.j4yesh.tictoetoemultiplayer.Data.Network

import com.j4yesh.tictoetoemultiplayer.Data.Responses.LoginResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET("auth/user")
    suspend fun getUser(): LoginResponse

    @POST("auth/logout")
    suspend fun logout(): ResponseBody
}