package com.j4yesh.tictoetoemultiplayer.ui.Register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.j4yesh.tictoetoemultiplayer.Data.Network.Resource
import com.j4yesh.tictoetoemultiplayer.Data.Repository.AuthRepository
import com.j4yesh.tictoetoemultiplayer.Data.Repository.BaseRepository
import com.j4yesh.tictoetoemultiplayer.Data.Responses.LoginResponse
import com.j4yesh.tictoetoemultiplayer.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class RegisterViewModel(  private val repository:AuthRepository) : BaseViewModel(repository) {

    private val _registerResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val registerResponse: LiveData<Resource<LoginResponse>>
        get() = _registerResponse

    fun register(username: String, password: String) = viewModelScope.launch {
        _registerResponse.value = Resource.Loading
        val result = repository.register(username, password) // assuming this returns Resource<LoginResponse>
        _registerResponse.value = result
        Log.d("RegisterViewModel", "Register Response: $result")
    }
}
