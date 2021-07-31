package com.itunesapi.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class StoreResultModel(
    @JsonProperty("wrapperType")
    val wrapperType: String,

    @JsonProperty("artistType")
    val artistType: String?,

    @JsonProperty("artistName")
    val artistName: String,

    @JsonProperty("artistLinkUrl")
    val artistLinkUrl: String?,

    @JsonProperty("artistId")
    val artistId: Long?,

    @JsonProperty("amgArtistId")
    val amgArtistId: Long?,

    @JsonProperty("primaryGenreName")
    val primaryGenreName: String?,

    @JsonProperty("primaryGenreId")
    val primaryGenreId: Long?,

    @JsonProperty("collectionType")
    val collectionType: String?,

    @JsonProperty("collectionId")
    val collectionId: Long?,

    @JsonProperty("collectionName")
    val collectionName: String?,

    @JsonProperty("collectionViewUrl")
    val collectionViewUrl: String?,

    @JsonProperty("collectionExplicitness")
    val collectionExplicitness: String?,

    @JsonProperty("artworkUrl100")
    val artworkUrl100: String?,

    @JsonProperty("trackCount")
    val trackCount: Long?,

    @JsonProperty("country")
    val country: String?,

    @JsonProperty("releaseDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val releaseDate: LocalDateTime?,
)
