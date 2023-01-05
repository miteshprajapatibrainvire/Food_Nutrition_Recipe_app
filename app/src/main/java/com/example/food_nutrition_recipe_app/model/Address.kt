package com.example.food_nutrition_recipe_app.model


import com.google.gson.annotations.SerializedName

data class Address(
    val address: String,
    val city: String,
    val coordinates: Coordinates,
    val postalCode: String,
    val state: String
)