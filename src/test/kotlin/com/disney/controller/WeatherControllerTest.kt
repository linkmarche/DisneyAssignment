package com.disney.controller

import com.disney.constant.Constant
import com.disney.model.DailyWeather
import com.disney.model.WeatherResponse
import com.disney.service.WeatherService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import reactor.core.publisher.Mono
import reactor.test.StepVerifier


class WeatherControllerTest {

    private lateinit var weatherService: WeatherService
    private lateinit var weatherController: WeatherController

    @BeforeEach
    fun setup() {
        weatherService = mock(WeatherService::class.java)
        weatherController = WeatherController(weatherService)
    }

    @Test
    fun `test getWeatherForecastSuccess`() {
        // arrange
        val expectedResponse = WeatherResponse(
            listOf(
                DailyWeather("Monday", 25.5, "Sunny day")
            )
        )

        `when`(weatherService.getWeatherForecast()).thenReturn(Mono.just(expectedResponse))

        // act
        val actualResponse = weatherController.getWeatherForecast().block()

        // assert
        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `test getWeatherForecastError`() {
        // arrange
        val error: Mono<WeatherResponse> = Mono.error(RuntimeException(Constant.WEATHER_EXCEPTION))
        `when`(weatherService.getWeatherForecast()).thenReturn(error)

        // act & assert
        StepVerifier.create(weatherController.getWeatherForecast()).expectError(RuntimeException::class.java).verify()
    }
}