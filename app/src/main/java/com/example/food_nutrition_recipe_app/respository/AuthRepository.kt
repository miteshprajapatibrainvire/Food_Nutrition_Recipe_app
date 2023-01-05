package com.example.food_nutrition_recipe_app.respository

import android.util.Log
import com.example.food_nutrition_recipe_app.api.InterfaceAPI
import com.example.food_nutrition_recipe_app.model.LoginRequest
import com.example.food_nutrition_recipe_app.model.LoginResponse
import com.example.food_nutrition_recipe_app.model.RegisterRequest
import com.example.food_nutrition_recipe_app.model.RegisterResponse
import retrofit2.Call

class AuthRepository {

    fun loginUser(loginRequest: LoginRequest): Call<LoginResponse>? {
        Log.e("userrepository_login=",loginRequest.toString())
        return  InterfaceAPI.getAuthAPI()?.sendUserData(loginRequest)
    }

    fun registerUser(registerRequest: RegisterRequest): Call<RegisterResponse>?{
        Log.e("userrepositoryregister=",registerRequest.toString())
        return InterfaceAPI.getAuthAPI()?.sendRegisterData(registerRequest)
    }
}