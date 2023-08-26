package com.example.weatherapp.models

import java.io.Serializable

data class Main(
    val temp: Int,
    val feels_like:Int,
    val tempMin: Int,
    val tempMax: Int,
    val pressure: Int,
    val humidity: Int,
    val sea_level:Int,
    val grnd_level:Int

) : Serializable