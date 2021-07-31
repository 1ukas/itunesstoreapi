package com.itunesapi.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Min

data class SaveFavouriteArtistRequest(
    @JsonProperty("userId")
    @field:Min(1)
    val userId: Long,

    @JsonProperty("artistId")
    @field:Min(1)
    val artistId: Long
)
