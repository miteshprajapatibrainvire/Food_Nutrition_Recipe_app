package com.example.food_nutrition_recipe_app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.food_nutrition_recipe_app.R
import com.example.food_nutrition_recipe_app.RegisterHandler
import com.example.food_nutrition_recipe_app.databinding.FragmentRegistrationBinding
import com.example.food_nutrition_recipe_app.model.BaseResponse
import com.example.food_nutrition_recipe_app.network.NetworkUtils
import com.example.food_nutrition_recipe_app.viewmodel.AuthViewModel

class RegistrationFragment : Fragment(),RegisterHandler {

    lateinit var binding:FragmentRegistrationBinding
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    lateinit var viewModel:AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            binding= FragmentRegistrationBinding.inflate(inflater, container, false)
            viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

            binding.viewModelRegister=viewModel
            binding.handler = this
            binding.lifecycleOwner=this

            viewModel.getRegistrationResult().observe(requireActivity(), Observer { result ->
                if(result=="valid credention")
                {
                    val radioBtn=view?.findViewById<RadioButton>(id)
                    val radioText=radioBtn?.text.toString()
                    viewModel.registerUser(binding.edFirstName.text.toString(),binding.edLastName.text.toString(),
                        binding.edMiddleName.text.toString(),Integer.parseInt(binding.idAge.text.toString()),radioText,binding.idEmail.text.toString(),
                        binding.idPhone.text.toString(),binding.edUsername.text.toString(),binding.edPassword.text.toString())
                    Toast.makeText(requireContext(), "valid credential!", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(requireActivity(), result.toString(), Toast.LENGTH_SHORT).show()
                }
            })

            binding.registerId.setOnClickListener {
                if(NetworkUtils.isNetworkAvailable(requireContext())) {
                    validation()
                }
                else
                {
                    Toast.makeText(requireContext(), "Turn On Your Internet Connection!", Toast.LENGTH_SHORT).show()
                }
            }

        if(NetworkUtils.isNetworkAvailable(requireContext())) {
            viewModel.registerResult.observe(viewLifecycleOwner)
            {
                when (it) {
                    is BaseResponse.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Register successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
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
        }
        else
        {
            Toast.makeText(requireContext(), "Turn On Your Internet Connection!", Toast.LENGTH_SHORT).show()
        }
            return binding.root
    }

    fun showLoading() {
        binding.prgbarRegistration.visibility = View.VISIBLE
    }

    fun stopLoading() {
        binding.prgbarRegistration.visibility=View.VISIBLE
    }

    private fun validation()
    {
        if(binding.edFirstName.text!!.isEmpty())
        {
            binding.idtxFirstName.error="enter firstname!"
            binding.idtxFirstName.requestFocus()
        }
        else
        {
            binding.idtxFirstName.error=null
            if(binding.edLastName.text!!.isEmpty())
            {
               binding.idtxLastName.error="enter lastname!"
               binding.idtxLastName.requestFocus()
            }
            else
            {
                binding.idtxLastName.error=null
                if(binding.edMiddleName.text!!.isEmpty())
                {
                    binding.idtxMiddleName.error="enter middlename!"
                    binding.idtxMiddleName.requestFocus()
                }
                else
                {
                    binding.idtxMiddleName.error=null
                    if(binding.idAge.text!!.isEmpty())
                    {
                        binding.idtxAge.error="enter age!"
                        binding.idtxAge.requestFocus()
                    }
                    else
                    {
                        if(binding.idAge.text!!.length>3) {
                            binding.idtxAge.error="enter only 2 digit age!"
                            binding.idtxAge.requestFocus()
                        }
                        else {

                            binding.idtxAge.error = null
                            val id = binding.radioGroupId.checkedRadioButtonId
                            if (null == id) {
                                binding.rbMale.error = "select Gender"
                                binding.rbFemale.error = "select Gender"
                            }

                            if (binding.idEmail.text!!.isEmpty()) {
                                binding.idtxEmail.error = "enter email id!"
                                binding.idtxEmail.requestFocus()
                            } else {
                                if (!binding.idEmail.text.toString().trim().matches(emailPattern.toRegex())) {
                                    binding.idtxEmail.error = "Invalide email address!"
                                    binding.idtxEmail.requestFocus()
                                }
                                else {
                                    binding.idtxEmail.error = null
                                    if (binding.idPhone.text!!.isEmpty()) {
                                        binding.txidPhone.error = "enter phone number!"
                                        binding.txidPhone.requestFocus()
                                    } else {
                                        binding.txidPhone.error = null
                                        if (binding.idPhone.text!!.length>10 || binding.idPhone.text!!.length<10) {
                                            binding.txidPhone.error = "enter only 10 digit phone number!"
                                            binding.txidPhone.requestFocus()
                                        }
                                        else {
                                            if (binding.edUsername.text!!.isEmpty()) {
                                                binding.txidUsername.error = "enter username!"
                                                binding.edUsername.requestFocus()
                                            }
                                            else
                                            {
                                                binding.txidUsername.error = null
                                                if (binding.edPassword.text!!.isEmpty()) {
                                                    binding.txidPassword.error = "enter password!"
                                                    binding.txidPassword.requestFocus()
                                                } else {
                                                    binding.txidPassword.error = null

                                                    val radioBtn=view?.findViewById<RadioButton>(id)
                                                    val radioText=radioBtn?.text.toString()

                                                    viewModel.registerUser(binding.edFirstName.text.toString(),binding.edLastName.text.toString(),
                                                    binding.edMiddleName.text.toString(),Integer.parseInt(binding.idAge.text.toString()),radioText,binding.idEmail.text.toString(),
                                                    binding.idPhone.text.toString(),binding.edUsername.text.toString(),binding.edPassword.text.toString())
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun registerOnClick() {
        viewModel.performRegistrationValidation()
    }

}