import com.disney.dao.ThirdPartyDAO
import com.disney.dao.WeatherDAO
import com.disney.model.WeatherRequest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class ThirdPartyDAOTest {
    @Test
    fun `getWeatherData should return WeatherRequest`() {
        // arrange
        val webClient = mock(WebClient::class.java)
        val thirdPartyDAO = ThirdPartyDAO(webClient as WebClient)

        val period = WeatherRequest.Period(
            number = 1,
            temperature = 55.0,
            temperatureUnit = "F",
            shortForecast = "Sunny",
            startTime = "2024-01-08T12:00:00Z"
        )

        val request = WeatherRequest(WeatherRequest.Properties(periods = mutableListOf(period)))

        // Mock WebClient response
        `when`(webClient.get()).thenReturn(mock(WebClient.RequestHeadersUriSpec::class.java))
        `when`(webClient.get().retrieve()).thenReturn(mock(WebClient.ResponseSpec::class.java))
        `when`(webClient.get().retrieve().bodyToMono(WeatherRequest::class.java)).thenReturn(Mono.just(request))

        // act
        val result = thirdPartyDAO.getWeatherData()

        // assert
        StepVerifier.create(result)
            .expectNext(request)
            .verifyComplete()
    }
}
