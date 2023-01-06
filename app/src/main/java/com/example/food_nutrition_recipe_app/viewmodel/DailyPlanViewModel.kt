package com.example.food_nutrition_recipe_app.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food_nutrition_recipe_app.model.BaseResponse
import com.example.food_nutrition_recipe_app.model.DailyPlanModel
import com.example.food_nutrition_recipe_app.respository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DailyPlanViewModel:
    ViewModel() {

    private val userRepository = UserRepository()
    val dailyPlanResult: MutableLiveData<BaseResponse<DailyPlanModel>> = MutableLiveData()

    fun getDailyPlan()
    {
        //                android:text="atuny0"
        dailyPlanResult.value= BaseResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
          try{
                val response=userRepository.getDailyPlan()
                response?.enqueue(object : Callback<DailyPlanModel> {
                    override fun onResponse(
                        call: Call<DailyPlanModel>,
                        response: Response<DailyPlanModel>
                    ) {
                        if(response.isSuccessful)
                        {
                            if (response.code() == 200)
                            {
                                Log.e("responseDailyPlan=",response.body().toString())
                                dailyPlanResult.value = BaseResponse.Success(response.body())
                            }
                            else
                            {
                                dailyPlanResult.value = BaseResponse.Error(response.message())
                            }
                        }
                        else{
                            dailyPlanResult.value = BaseResponse.Error(response.message())
                        }
                    }

                    override fun onFailure(call: Call<DailyPlanModel>, t: Throwable) {
                            Log.e("error=",t.toString())
                        dailyPlanResult.value = BaseResponse.Error(t.toString())

                    }
                })
            }
            catch (ex: Exception) {
                dailyPlanResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun getDailyPlanByDayIndex(indexNo:Int)
    {
        dailyPlanResult.value= BaseResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response=userRepository.getDailyPlanByDayIndex(indexNo)
                response?.enqueue(object : Callback<DailyPlanModel> {
                    override fun onResponse(
                        call: Call<DailyPlanModel>,
                        response: Response<DailyPlanModel>
                    ) {
                        if(response.isSuccessful)
                        {
                            if (response.code() == 200)
                            {
                                Log.e("responseDailyPlanIndex=",response.body().toString())
                                dailyPlanResult.value = BaseResponse.Success(response.body())
                            }
                            else
                            {
                                dailyPlanResult.value = BaseResponse.Error(response.message())
                            }
                        }
                        else{
                            dailyPlanResult.value = BaseResponse.Error(response.message())
                        }
                    }

                    override fun onFailure(call: Call<DailyPlanModel>, t: Throwable) {
                        dailyPlanResult.value = BaseResponse.Error("Internal Server Error!")
                    }
                })
            }
            catch (ex: Exception) {
                dailyPlanResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

}