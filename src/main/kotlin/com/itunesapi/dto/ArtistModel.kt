package com.itunesapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ArtistModel (
    @JsonProperty("artistId")
    var artistId: Long?,

    @JsonProperty("artistName")
    val artistName: String?,

    @JsonProperty("artistLinkUrl")
    val artistLinkUrl: String?,

    @JsonProperty("genre")
    val genre: String?
) {
    constructor(storeResultModel: StoreResultModel) : this(
        storeResultModel.artistId,
        storeResultModel.artistName,
        storeResultModel.artistLinkUrl,
        storeResultModel.primaryGenreName
    )
}
