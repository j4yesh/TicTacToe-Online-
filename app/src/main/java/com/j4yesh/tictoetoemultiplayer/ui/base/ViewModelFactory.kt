package com.j4yesh.tictoetoemultiplayer.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.j4yesh.tictoetoemultiplayer.Data.Repository.AuthRepository
import com.j4yesh.tictoetoemultiplayer.Data.Repository.BaseRepository
import com.j4yesh.tictoetoemultiplayer.Data.Repository.UserRepository
import com.j4yesh.tictoetoemultiplayer.ui.Auth.AuthViewModel
import com.j4yesh.tictoetoemultiplayer.ui.Register.RegisterViewModel
import com.j4yesh.tictoetoemultiplayer.ui.home.HomeViewModel

class ViewModelFactory (
    private val repository: BaseRepository
):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java)->AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java)-> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java)-> HomeViewModel(repository as UserRepository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java)-> RegisterViewModel(repository as AuthRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }
}