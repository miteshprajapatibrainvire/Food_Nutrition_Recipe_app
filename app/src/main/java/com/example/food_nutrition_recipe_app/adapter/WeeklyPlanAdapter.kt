package com.example.food_nutrition_recipe_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
//import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food_nutrition_recipe_app.R
import com.example.food_nutrition_recipe_app.databinding.DailyPlanLayoutBinding
import com.example.food_nutrition_recipe_app.model.DailyPlan

class WeeklyPlanAdapter(var slist: List<DailyPlan>?):RecyclerView.Adapter<WeeklyPlanAdapter.viewHolder>() {


    class viewHolder(binding: DailyPlanLayoutBinding):RecyclerView.ViewHolder(binding.root)
    {
        val txType=binding.txIdType
        var recyMealId=binding.recyMealId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder
    {
        return viewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.daily_plan_layout,parent,false))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.txType.text=slist!![0].meals[position].type
        val dataList=slist!![0].meals[position].ingredients
        val mealAdp = MealAdapter(dataList)
        holder.recyMealId.adapter=mealAdp
        holder.recyMealId.layoutManager=LinearLayoutManager(holder.itemView.context)
//        holder.imgSrc.setOnClickListener {
//            if(slist!![0].meals[position].layoutChange==true)
//            {
//                slist!![0].meals[position].layoutChange=false
//                holder.recyMealId.visibility=View.VISIBLE
//            }
//            else
//            {
//                slist!![0].meals[position].layoutChange=true
//                holder.recyMealId.visibility=View.INVISIBLE
//            }
////            holder.recyMealId.visibility=View.VISIBLE
//        }

    }

    override fun getItemCount(): Int
    {
        return slist!![0].meals.size
    }

}