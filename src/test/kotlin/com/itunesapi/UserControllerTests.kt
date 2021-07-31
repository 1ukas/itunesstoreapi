package com.itunesapi

import com.itunesapi.dto.ArtistModel
import com.itunesapi.dto.SaveFavouriteArtistRequest
import org.junit.FixMethodOrder
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Tag("User")
class UserControllerTests {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    @DisplayName("Test if we can save a favourite artist for a given user")
    fun saveUserFavouriteArtists() {
        val request = SaveFavouriteArtistRequest(123, 372976)
        webTestClient.post()
            .uri { it.path("/api/user/saveFavouriteArtist").build() }
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(request), SaveFavouriteArtistRequest::class.java)
            .exchange()
            .expectStatus().isCreated
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
    }

    @Test
    @DisplayName("Test if saving duplicate artists fails")
    fun failToSaveFavouriteArtistConflict() {
        val request = SaveFavouriteArtistRequest(2, 372976)
        webTestClient.post()
            .uri { it.path("/api/user/saveFavouriteArtist").build() }
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(request), SaveFavouriteArtistRequest::class.java)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.CONFLICT)
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.errorMessage").isNotEmpty
    }

    @Test
    @DisplayName("Test if we can fail to save a favourite artist for a given user by giving bad request values")
    fun failToSaveFavouriteArtistBadRequest() {
        val request = SaveFavouriteArtistRequest(2, 372976)
        webTestClient.post()
            .uri { it.path("/api/user/saveFavouriteArtist").build() }
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(request), SaveFavouriteArtistRequest::class.java)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.CONFLICT)
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.errorMessage").isNotEmpty
    }

    @Test
    @DisplayName("Test if we can get favourite artists for user by param")
    fun getFavouriteArtistsForUserByParam() {
        val id = 3
        webTestClient.get()
            .uri { it.path("/api/user/getFavouriteArtists").queryParam("userId", id).build() }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(ArtistModel::class.java).hasSize(1)
    }

    @Test
    @DisplayName("Test if we can fail to get favourite artists if the user doesn't have any saved")
    fun failToGetFavouriteArtistsForUserNotFound() {
        val id = 1
        webTestClient.get()
            .uri { it.path("/api/user/getFavouriteArtists").queryParam("userId", id).build() }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
    }

    @Test
    @DisplayName("Test if we can fail to get favourite artists by sending bad params")
    fun failToGetFavouriteArtistsForUserBadParam() {
        val id = -123
        webTestClient.get()
            .uri { it.path("/api/user/getFavouriteArtists").queryParam("userId", id).build() }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.errorMessage").isNotEmpty
    }

    @Test
    @DisplayName("Test if we can fail to get favourite artists by sending no params")
    fun failToGetFavouriteArtistsForUserNoParams() {
        webTestClient.get()
            .uri { it.path("/api/user/getFavouriteArtists").build() }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.errorMessage").isNotEmpty
    }
}