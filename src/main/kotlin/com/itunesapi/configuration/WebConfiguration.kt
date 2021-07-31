package com.itunesapi.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.util.MimeType
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebConfiguration(@Value("\${store.url}") private val storeUrl: String) {

    @Bean
    fun webClient(): WebClient {
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
                                         .registerModule(JavaTimeModule())

        return WebClient.builder()
            .codecs {
                it.customCodecs().register(Jackson2JsonDecoder(objectMapper, MimeType("text", "javascript")))
            }
            .baseUrl(storeUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()
    }
}