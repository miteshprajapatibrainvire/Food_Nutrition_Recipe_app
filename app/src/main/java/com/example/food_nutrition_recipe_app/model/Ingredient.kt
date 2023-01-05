package com.example.food_nutrition_recipe_app.model


import com.google.gson.annotations.SerializedName

data class Ingredient(
    val id: String,
    val name: String,
    val quantity: Double
)