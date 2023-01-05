package com.example.food_nutrition_recipe_app.model


import com.google.gson.annotations.SerializedName

data class ResponseDietModel(
    @SerializedName("dailyPlan")
    val dailyPlan: List<DailyPlanX>,
    @SerializedName("dietDuration")
    val dietDuration: Int,
    @SerializedName("dietType")
    val dietType: String,
    @SerializedName("weightGoal")
    val weightGoal: Int
)
{
    override fun toString(): String {
        return "ResponseDietModel(dailyPlan=$dailyPlan, dietDuration=$dietDuration, dietType='$dietType', weightGoal=$weightGoal)"
    }
}