package com.example.food_nutrition_recipe_app.model


import com.google.gson.annotations.SerializedName

data class MealX(
    val ingredients: List<IngredientX>,
    val type: String
)