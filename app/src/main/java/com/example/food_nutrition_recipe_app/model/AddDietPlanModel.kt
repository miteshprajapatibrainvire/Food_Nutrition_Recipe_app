package com.example.food_nutrition_recipe_app.model

data class AddDietPlanModel(
    val dietType:String,
    val weightGoal:Int,
    val dietDuration:Int
) {
    override fun toString(): String {
        return "AddDietPlanModel(dietType='$dietType', weightGoal=$weightGoal, dietDuration=$dietDuration)"
    }
}