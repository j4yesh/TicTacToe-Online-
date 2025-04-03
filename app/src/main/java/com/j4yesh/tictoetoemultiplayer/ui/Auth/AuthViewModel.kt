package com.j4yesh.tictoetoemultiplayer.ui.Auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.j4yesh.tictoetoemultiplayer.Data.Network.Resource
import com.j4yesh.tictoetoemultiplayer.Data.Repository.AuthRepository
import com.j4yesh.tictoetoemultiplayer.Data.Responses.LoginResponse
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository:AuthRepository
) : ViewModel(){

    private val _loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse:LiveData<Resource<LoginResponse>>
        get()= _loginResponse

    fun login(
        username:String,
        password:String
    ) = viewModelScope.launch{
        _loginResponse.value=repository.login(username,password)
        Log.d("AuthViewModel", "Login Response: ${_loginResponse.value}")
    }
}