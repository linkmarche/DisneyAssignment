package com.disney.model

import com.fasterxml.jackson.annotation.JsonGetter

data class WeatherResponse(
    val daily: List<DailyWeather>
)

data class DailyWeather(
    @get:JsonGetter("day_name")
    val dayName: String,
    @get:JsonGetter("temp_high_celsius")
    val tempHighCelsius: Double,
    @get:JsonGetter("forecast_blurp")
    val forecastBlurp: String
)