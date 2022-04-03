package com.example.coffeeit.network

import com.example.coffeeit.model.CoffeeMachine
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoffeeMachineService {

    @GET("coffee-machine/{id}")
    suspend fun fetchCoffeeList(@Path("id") id: String): ApiResponse<CoffeeMachine>
}
