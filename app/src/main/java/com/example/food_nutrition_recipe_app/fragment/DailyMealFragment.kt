package com.example.food_nutrition_recipe_app.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_nutrition_recipe_app.adapter.DailyPlanAdapter
import com.example.food_nutrition_recipe_app.databinding.FragmentDailyMealBinding
import com.example.food_nutrition_recipe_app.model.BaseResponse
import com.example.food_nutrition_recipe_app.network.NetworkUtils
import com.example.food_nutrition_recipe_app.viewmodel.DailyPlanViewModel

class DailyMealFragment : Fragment() {

     lateinit var binding:FragmentDailyMealBinding
     private val viewModel by viewModels<DailyPlanViewModel>()
     var spTouched: Boolean? = null
     var adpDaily:DailyPlanAdapter ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentDailyMealBinding.inflate(inflater,container,false)
        viewModel.getDailyPlan()
        initSpinner()
//        if(NetworkUtils.isNetworkAvailable(requireContext())) {
            viewModel.dailyPlanResult.observe(requireActivity())
            {
                when (it) {
                    is BaseResponse.Success -> {
                        Log.e("DailyMeal=", it.data?.meals.toString())
                        adpDaily = DailyPlanAdapter(it.data!!.meals)
                        binding.recyDailyPlane.adapter = adpDaily
                        binding.recyDailyPlane.layoutManager = LinearLayoutManager(requireContext())
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



    private fun initSpinner() {
        val slist = ArrayList<String>()
        slist.add("1")
        slist.add("2")
        slist.add("3")
        slist.add("4")
        slist.add("5")
        slist.add("6")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            slist
        )

        binding.sp.adapter = adapter
        binding.sp.setOnTouchListener { v, event ->
            spTouched = true
            false
        }

        binding.sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (spTouched == true) {
//                    Toast.makeText(requireActivity(), slist.get(p2), Toast.LENGTH_LONG)
//                        .show()
                    viewModel.getDailyPlanByDayIndex(Integer.parseInt(slist.get(p2)))
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

    }

    fun showLoading() {
        binding.prgbar.visibility = View.VISIBLE
    }

    fun stopLoading() {
        binding.prgbar.visibility = View.GONE
    }

}