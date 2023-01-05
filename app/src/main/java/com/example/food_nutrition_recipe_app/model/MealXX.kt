package com.example.food_nutrition_recipe_app.model


import com.google.gson.annotations.SerializedName

data class MealXX(
    val ingredients: List<Ingredient>,
    val type: String
)
{
    override fun toString(): String {
        return "MealXX(ingredients=$ingredients, type='$type')"
    }
}
