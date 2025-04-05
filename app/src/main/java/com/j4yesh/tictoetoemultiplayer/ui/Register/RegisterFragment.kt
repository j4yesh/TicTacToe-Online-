package com.j4yesh.tictoetoemultiplayer.ui.Register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.j4yesh.tictoetoemultiplayer.Data.Network.AuthApi
import com.j4yesh.tictoetoemultiplayer.Data.Network.Resource
import com.j4yesh.tictoetoemultiplayer.Data.Repository.AuthRepository
import com.j4yesh.tictoetoemultiplayer.R
import com.j4yesh.tictoetoemultiplayer.databinding.FragmentLoginBinding
import com.j4yesh.tictoetoemultiplayer.databinding.FragmentRegisterBinding
import com.j4yesh.tictoetoemultiplayer.ui.Auth.AuthViewModel
import com.j4yesh.tictoetoemultiplayer.ui.base.BaseFragment
import com.j4yesh.tictoetoemultiplayer.ui.enable
import com.j4yesh.tictoetoemultiplayer.ui.handleApiError
import com.j4yesh.tictoetoemultiplayer.ui.home.HomeActivity
import com.j4yesh.tictoetoemultiplayer.ui.startNewActivity
import com.j4yesh.tictoetoemultiplayer.ui.visible
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : BaseFragment<RegisterViewModel, FragmentRegisterBinding,AuthRepository>() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Toast.makeText(requireContext(),"inside the register fragment",Toast.LENGTH_LONG).show()
        binding.progressbar.visible(false)
        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(false)
            binding.buttonRegister.visible(false)
            when(it){
                is Resource.Success->{
//                    lifecycleScope.launch{
//                        userPreferences.saveAuthToken(it.value.token!! ) //Not Recommended
//                        requireActivity().startNewActivity(HomeActivity::class.java)
//                    }
                    Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_LONG).show()
                }
                is Resource.Failure->{
                    Log.e("LoginFragment", "Login Failed: ${it.errorBody}")
                    Toast.makeText(requireContext(),"Login Failure",Toast.LENGTH_LONG).show()
//                    handleApiError(it){login()}
                }

                Resource.Loading -> {

                }
            }
        })

        binding.editTextTextPassword.doAfterTextChanged { editable ->
            val username = binding.editTextTextEmailAddress.text.toString().trim()
            val password = editable?.toString()?.trim()
            binding.buttonRegister.enable(username.isNotEmpty() && !password.isNullOrEmpty())
        }


        binding.buttonRegister.setOnClickListener{
            register()
        }
    }

    private fun register(){
        val email=binding.editTextTextEmailAddress.text.toString().trim()
        val password=binding.editTextTextPassword.text.toString().trim()
        //do input validations
        binding.progressbar.visible(true)
        viewModel.register(email,password)
    }

    override fun getViewModel()= RegisterViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentRegisterBinding.inflate(inflater,container,false)

    override fun getFragmentRepository()= AuthRepository(remoteDataSource.buildApi(AuthApi::class.java),userPreferences)

}