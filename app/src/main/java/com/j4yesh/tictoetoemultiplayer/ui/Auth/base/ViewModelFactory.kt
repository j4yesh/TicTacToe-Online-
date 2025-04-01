package com.j4yesh.tictoetoemultiplayer.ui.Auth.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.j4yesh.tictoetoemultiplayer.Repository.AuthRepository
import com.j4yesh.tictoetoemultiplayer.Repository.BaseRepository
import com.j4yesh.tictoetoemultiplayer.ui.Auth.AuthViewModel

class ViewModelFactory (
    private val repository: BaseRepository
):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java)->AuthViewModel(repository as AuthRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }
}