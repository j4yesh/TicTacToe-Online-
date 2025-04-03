package com.j4yesh.tictoetoemultiplayer.ui.Auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.j4yesh.tictoetoemultiplayer.Network.AuthApi
import com.j4yesh.tictoetoemultiplayer.Network.RemoteDataSource
import com.j4yesh.tictoetoemultiplayer.Network.Resource
import com.j4yesh.tictoetoemultiplayer.R
import com.j4yesh.tictoetoemultiplayer.Repository.AuthRepository
import com.j4yesh.tictoetoemultiplayer.databinding.FragmentLoginBinding
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
class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding,AuthRepository>() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Toast.makeText(requireContext(),"inside the loginFragment",Toast.LENGTH_LONG).show()
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success->{
                    Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_LONG).show()
                }
                is Resource.Failure->{
                    Log.e("LoginFragment", "Login Failed: ${it.errorBody}")
                    Toast.makeText(requireContext(),"Login Failure",Toast.LENGTH_LONG).show()
                }
            }
        })
        binding.buttonLogin.setOnClickListener{
            val email=binding.editTextTextEmailAddress.text.toString().trim()
            val password=binding.editTextTextPassword.text.toString().trim()
            //do input validations
            Toast.makeText(requireContext(),"login pressed",Toast.LENGTH_LONG).show();
            viewModel.login(email,password)
        }
    }

    override fun getViewModel()=AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )=FragmentLoginBinding.inflate(inflater,container,false)

    override fun getFragmentRepository()= AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))


}