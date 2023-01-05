package com.example.food_nutrition_recipe_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food_nutrition_recipe_app.databinding.MealLayoutBinding
import com.example.food_nutrition_recipe_app.model.Ingredient

class MealAdapter(var mealList: List<Ingredient>) :RecyclerView.Adapter<MealAdapter.MealHolders>() {

    class MealHolders(binding : MealLayoutBinding):RecyclerView.ViewHolder(binding.root)
    {
        var name=binding.nameid
        var incredients=binding.incredientsId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealHolders {
        return MealHolders(MealLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MealHolders, position: Int) {
        holder.name.text=mealList[position].name
        holder.incredients.text=mealList[position].quantity.toString()
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

}