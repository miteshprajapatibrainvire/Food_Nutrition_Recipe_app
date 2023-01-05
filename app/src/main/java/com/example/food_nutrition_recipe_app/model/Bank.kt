package com.example.food_nutrition_recipe_app.model


import com.google.gson.annotations.SerializedName

data class Bank(
    val cardExpire: String,
    val cardNumber: String,
    val cardType: String,
    val currency: String,
    val iban: String
)