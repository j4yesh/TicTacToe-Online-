package com.j4yesh.tictoetoemultiplayer.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.j4yesh.tictoetoemultiplayer.Data.Network.Resource
import com.j4yesh.tictoetoemultiplayer.Data.Network.UserApi
import com.j4yesh.tictoetoemultiplayer.Data.Repository.UserRepository
import com.j4yesh.tictoetoemultiplayer.Data.Responses.LoginResponse
import com.j4yesh.tictoetoemultiplayer.databinding.FragmentHomeBinding
import com.j4yesh.tictoetoemultiplayer.ui.handleApiError
import com.j4yesh.tictoetoemultiplayer.ui.base.BaseFragment
import com.j4yesh.tictoetoemultiplayer.ui.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser()
        viewModel.user.observe(viewLifecycleOwner, Observer{
            binding.progressbar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success->{
                    Toast.makeText(requireContext(),"got the token",Toast.LENGTH_SHORT).show()
                    binding.progressbar.visible(false)
                    updateUI(it.value)
                }
                is Resource.Loading->{
                    binding.progressbar.visible(true)
                }

                is Resource.Failure -> {
                    binding.progressbar.visible(false)
                    Toast.makeText(requireContext(),"Token expired",Toast.LENGTH_SHORT).show()
                    handleApiError(it)
                    lifecycleScope.launch { userPreferences.deleteAuthToken() }
                }
            }
        })
        binding.buttonLogout.setOnClickListener {
            logout()
        }
    }
    private fun HomeFragment.updateUI(user: LoginResponse) {
        with(binding) {
            textViewName.text = user.username
            winCnt.text = user.winCnt.toString()
            lossCnt.text=user.lossCnt.toString()
        }
    }

    override fun getViewModel()= HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentHomeBinding.inflate(inflater,container,false)

    override fun getFragmentRepository(): UserRepository {
        val token= runBlocking {userPreferences.authToken.first()}
        val api=remoteDataSource.buildApi(UserApi::class.java,token)
        return UserRepository(api)
    }

}


