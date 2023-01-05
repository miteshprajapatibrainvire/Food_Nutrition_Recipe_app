package com.example.food_nutrition_recipe_app.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_nutrition_recipe_app.adapter.WeeklyPlanAdapter
import com.example.food_nutrition_recipe_app.databinding.FragmentWeeklyMealBinding
import com.example.food_nutrition_recipe_app.model.BaseResponse
import com.example.food_nutrition_recipe_app.network.NetworkUtils
import com.example.food_nutrition_recipe_app.viewmodel.WeeklyPlanViewModel

class WeeklyMealFragment : Fragment() {

    private val viewModel by viewModels<WeeklyPlanViewModel>()
    lateinit var binding:FragmentWeeklyMealBinding
    var weeklyAdp:WeeklyPlanAdapter ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentWeeklyMealBinding.inflate(inflater,container,false)

        viewModel.getWeeklyPlan()
//        if(NetworkUtils.isNetworkAvailable(requireContext())) {
            viewModel.weeklyPlanResult.observe(requireActivity())
            {

                when (it) {
                    is BaseResponse.Success -> {
                        Log.e("DailyMeal=", it.data?.dailyPlan.toString())
                        weeklyAdp = WeeklyPlanAdapter(it.data?.dailyPlan)
                        binding.weeklyPlanRecy.adapter = weeklyAdp
                        binding.weeklyPlanRecy.layoutManager = LinearLayoutManager(requireContext())
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
//        }
//        else
//        {
//            Toast.makeText(requireContext(), "Turn On Your Internet Connection!", Toast.LENGTH_SHORT).show()
//        }
        return binding.root
    }

    fun showLoading() {
        binding.prgbar.visibility = View.VISIBLE
    }

    fun stopLoading() {
        binding.prgbar.visibility = View.GONE
    }

}