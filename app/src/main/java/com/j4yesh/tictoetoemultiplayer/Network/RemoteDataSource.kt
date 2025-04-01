package com.j4yesh.tictoetoemultiplayer.Network

import androidx.datastore.preferences.protobuf.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    companion object{
        private  var BASE_URL = "http://localhost:8080/"
    }

    fun <Api>buildApi(
        api:Class<Api>
    ):Api{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}