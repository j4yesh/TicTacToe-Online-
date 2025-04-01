package com.j4yesh.tictoetoemultiplayer.Network

import com.j4yesh.tictoetoemultiplayer.Responses.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("auth/login")
    suspend  fun login(
        @Field("username") username: String,
        @Field("password") password:String

    ):LoginResponse
}