package com.itunesapi.component

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.itunesapi.dto.FavouriteArtistModel
import com.itunesapi.dto.UserModel
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class UserComponent {
    private lateinit var users: List<UserModel>

    @PostConstruct
    fun loadUsersFromFile() {
        val json = this::class.java.getResource("/files/users.json").readText()
        val users: List<UserModel> = jacksonObjectMapper().readValue(json)
        this.users = users
    }

    fun getUserIndex(userId: Long): Int = users.indexOfFirst{ user -> user.userId == userId }

    fun getUserFavoriteArtistsIds(userIndex: Int): List<FavouriteArtistModel> =
        users[userIndex].favouriteArtists

    fun isArtistInFavorites(userIndex: Int, artistId: Long): Boolean =
        users[userIndex].favouriteArtists.any{ a -> a.artistId == artistId }

    fun addFavoriteArtist(userIndex: Int, artistId: Long) =
        users[userIndex].favouriteArtists.add(FavouriteArtistModel(artistId))
}