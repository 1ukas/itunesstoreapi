package com.itunesapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class FavouriteArtistModel(
    @JsonProperty("artistId")
    val artistId: Long
)
