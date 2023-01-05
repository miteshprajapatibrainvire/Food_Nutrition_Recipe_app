package com.example.food_nutrition_recipe_app.model


import com.google.gson.annotations.SerializedName

data class Company(
    val address: Address,
    val department: String,
    val name: String,
    val title: String
)