package com.disney.dao

import com.disney.model.WeatherRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Repository
class ThirdPartyDAO(@Qualifier("thirdPartyWebClient") private val webClient: WebClient): DAO {
    override fun getWeatherData(): Mono<WeatherRequest> {
        return webClient.get()
            .retrieve()
            .bodyToMono(WeatherRequest::class.java)
    }
}
