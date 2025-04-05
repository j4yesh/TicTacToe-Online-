package com.j4yesh.tictoetoemultiplayer.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.j4yesh.tictoetoemultiplayer.Data.Network.RemoteDataSource
import com.j4yesh.tictoetoemultiplayer.Data.Network.UserApi
import com.j4yesh.tictoetoemultiplayer.Data.Repository.BaseRepository
import com.j4yesh.tictoetoemultiplayer.Data.UserPreferences
import com.j4yesh.tictoetoemultiplayer.ui.Auth.AuthActivity
import com.j4yesh.tictoetoemultiplayer.ui.startNewActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseFragment<VM: BaseViewModel,B:ViewBinding , R: BaseRepository>: Fragment() {

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

    fun logout()=lifecycleScope.launch{
        val authToken=userPreferences.authToken.first()
        val api=remoteDataSource.buildApi(UserApi::class.java,authToken)
        viewModel.logout(api)
        userPreferences.deleteAuthToken()
        requireActivity().startNewActivity(AuthActivity::class.java)
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater,container: ViewGroup?):B

    abstract fun getFragmentRepository():R

}