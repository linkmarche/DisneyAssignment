package com.disney.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
open class WebClientConfig {

    @Bean
    //initializing the WebClient Bean
    open fun webClient(): WebClient {
        return WebClient.create()
    }
}