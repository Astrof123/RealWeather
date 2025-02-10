package com.example.testretrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String = "Irkutsk", //  Значение по умолчанию
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric" // Значение по умолчанию
    ): WeatherData

}