package com.j4yesh.tictoetoemultiplayer.Network

import com.j4yesh.tictoetoemultiplayer.Request.LoginRequest
import com.j4yesh.tictoetoemultiplayer.Responses.LoginResponse
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

}