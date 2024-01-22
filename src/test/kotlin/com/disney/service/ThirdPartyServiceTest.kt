import com.disney.constant.Constant
import com.disney.dao.ThirdPartyDAO
import com.disney.model.DailyWeather
import com.disney.model.WeatherRequest
import com.disney.model.WeatherRequest.Period
import com.disney.model.WeatherRequest.Properties
import com.disney.model.WeatherResponse
import com.disney.service.ThirdPartyService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
class ThirdPartyServiceTest {

    @Mock
    private lateinit var thirdPartyDAO: ThirdPartyDAO

    @InjectMocks
    private lateinit var thirdPartyService: ThirdPartyService

    @Test
    fun `test getWeatherForecastFahrenheitSuccess`() {

        // arrange
        val period = Period(
            number = 1,
            temperature = 55.0,
            temperatureUnit = "F",
            shortForecast = "Sunny",
            startTime = "2024-01-08T12:00:00Z")

        val daily = DailyWeather(
            dayName = "Monday",
            tempHighCelsius = 12.8,
            forecastBlurp = "Sunny"
        )

        val request = WeatherRequest(Properties(periods = mutableListOf(period)))
        val response = WeatherResponse(daily = mutableListOf(daily))

        `when`(thirdPartyDAO.getWeatherData()).thenReturn(Mono.just(request))

        // act
        val result = thirdPartyService.getWeatherForecast()

        // assert
        StepVerifier.create(result)
            .expectNextMatches { weatherResponse ->
                weatherResponse.daily.isNotEmpty()
                weatherResponse.equals(response)
            }
            .verifyComplete()
    }

    @Test
    fun `test getWeatherForecastKelvinSuccess`() {

        // arrange
        val period = Period(
            number = 1,
            temperature = 270.0,
            temperatureUnit = "K",
            shortForecast = "Snowing",
            startTime = "2024-01-09T12:00:00Z")

        val daily = DailyWeather(
            dayName = "Tuesday",
            tempHighCelsius = -3.1,
            forecastBlurp = "Snowing"
        )

        val request = WeatherRequest(Properties(periods = mutableListOf(period)))
        val response = WeatherResponse(daily = mutableListOf(daily))

        `when`(thirdPartyDAO.getWeatherData()).thenReturn(Mono.just(request))

        // act
        val result = thirdPartyService.getWeatherForecast()

        // assert
        StepVerifier.create(result)
            .expectNextMatches { weatherResponse ->
                weatherResponse.daily.isNotEmpty()
                weatherResponse.equals(response)
            }
            .verifyComplete()
    }

    @Test
    fun `test getWeatherForecastCelsiusSuccess`() {

        // arrange
        val period = Period(
            number = 1,
            temperature = 15.236452,
            temperatureUnit = "C",
            shortForecast = "Cloudy",
            startTime = "2024-01-10T12:00:00Z")

        val daily = DailyWeather(
            dayName = "Wednesday",
            tempHighCelsius = 15.2,
            forecastBlurp = "Cloudy"
        )

        val request = WeatherRequest(Properties(periods = mutableListOf(period)))
        val response = WeatherResponse(daily = mutableListOf(daily))

        `when`(thirdPartyDAO.getWeatherData()).thenReturn(Mono.just(request))

        // act
        val result = thirdPartyService.getWeatherForecast()

        // assert
        StepVerifier.create(result)
            .expectNextMatches { weatherResponse ->
                weatherResponse.daily.isNotEmpty()
                weatherResponse.equals(response)
            }
            .verifyComplete()
    }

    @Test
    fun `test getWeatherForecastEmpty`() {
        // arrange
        val period = Period(
            number = 2,
            temperature = 15.236452,
            temperatureUnit = "C",
            shortForecast = "Cloudy",
            startTime = "2024-01-10T12:00:00Z")

        val request = WeatherRequest(Properties(periods = mutableListOf(period)))

        `when`(thirdPartyDAO.getWeatherData()).thenReturn(Mono.just(request))

        // act
        val result = thirdPartyService.getWeatherForecast()

        // assert
        StepVerifier.create(result)
            .expectNextMatches { weatherResponse ->
                weatherResponse.daily.isEmpty()
            }
            .verifyComplete()
    }

    @Test
    fun `test getWeatherForecastError`() {
        // arrange
        val error: Mono<WeatherRequest> = Mono.error(RuntimeException(Constant.WEATHER_EXCEPTION))
        `when`(thirdPartyDAO.getWeatherData()).thenReturn(error)

        // act & assert
        StepVerifier.create(thirdPartyService.getWeatherForecast()).expectError(RuntimeException::class.java).verify()
    }
}
