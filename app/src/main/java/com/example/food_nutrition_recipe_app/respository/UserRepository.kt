package com.example.food_nutrition_recipe_app.respository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.food_nutrition_recipe_app.api.InterfaceAPI
import com.example.food_nutrition_recipe_app.model.*
import com.example.food_nutrition_recipe_app.network.NetworkUtils
import com.example.food_nutrition_recipe_app.viewmodel.MealApplication
import retrofit2.Call

class UserRepository() {


    fun getWeeklyPlan(): Call<WeeklyPlanModel>? {
            return InterfaceAPI.getApi()?.weeklyPlan(
                "101de03738mshbb18b0cea1fc68ap1c7d7cjsn2cd9538ddff1",
                "bespoke-diet-generator.p.rapidapi.com"
            )
    }

    fun getDailyPlan(): Call<DailyPlanModel>? {
            return InterfaceAPI.getApi()?.getDailyMealPlan(
                "101de03738mshbb18b0cea1fc68ap1c7d7cjsn2cd9538ddff1",
                "bespoke-diet-generator.p.rapidapi.com"
            )
    }

    fun getDailyPlanByDayIndex(indexNo: Int): Call<DailyPlanModel>? {
            return InterfaceAPI.getApi()?.getDailyMealPlanByDayIndex(
                "101de03738mshbb18b0cea1fc68ap1c7d7cjsn2cd9538ddff1",
                "bespoke-diet-generator.p.rapidapi.com",
                indexNo
            )
    }

    fun addDietPlanMeal(addDiet: AddDietPlanModel): Call<ResponseDietModel>? {
        Log.e("adddietPlan = ", addDiet.toString())
            return InterfaceAPI.getApi()?.addDietPlan(
                "ro",
                "ef734eb23bmshd06b5882a00c733p1bf7ebjsn96b5abb08419",
                "bespoke-diet-generator.p.rapidapi.com",
                addDiet
            )
    }

}

