package com.example.food_nutrition_recipe_app.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val username:String,
    val password:String)
{
    override fun toString(): String {
        return "LoginRequest(username='$username', password='$password')"
    }
}