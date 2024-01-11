import com.disney.constant.Constant
import com.disney.model.DailyWeather
import com.disney.service.WeatherService
import com.disney.model.WeatherRequest
import com.disney.model.WeatherRequest.Period
import com.disney.model.WeatherRequest.Properties
import com.disney.model.WeatherResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class WeatherServiceTest {

    private lateinit var webClient: WebClient
    private lateinit var weatherService: WeatherService

    @BeforeEach
    fun setup() {
        // Mocking the WebClient
        webClient = mock(WebClient::class.java)
        weatherService = WeatherService(webClient)
    }

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

        `when`(webClient.get()).thenReturn(mock(WebClient.RequestHeadersUriSpec::class.java))
        `when`(webClient.get().uri(anyString())).thenReturn(mock(WebClient.RequestHeadersSpec::class.java))
        `when`(webClient.get().uri(anyString()).retrieve()).thenReturn(mock(WebClient.ResponseSpec::class.java))
        `when`(webClient.get().uri(anyString()).retrieve().bodyToMono(WeatherRequest::class.java)).thenReturn(Mono.just(request))

        // act
        val result = weatherService.getWeatherForecast()

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

        `when`(webClient.get()).thenReturn(mock(WebClient.RequestHeadersUriSpec::class.java))
        `when`(webClient.get().uri(anyString())).thenReturn(mock(WebClient.RequestHeadersSpec::class.java))
        `when`(webClient.get().uri(anyString()).retrieve()).thenReturn(mock(WebClient.ResponseSpec::class.java))
        `when`(webClient.get().uri(anyString()).retrieve().bodyToMono(WeatherRequest::class.java)).thenReturn(Mono.just(request))

        // act
        val result = weatherService.getWeatherForecast()

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

        `when`(webClient.get()).thenReturn(mock(WebClient.RequestHeadersUriSpec::class.java))
        `when`(webClient.get().uri(anyString())).thenReturn(mock(WebClient.RequestHeadersSpec::class.java))
        `when`(webClient.get().uri(anyString()).retrieve()).thenReturn(mock(WebClient.ResponseSpec::class.java))
        `when`(webClient.get().uri(anyString()).retrieve().bodyToMono(WeatherRequest::class.java)).thenReturn(Mono.just(request))

        // act
        val result = weatherService.getWeatherForecast()

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
        `when`(webClient.get()).thenReturn(mock(WebClient.RequestHeadersUriSpec::class.java))
        `when`(webClient.get().uri(anyString())).thenReturn(mock(WebClient.RequestHeadersSpec::class.java))
        `when`(webClient.get().uri(anyString()).retrieve()).thenReturn(mock(WebClient.ResponseSpec::class.java))
        `when`(webClient.get().uri(anyString()).retrieve().bodyToMono(WeatherRequest::class.java)).thenReturn(Mono.just(request))

        // act
        val result = weatherService.getWeatherForecast()

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
        `when`(webClient.get()).thenReturn(mock(WebClient.RequestHeadersUriSpec::class.java))
        `when`(webClient.get().uri(anyString())).thenReturn(mock(WebClient.RequestHeadersSpec::class.java))
        `when`(webClient.get().uri(anyString()).retrieve()).thenReturn(mock(WebClient.ResponseSpec::class.java))
        `when`(webClient.get().uri(anyString()).retrieve().bodyToMono(WeatherRequest::class.java)).thenReturn(error)

        // act & assert
        StepVerifier.create(weatherService.getWeatherForecast()).expectError(RuntimeException::class.java).verify()
    }
}
