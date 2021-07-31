package com.itunesapi.controller

import com.itunesapi.dto.AlbumModel
import com.itunesapi.dto.ArtistModel
import com.itunesapi.service.StoreService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

@Validated
@RestController
class StoreController(private val storeService: StoreService) {
    @GetMapping("/api/store/searchArtists")
    fun searchForArtistsByKeyword(@RequestParam(required = true, name = "term") @NotEmpty term: String): ResponseEntity<Flux<ArtistModel>> {
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Content-Type", "application/json")

        val artists = storeService.getArtists(term)

        return ResponseEntity(artists, httpHeaders, HttpStatus.OK)
    }

    @GetMapping("/api/store/searchAlbums")
    fun searchForAlbums(@RequestParam(required = true, name = "artistId") @Min(1) artistId: Long): ResponseEntity<Flux<AlbumModel>> {
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Content-Type", "application/json")

        val albums = storeService.getAlbums(artistId)

        return ResponseEntity(albums, httpHeaders, HttpStatus.OK)
    }
}
