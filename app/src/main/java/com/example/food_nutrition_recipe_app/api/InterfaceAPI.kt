package com.example.food_nutrition_recipe_app.api

import com.example.food_nutrition_recipe_app.model.*
import retrofit2.Call
import retrofit2.http.*

interface InterfaceAPI {

    @POST("/auth/login")
    fun sendUserData(
        @Body myData: LoginRequest
    ): Call<LoginResponse>

    @POST("/users/add")
    fun sendRegisterData(
        @Body registerData:RegisterRequest
    ):Call<RegisterResponse>

    @GET("/user/KZSlVgYKwhgN7eTstqTWG/diet/5?dayIndex=5&userId=KZSlVgYKwhgN7eTstqTWG")
    fun getDailyMealPlan(@Header("X-RapidAPI-Key") key:String, @Header("X-RapidAPI-Host") host:String):Call<DailyPlanModel>

    @GET("/user/KZSlVgYKwhgN7eTstqTWG/diet/{index}?dayIndex=5&userId=KZSlVgYKwhgN7eTstqTWG")
    fun getDailyMealPlanByDayIndex(@Header("X-RapidAPI-Key") key:String, @Header("X-RapidAPI-Host") host:String,@Path("index") index:Int):Call<DailyPlanModel>

    @GET("/user/KZSlVgYKwhgN7eTstqTWG/diet")
    fun weeklyPlan(@Header("X-RapidAPI-Key") key:String, @Header("X-RapidAPI-Host") host:String):Call<WeeklyPlanModel>

    @PUT("/user/nJrlgEC0EIZVNZsgkffcK/diet")
    fun addDietPlan(@Header("Accept-Language") language:String,@Header("X-RapidAPI-Key") key:String, @Header("X-RapidAPI-Host") host:String,@Body addDiet:AddDietPlanModel):Call<ResponseDietModel>

    companion object {
        fun getApi(): InterfaceAPI? {
            return ApiClient.client?.create(InterfaceAPI::class.java)
        }

        fun getAuthAPI():InterfaceAPI?{
            return ApiClient.client2?.create(InterfaceAPI::class.java)
        }
    }

}