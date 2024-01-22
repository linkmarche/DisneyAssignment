package com.disney.controller

import com.disney.constant.Constant
import com.disney.model.DailyWeather
import com.disney.model.WeatherResponse
import com.disney.service.ThirdPartyService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import reactor.core.publisher.Mono
import reactor.test.StepVerifier


class ThirdPartyControllerTest {

    private lateinit var thirdPartyService: ThirdPartyService
    private lateinit var thirdPartyController: ThirdPartyController

    @BeforeEach
    fun setup() {
        thirdPartyService = mock(ThirdPartyService::class.java)
        thirdPartyController = ThirdPartyController(thirdPartyService)
    }

    @Test
    fun `test getWeatherForecastSuccess`() {
        // arrange
        val expectedResponse = WeatherResponse(
            listOf(
                DailyWeather("Monday", 25.5, "Sunny day")
            )
        )

        `when`(thirdPartyService.getWeatherForecast()).thenReturn(Mono.just(expectedResponse))

        // act
        val actualResponse = thirdPartyController.getWeatherForecast().block()

        // assert
        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `test getWeatherForecastError`() {
        // arrange
        val error: Mono<WeatherResponse> = Mono.error(RuntimeException(Constant.WEATHER_EXCEPTION))
        `when`(thirdPartyService.getWeatherForecast()).thenReturn(error)

        // act & assert
        StepVerifier.create(thirdPartyController.getWeatherForecast()).expectError(RuntimeException::class.java).verify()
    }
}