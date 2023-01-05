package com.example.food_nutrition_recipe_app.model


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class DailyPlanModel(
    val meals: List<MealXX>
)
{
    override fun toString(): String {
        return "DailyPlanModel(meals=$meals)"
    }
}