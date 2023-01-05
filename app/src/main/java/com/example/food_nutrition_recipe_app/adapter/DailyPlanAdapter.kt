package com.example.food_nutrition_recipe_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food_nutrition_recipe_app.R
import com.example.food_nutrition_recipe_app.databinding.DailyPlanLayoutDemoBinding
import com.example.food_nutrition_recipe_app.model.MealXX

class DailyPlanAdapter(var dailyList: List<MealXX>):RecyclerView.Adapter<DailyPlanAdapter.dailyViewModel>() {

    class dailyViewModel(binding : DailyPlanLayoutDemoBinding):RecyclerView.ViewHolder(binding.root)
    {
        val txType=binding.txIdDailyType
        val recyData=binding.recyMealIdData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): dailyViewModel {

        return DailyPlanAdapter.dailyViewModel(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.daily_plan_layout_demo,parent,false))

    }

    override fun onBindViewHolder(holder: dailyViewModel, position: Int) {
        holder.txType.text= dailyList[position].type
        val dataList= dailyList[position].ingredients
        val mealAdp = MealAdapter(dataList)
        holder.recyData.adapter=mealAdp
        holder.recyData.layoutManager= LinearLayoutManager(holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return dailyList.size
    }

}