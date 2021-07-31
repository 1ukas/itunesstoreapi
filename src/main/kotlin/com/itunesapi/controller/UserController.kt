package com.itunesapi.controller

import com.itunesapi.dto.ArtistModel
import com.itunesapi.dto.ErrorMessageModel
import com.itunesapi.dto.SaveFavouriteArtistRequest
import com.itunesapi.service.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import javax.validation.Valid
import javax.validation.constraints.Min

@Validated
@RestController
class UserController(private val userService: UserService) {
    @GetMapping("/api/user/getFavouriteArtists")
    fun getFavouriteArtists(@RequestParam(required = true, name = "userId") @Min(1) userId: Long): ResponseEntity<Flux<ArtistModel>> {
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Content-Type", "application/json")

        val artists = userService.getUserFavouriteArtists(userId)
        if (artists != null) {
            return ResponseEntity(artists, httpHeaders, HttpStatus.OK)
        }
        return ResponseEntity(httpHeaders, HttpStatus.NOT_FOUND)
    }

    @PostMapping("/api/user/saveFavouriteArtist")
    fun saveFavouriteArtist(@Valid @RequestBody(required = true) request: SaveFavouriteArtistRequest): ResponseEntity<Any> {
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Content-Type", "application/json")

        if (userService.saveFavouriteArtist(request.userId, request.artistId)) {
            return ResponseEntity(httpHeaders, HttpStatus.CREATED)
        }
        return ResponseEntity(ErrorMessageModel("Artist already in favourites"), httpHeaders, HttpStatus.CONFLICT)
    }
}