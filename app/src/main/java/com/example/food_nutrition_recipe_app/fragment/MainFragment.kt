package com.example.food_nutrition_recipe_app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.food_nutrition_recipe_app.R
import com.example.food_nutrition_recipe_app.databinding.FragmentMainBinding

class MainFragment : Fragment() {


    lateinit var binding:FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentMainBinding.inflate(inflater,container,false)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

        binding.btnRegistration.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_registrationFragment)
        }

        return binding.root
    }




}