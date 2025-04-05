package com.j4yesh.tictoetoemultiplayer.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.j4yesh.tictoetoemultiplayer.Data.Network.Resource
import com.j4yesh.tictoetoemultiplayer.Data.Repository.AuthRepository
import com.j4yesh.tictoetoemultiplayer.Data.Repository.UserRepository
import com.j4yesh.tictoetoemultiplayer.Data.Responses.LoginResponse
import com.j4yesh.tictoetoemultiplayer.ui.base.BaseViewModel
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repository: UserRepository
) : BaseViewModel(repository) {

    private val _user: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val user: LiveData<Resource<LoginResponse>> get() = _user

    fun getUser() = viewModelScope.launch {
        _user.value= Resource.Loading
//        _user.postValue(repository.getUser())
        _user.value=repository.getUser()
    }


}