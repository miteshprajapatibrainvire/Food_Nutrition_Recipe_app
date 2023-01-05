package com.example.food_nutrition_recipe_app.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.food_nutrition_recipe_app.R
import com.example.food_nutrition_recipe_app.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {

    var dataSession:String="sessionData"
    lateinit var binding:FragmentSplashScreenBinding
    lateinit var lottiAnime:LottieAnimationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding=FragmentSplashScreenBinding.inflate(inflater,container,false)
         binding.lottieId.playAnimation()
         binding.lottieLoadingId.playAnimation()

        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            val session=context?.getSharedPreferences(dataSession, AppCompatActivity.MODE_PRIVATE)
            if(session?.getString("token",null)!=null)
            {
                findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
            }
            else
            {
                findNavController().navigate(R.id.action_splashScreenFragment_to_mainFragment)
            }
        },3000)

        return binding.root
    }



}