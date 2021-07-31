package com.itunesapi.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class AlbumModel (
    @JsonProperty("artistId")
    val artistId: Long?,

    @JsonProperty("albumName")
    val albumName: String?,

    @JsonProperty("explicit")
    var explicit: String?,

    @JsonProperty("songCount")
    val songCount: Long?,

    @JsonProperty("genre")
    val genre: String?,

    @JsonProperty("artworkUrl")
    val artworkUrl: String?,

    @JsonProperty("releaseDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val releaseDate: LocalDateTime?
) {
    constructor(storeResultModel: StoreResultModel) : this(
        storeResultModel.artistId,
        storeResultModel.collectionName,
        storeResultModel.collectionExplicitness,
        storeResultModel.trackCount,
        storeResultModel.primaryGenreName,
        storeResultModel.artworkUrl100,
        storeResultModel.releaseDate
    )
}