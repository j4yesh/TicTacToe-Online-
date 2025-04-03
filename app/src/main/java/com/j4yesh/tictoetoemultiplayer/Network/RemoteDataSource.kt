package com.j4yesh.tictoetoemultiplayer.Network

import androidx.datastore.preferences.protobuf.Api
import com.j4yesh.tictoetoemultiplayer.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    companion object {
        private const val BASE_URL = "https://tictactoe-online-mf1f.onrender.com/"
    }

    fun <Api> buildApi(api: Class<Api>): Api {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(logging)
            }
        }.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}