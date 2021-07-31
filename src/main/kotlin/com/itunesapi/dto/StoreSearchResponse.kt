package com.itunesapi.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import reactor.core.publisher.Flux

@JsonIgnoreProperties(ignoreUnknown = true)
data class StoreSearchResponse (
    @JsonProperty("resultCount")
    val resultCount: Long,

    @JsonProperty("results")
    val results: List<StoreResultModel>
)