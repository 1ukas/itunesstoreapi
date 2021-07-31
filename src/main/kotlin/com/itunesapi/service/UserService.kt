package com.itunesapi.service

import com.itunesapi.component.UserComponent
import com.itunesapi.dto.ArtistModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class UserService(private val storeService: StoreService) {
    // Using an in-memory singleton class for users
    // Would hook up to an existing user database in a real system
    @Autowired
    private lateinit var user: UserComponent

    fun saveFavouriteArtist(userId: Long, artistId: Long): Boolean {
        // returns true on success, false on failure
        val userIndex = user.getUserIndex(userId)
        if (userIndex != -1 && !user.isArtistInFavorites(userIndex, artistId)) { // if user exists and artist not already in favorites
            user.addFavoriteArtist(userIndex, artistId)
            return true
        }
        return false
    }

    fun getUserFavouriteArtists(userId: Long): Flux<ArtistModel>? {
        val userIndex = user.getUserIndex(userId)
        if (userIndex != -1) { // check if user exists
            val favouriteArtistIds = user.getUserFavoriteArtistsIds(userIndex)
            if (favouriteArtistIds.isNotEmpty()) {
                return storeService.getArtists(favouriteArtistIds)
            }
        }
        return null
    }
}