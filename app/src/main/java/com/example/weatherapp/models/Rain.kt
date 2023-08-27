package com.example.weatherapp.models

import com.squareup.moshi.Json
import java.io.Serializable

data class Rain(
    @Json(name="1h") val h1 : Double):Serializable //1h not allowed by kotlin as variable
