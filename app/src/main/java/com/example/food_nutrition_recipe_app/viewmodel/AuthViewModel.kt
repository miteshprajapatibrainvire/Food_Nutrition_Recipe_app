package com.example.food_nutrition_recipe_app.viewmodel

import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
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
    var username:String=""
    var password:String=""
    var firstname:String=""
    var lastname:String=""
    var middlename:String=""
    var age:String=""
    var gender:String=""
    var email:String=""
    var phone:String=""
    var optionA = "Male"
    var optionB = "Female"
    private var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    //android:text="9uQFF1Lh"
    //android:text="atuny0"

    private var logInResultValidation=MutableLiveData<String>()
    fun getLogInResult(): LiveData<String> = logInResultValidation

    private var registrationResultValidation=MutableLiveData<String>()
    fun getRegistrationResult(): LiveData<String> = registrationResultValidation

    fun performLoginValidation()
    {
        when{
            username.isBlank()->{
                logInResultValidation.value="Invalide username!"
            }
            username.length>=16->{
                logInResultValidation.value="your username should be 15 character!"
            }
            password.isBlank()->{
                logInResultValidation.value="Invalid password!"
            }
//            password.length>10 || password.length<10->{
//                logInResultValidation.value="your password should be 10 character!"
//            }
            password.isNotEmpty() && username.isNotEmpty()->{
                logInResultValidation.value="valid credention!"
                loginUser(username,password)
            }
        }
    }

    fun maleClickRadio()
    {

    }
    fun femaleClickRadio(fl:String)
    {

    }

    fun onSplitTypeChanged(radioGroup: RadioGroup?, id: Int) {
        Log.e("dataClick=",id.toString())

    }

    fun performRegistrationValidation()
    {
        when{
            firstname.isBlank()->{
                registrationResultValidation.value="Enter firstname!"
            }
            firstname.length>=10->{
                registrationResultValidation.value="Firstname should be under 10 digit!"
            }
            lastname.isBlank()->{
                registrationResultValidation.value="Enter lastname!"
            }
            lastname.length>=10->{
                registrationResultValidation.value="Lastname should be under 10 digit!"
            }
            middlename.isBlank()->{
                registrationResultValidation.value="Enter middlename!"
            }
            middlename.length>=10->{
                registrationResultValidation.value="Middlename should be under 10 digit!"
            }
            age.isBlank()->{
                registrationResultValidation.value="Enter your age!"
            }
            Integer.parseInt(age) <18->{
                registrationResultValidation.value="Your age should be above 18+!"
            }
            email.isBlank()->{
                registrationResultValidation.value="Enter Email Address!"
            }
            !email.trim().matches(emailPattern.toRegex())->{
                registrationResultValidation.value="Invalid email!"
            }
            phone.isBlank()->{
                registrationResultValidation.value="Enter phone number!"
            }
            phone.length>10 || phone.length<10 ->{
                registrationResultValidation.value="your phone number should be 10 digit"
            }
            password.isBlank()->{
                registrationResultValidation.value="Enter password!"
            }
            password.length>11 ->{
                registrationResultValidation.value="your password should be 10 digit!"
            }
            password.isNotEmpty() && username.isNotEmpty() && firstname.isNotEmpty() && lastname.isNotEmpty() && middlename.isNotEmpty() && age.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()->{
                registrationResultValidation.value="valid credention!"
            }
        }
    }



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