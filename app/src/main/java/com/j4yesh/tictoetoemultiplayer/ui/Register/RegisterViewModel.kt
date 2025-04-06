package com.j4yesh.tictoetoemultiplayer.ui.Register

import android.R
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
import okhttp3.ResponseBody

class RegisterViewModel(  private val repository:AuthRepository) : BaseViewModel(repository) {

    private val _registerResponse: MutableLiveData<Resource<ResponseBody>> = MutableLiveData()
    val registerResponse: LiveData<Resource<ResponseBody>>
        get() = _registerResponse

    fun register(email: String, password: String) = viewModelScope.launch {
        _registerResponse.value = Resource.Loading
        val response = repository.register(email, password)
        _registerResponse.value = response
    }

}
