package com.example.food_nutrition_recipe_app.model


import com.google.gson.annotations.SerializedName

data class MealXXX(
    @SerializedName("ingredients")
    val ingredients: List<IngredientXXX>,
    @SerializedName("type")
    val type: String
)