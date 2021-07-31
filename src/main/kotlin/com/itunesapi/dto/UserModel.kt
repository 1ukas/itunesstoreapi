package com.itunesapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserModel(
    @JsonProperty("userId")
    val userId: Long,

    @JsonProperty("favouriteArtists")
    val favouriteArtists: MutableList<FavouriteArtistModel>
)
