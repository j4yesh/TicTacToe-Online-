package com.j4yesh.tictoetoemultiplayer.Data.Network

import com.j4yesh.tictoetoemultiplayer.Data.Request.LoginRequest
import com.j4yesh.tictoetoemultiplayer.Data.Responses.LoginResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/signup")
    suspend fun register(@Body request: LoginRequest): ResponseBody


}