package com.example.food_nutrition_recipe_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food_nutrition_recipe_app.fragment.HomeFragment
import com.example.food_nutrition_recipe_app.model.AddDietPlanModel
import com.example.food_nutrition_recipe_app.model.BaseResponse
import com.example.food_nutrition_recipe_app.model.Meal
import com.example.food_nutrition_recipe_app.model.ResponseDietModel
import com.example.food_nutrition_recipe_app.respository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class AddDietViewModel:ViewModel() {

    private var userRepository=UserRepository()
    val addDietResult: MutableLiveData<BaseResponse<ResponseDietModel>> = MutableLiveData()

    fun addDietPlan(dietType:String,weightGoal:Int,dietDuration:Int)
    {
        addDietResult.value= BaseResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val addDietData=AddDietPlanModel(dietType,weightGoal,dietDuration)
                val response=userRepository.addDietPlanMeal(addDietData)
                response?.enqueue(object : retrofit2.Callback<ResponseDietModel> {
                    override fun onResponse(
                        call: Call<ResponseDietModel>,
                        response: Response<ResponseDietModel>
                    ) {
                        if(response.isSuccessful)
                        {
                            if(response.code()==200)
                            {
                                Log.e("addResponseDiet=",response.body().toString())
                                addDietResult.value = BaseResponse.Success(response.body())
                            }
                            else
                            {
                                addDietResult.value = BaseResponse.Error(response.message())
                            }
                        }
                        else
                        {
                            addDietResult.value = BaseResponse.Error(response.message())
                        }
                    }

                    override fun onFailure(call: Call<ResponseDietModel>, t: Throwable) {
                        Log.e("error=",t.toString())
                        addDietResult.value = BaseResponse.Error("Internal Server Error")
                    }
                })

            }
            catch(ex: Exception)
            {
                    Log.e("Exception=",ex.toString())
            }
        }
    }
}