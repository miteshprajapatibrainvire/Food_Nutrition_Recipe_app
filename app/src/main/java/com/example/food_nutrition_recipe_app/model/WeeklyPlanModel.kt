package com.example.food_nutrition_recipe_app.model


import com.google.gson.annotations.SerializedName

data class WeeklyPlanModel(
    val dailyPlan: List<DailyPlan>,
    val dietDuration: Int,
    val dietType: String,
    val weightGoal: Int
)
{
    override fun toString(): String {
        return "DailyPlanModel(dailyPlan=$dailyPlan, dietDuration=$dietDuration, dietType='$dietType', weightGoal=$weightGoal)"
    }
}
