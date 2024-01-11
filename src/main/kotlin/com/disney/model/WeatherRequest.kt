package com.disney.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties
class WeatherRequest(
    @JsonProperty("properties")
    val properties: Properties) {

    data class Properties(
        @JsonProperty("periods")
        val periods: List<Period>)

    data class Period(
        @JsonProperty("name")
        val name: String,
        @JsonProperty("temperature")
        val temperature: Double,
        @JsonProperty("temperatureUnit")
        val temperatureUnit: String,
        @JsonProperty("shortForecast")
        val shortForecast: String,
        @JsonProperty("startTime")
        val startTime: String
    )
}
