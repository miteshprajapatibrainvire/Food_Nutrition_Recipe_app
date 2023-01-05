package com.example.food_nutrition_recipe_app.model


import com.google.gson.annotations.SerializedName

data class Meal(
    val ingredients: List<Ingredient>,
    val type: String,
  var layoutChange:Boolean=false
)