package com.example.food_nutrition_recipe_app.viewmodel

import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food_nutrition_recipe_app.model.*
import com.example.food_nutrition_recipe_app.respository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel:
ViewModel()
{
   private val userRepository = AuthRepository()
    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()

    val registerResult: MutableLiveData<BaseResponse<RegisterResponse>> = MutableLiveData()

    fun registerUser( firstName:String,
                      lastName:String,
                      maidenName:String,
                      age:Int,
                      gender:String,
                      email:String,
                      phone:String,
                      username:String,
                      password:String)
    {
            registerResult.value=BaseResponse.Loading()
            viewModelScope.launch(Dispatchers.IO) {
            try{
                val registerRequest=RegisterRequest(firstName,lastName,maidenName,age,gender,email,phone,username,password)
                val response=userRepository.registerUser(registerRequest)
                response?.enqueue(object : Callback<RegisterResponse>{
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        if(response.isSuccessful)
                        {
                            val responseData=response.body()
                            Log.e("responseRegisterData=",responseData.toString())
                            if (response.code() == 200)
                            {
                                registerResult.value = BaseResponse.Success(response.body())
                            }
                            else
                            {
                                registerResult.value = BaseResponse.Error(response.message())
                            }
                        }
                    }
                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        registerResult.value = BaseResponse.Error("Internal Server Error!")
                    }
                })
            }
            catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun loginUser(email: String, pwd: String) {

        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loginRequest = LoginRequest(
                    username=email,
                    password=pwd
                )
                val response = userRepository.loginUser(loginRequest)

                response?.enqueue(object : Callback<LoginResponse>{
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if(response.isSuccessful)
                        {
                            val responseData=response.body()
                            Log.e("responseData=",responseData.toString())
                            if (response.code() == 200) {
                                loginResult.value = BaseResponse.Success(response.body())
                            }
                            else
                            {
                                loginResult.value = BaseResponse.Error(response.message())
                            }
                        }
                        else
                        {
                            Log.e("error response=",response.body().toString())
                            Log.e("error Body=",response.errorBody().toString())
                        }
                    }
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.e("login error=",t.toString())
                        loginResult.value = BaseResponse.Error("Internal Server Error!")
                    }
                })
            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}