package com.example.weatherapp

import com.example.weatherapp.models.WResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL =
    "https://api.openweathermap.org/data/"

const val API_ID: String="df4f7d273c3a81161dbb04ec23634f02"

interface WeatherApiService {
    @GET("2.5/weather")
    fun getWeather(
        @Query("lat") lat :Double?,
        @Query("lon") lon:Double?,
        @Query("appid") appid :String?,
    ) : Call<WResponse>
}

