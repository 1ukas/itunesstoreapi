package com.itunesapi.service

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.itunesapi.dto.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

const val ALBUM_LIMIT: Int = 5

@Service
class StoreService(private val webClient: WebClient) {
    // Using in-memory caches for now,
    // would use a CDN in a real app (cloudflare?)
    private val artistCache: Cache<String, StoreSearchResponse> =
        Caffeine.newBuilder().expireAfterWrite(Duration.ofHours(24)).build()

    private val albumCache: Cache<String, StoreSearchResponse> =
        Caffeine.newBuilder().expireAfterWrite(Duration.ofHours(24)).build()

    fun getArtists(keyword: String): Flux<ArtistModel> {
        val uri = UriComponentsBuilder.fromPath("/search")
            .queryParam("entity", "allArtist")
            .queryParam("term", keyword)
            .toUriString()

        return searchStoreForArtists(uri)
            .filter { res ->  res.resultCount > 0 }
            .flatMap { res -> Flux.fromIterable(res.results) }
            .map { res -> ArtistModel(res) }
    }

    fun getArtists(artistIds: List<FavouriteArtistModel>): Flux<ArtistModel> {
        val idString = artistIds.joinToString(","){ it.artistId.toString() }
        val uri = UriComponentsBuilder.fromPath("/lookup")
                .queryParam("id", idString)
                .queryParam("entity", "musicArtist")
                .toUriString()

        return searchStoreForArtists(uri)
            .filter { res ->  res.resultCount > 0 }
            .flatMap { res -> Flux.fromIterable(res.results) }
            .map { res -> ArtistModel(res) }
    }

    fun getAlbums(artistId: Long): Flux<AlbumModel> =
        searchStoreForAlbumsById(artistId)
            .filter { res -> res.resultCount > 0 }
            .flatMap { res -> Flux.fromIterable(res.results) }
            .filter { res -> res.wrapperType == "collection" }
            .map { res -> AlbumModel(res) }

    private fun searchStoreForArtists(uri: String): Flux<StoreSearchResponse> {
        val cachedResponse = artistCache.getIfPresent(uri)
        if (cachedResponse != null) {
            return Flux.just(cachedResponse)
        }
        return webClient.post()
            .uri(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus({ httpStatus -> HttpStatus.NOT_FOUND == httpStatus }) { Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found in store")) }
            .onStatus(HttpStatus::is5xxServerError) { Mono.error(ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Store unavailable")) }
            .bodyToFlux(StoreSearchResponse::class.java)
            .doOnNext { response -> artistCache.put(uri, response) }
    }

    private fun searchStoreForAlbumsById(artistId: Long): Flux<StoreSearchResponse> {
        val cachedResponse = albumCache.getIfPresent(artistId.toString())
        if (cachedResponse != null) {
            return Flux.just(cachedResponse)
        }
        return webClient.post()
            .uri { it.path("/lookup")
                .queryParam("entity", "album")
                .queryParam("id", artistId)
                .queryParam("limit", ALBUM_LIMIT)
                .build() }
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus({ httpStatus -> HttpStatus.NOT_FOUND == httpStatus }) { Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found in store")) }
            .onStatus(HttpStatus::is5xxServerError) { Mono.error(ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Store unavailable")) }
            .bodyToFlux(StoreSearchResponse::class.java)
            .doOnNext { response -> albumCache.put(artistId.toString(), response) }
    }
}