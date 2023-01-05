package com.example.food_nutrition_recipe_app.model


import com.google.gson.annotations.SerializedName

data class DailyPlanX(
    @SerializedName("meals")
    val meals: List<MealXX >
)
{
    override fun toString(): String {
        return "DailyPlanX(meals=$meals)"
    }
}
