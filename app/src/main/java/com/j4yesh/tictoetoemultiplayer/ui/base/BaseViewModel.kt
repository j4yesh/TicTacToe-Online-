package com.j4yesh.tictoetoemultiplayer.ui.base

import androidx.lifecycle.ViewModel
import com.j4yesh.tictoetoemultiplayer.Data.Network.UserApi
import com.j4yesh.tictoetoemultiplayer.Data.Repository.BaseRepository

abstract class BaseViewModel (private val repository: BaseRepository): ViewModel(){
    suspend fun logout(api: UserApi)=repository.logout(api)
}