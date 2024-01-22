package com.disney.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
open class WebClientConfig {

    @Value("\${weather.api.url}")
    private lateinit var weatherUrl: String

    @Value("\${thirdparty.api.url}")
    private lateinit var thirdPartyUrl: String

    @Bean
    open fun weatherWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl(weatherUrl)
            .build()
    }

    @Bean
    open fun thirdPartyWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl(thirdPartyUrl)
            .build()
    }
}
