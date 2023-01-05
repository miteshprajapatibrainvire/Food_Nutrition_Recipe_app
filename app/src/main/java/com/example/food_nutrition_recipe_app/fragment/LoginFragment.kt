package com.example.food_nutrition_recipe_app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.food_nutrition_recipe_app.databinding.FragmentLoginBinding
import com.example.food_nutrition_recipe_app.model.BaseResponse
import com.example.food_nutrition_recipe_app.model.LoginResponse
import com.example.food_nutrition_recipe_app.network.NetworkUtils
import com.example.food_nutrition_recipe_app.viewmodel.AuthViewModel


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    var dataSession:String="sessionData"
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.loginId.setOnClickListener {
            if(NetworkUtils.isNetworkAvailable(requireContext())) {
                validation()
            }
            else
            {
                Toast.makeText(requireContext(), "Turn On Your Internet Connection!", Toast.LENGTH_SHORT).show()
            }
        }


            viewModel.loginResult.observe(requireActivity())
            {
                when (it) {
                    is BaseResponse.Success -> {
                        Toast.makeText(requireContext(), "Login Successfully", Toast.LENGTH_SHORT)
                            .show()
                        processLogin(it.data)
                        stopLoading()
                    }
                    is BaseResponse.Loading -> {
                        showLoading()
                    }
                    is BaseResponse.Error -> {
                        stopLoading()
                        Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                    }
                }
            }


        return binding.root
    }

    fun showLoading() {
        binding.prgbarLogin.visibility = View.VISIBLE
    }

    fun stopLoading() {
        binding.prgbarLogin.visibility = View.GONE
    }

    fun processLogin(data: LoginResponse?) {
        if (!data?.token.isNullOrEmpty())
        {
            val session=context?.getSharedPreferences(dataSession,AppCompatActivity.MODE_PRIVATE)
            val edit=session?.edit()
            edit?.putString("token",data?.token.toString())
            if(edit?.commit() == true)
            {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment("Jack"))
            }
        }
    }

    private fun validation() {
        if(binding.edUsername.text!!.isEmpty())
        {
            binding.txusername.error="enter user name!"
            binding.txusername.requestFocus()
        }
        else {
            binding.txusername.error=null
            if (binding.edPassword.text!!.isEmpty())
            {
                binding.txpassword.error = "enter password!"
                binding.txpassword.requestFocus()
            }
            else
            {
                binding.txpassword.error = null
                if(binding.edPassword.text!!.length>10)
                {
                    binding.txpassword.error = "enter password under 10 digit!"
                    binding.txpassword.requestFocus()
                }
                else {
                    binding.txpassword.error = null
                    viewModel.loginUser(binding.edUsername.text.toString(),
                        binding.edPassword.text.toString())
                }
            }
        }
    }

}