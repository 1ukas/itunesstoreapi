package com.itunesapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorMessageModel (
    @JsonProperty("errorMessage")
    val errorMessage: String
)