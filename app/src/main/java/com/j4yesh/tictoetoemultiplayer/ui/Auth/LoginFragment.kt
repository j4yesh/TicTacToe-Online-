package com.j4yesh.tictoetoemultiplayer.ui.Auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.j4yesh.tictoetoemultiplayer.Network.RemoteDataSource
import com.j4yesh.tictoetoemultiplayer.R
import com.j4yesh.tictoetoemultiplayer.Repository.AuthRepository
import com.j4yesh.tictoetoemultiplayer.ui.Auth.base.BaseFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : BaseFragment<AuthViewModel,FragmentLoginBinding,AuthRepository>() {
    override fun getViewModel()= AuthViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )=FragmentLoginBinding.inflate(inflater,container,false)

    override fun getFragmentRepository()=AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))

}