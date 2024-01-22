package com.disney.dao

import com.disney.model.WeatherRequest
import reactor.core.publisher.Mono

interface DAO {
    fun getWeatherData(): Mono<WeatherRequest>
}