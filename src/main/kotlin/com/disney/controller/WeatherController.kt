package com.disney.controller

import com.disney.model.WeatherResponse
import com.disney.service.WeatherService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/weather")
class WeatherController(private val weatherService: WeatherService) {

    private val logger: Logger = LoggerFactory.getLogger(WeatherController::class.java)


    @GetMapping("/forecast")
    fun getWeatherForecast(): Mono<WeatherResponse> {
        logger.info("Request received for weather forecast.")

        return weatherService.getWeatherForecast()
            .doOnError { e ->
                logger.error("Error: ${e.message}")
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            }
            .doOnSuccess {
                logger.info("Weather forecast fetched successfully.")
            }
    }
}
