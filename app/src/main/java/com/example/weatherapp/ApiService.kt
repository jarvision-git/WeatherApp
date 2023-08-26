package com.example.weatherapp

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private const val BASE_URL =
    "https://run.mocky.io/v3/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MarsApiService {
    @GET("7ad62fa6-b164-487a-9b24-fe7d2204f220")
    suspend fun getPhotos(): List<detail>
}

object MarsApi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }

}