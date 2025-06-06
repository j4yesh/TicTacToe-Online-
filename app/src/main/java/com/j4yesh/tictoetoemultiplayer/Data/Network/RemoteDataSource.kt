package com.j4yesh.tictoetoemultiplayer.Data.Network

import androidx.datastore.preferences.protobuf.Api
import com.j4yesh.tictoetoemultiplayer.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteDataSource {

    companion object {
        private const val BASE_URL = "https://tictactoe-online-mf1f.onrender.com/"
    }

    fun <Api> buildApi(api: Class<Api>, authToken:String?=null): Api {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().

            addInterceptor { chain->chain.proceed(chain.request().newBuilder().also{
                if(authToken!=null) it.addHeader("Authorization","Bearer $authToken")
            }.build()) }
            .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(logging)
            }
        }
            .connectTimeout(60, TimeUnit.SECONDS)  // connection timeout
            .readTimeout(60, TimeUnit.SECONDS)     // socket read timeout
            .writeTimeout(60, TimeUnit.SECONDS)    // socket write timeout
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}