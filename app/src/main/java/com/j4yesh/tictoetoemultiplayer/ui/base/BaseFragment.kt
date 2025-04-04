package com.j4yesh.tictoetoemultiplayer.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.j4yesh.tictoetoemultiplayer.Data.Network.RemoteDataSource
import com.j4yesh.tictoetoemultiplayer.Data.Repository.BaseRepository
import com.j4yesh.tictoetoemultiplayer.Data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseFragment<VM: ViewModel,B:ViewBinding , R: BaseRepository>: Fragment() {

    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected val remoteDataSource = RemoteDataSource()
    protected lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
        userPreferences= UserPreferences(requireContext())
        binding=getFragmentBinding(inflater,container)
        val factory=ViewModelFactory(getFragmentRepository())
        viewModel=ViewModelProvider(this,factory).get(getViewModel())
        lifecycleScope.launch { userPreferences.authToken.first() }
        return binding.root
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater,container: ViewGroup?):B

    abstract fun getFragmentRepository():R

}