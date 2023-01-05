package com.example.food_nutrition_recipe_app.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val id:Int,
    val username:String,
    val email:String,
    val firstName:String,
    val lastName:String,
    val gender:String,
    val image:String,
    val token:String) {
    override fun toString(): String {
        return "LoginResponse(id=$id, username='$username', email='$email', firstName='$firstName', lastName='$lastName', gender='$gender', image='$image', token='$token')"
    }
}