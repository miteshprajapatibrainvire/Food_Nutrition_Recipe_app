package com.example.food_nutrition_recipe_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food_nutrition_recipe_app.model.*
import com.example.food_nutrition_recipe_app.respository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeeklyPlanViewModel:
   ViewModel() {

    private val userRepository = UserRepository()
    val weeklyPlanResult: MutableLiveData<BaseResponse<WeeklyPlanModel>> = MutableLiveData()

    fun getWeeklyPlan()
    {
        weeklyPlanResult.value= BaseResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response=userRepository.getWeeklyPlan()
                response?.enqueue(object : Callback<WeeklyPlanModel>{
                    override fun onResponse(
                        call: Call<WeeklyPlanModel>,
                        response: Response<WeeklyPlanModel>
                    ) {
                        if(response.isSuccessful)
                        {
                            if (response.code() == 200)
                            {
                                Log.e("responseWeeklyPlan=",response.body().toString())
                                weeklyPlanResult.value = BaseResponse.Success(response.body())
                            }
                            else
                            {
                                weeklyPlanResult.value = BaseResponse.Error(response.message())
                            }
                        }
                        else{
                            weeklyPlanResult.value = BaseResponse.Error(response.message())
                        }
                    }

                    override fun onFailure(call: Call<WeeklyPlanModel>, t: Throwable) {
                        weeklyPlanResult.value = BaseResponse.Error("Internal Server error!")
                      Log.e("weeklyplanList=",t.toString())
                    }
                })
            }
            catch (ex: Exception) {
                weeklyPlanResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

}