package com.itunesapi

import com.itunesapi.dto.AlbumModel
import com.itunesapi.dto.ArtistModel
import org.junit.FixMethodOrder
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Tag("Store")
class StoreControllerTests {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    @Tag("Artist")
    @DisplayName("Test if we can get artist list by term param")
    fun tryToGetArtistListByTerm() {
        val term = "abba"
        webTestClient.get()
            .uri { it.path("/api/store/searchArtists").queryParam("term", term).build() }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(ArtistModel::class.java)
    }

    @Test
    @Tag("Artist")
    @DisplayName("Test if we can fail to get artist list with bad param value")
    fun failToGetArtistListByBadParam() {
        webTestClient.get()
            .uri { it.path("/api/store/searchArtists").queryParam("term", "").build() }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.errorMessage").isNotEmpty
    }

    @Test
    @Tag("Artist")
    @DisplayName("Test if we can fail to get artist list with no param value")
    fun failToGetArtistListWithNoParam() {
        webTestClient.get()
            .uri { it.path("/api/store/searchArtists").build() }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.errorMessage").isNotEmpty
    }

    @Test
    @Tag("Album")
    @DisplayName("Test if we can get album list by artistId param")
    fun tryToGetAlbumListById() {
        val id = 372976
        webTestClient.get()
            .uri { it.path("/api/store/searchAlbums").queryParam("artistId", id).build() }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(AlbumModel::class.java).hasSize(5)
    }

    @Test
    @Tag("Album")
    @DisplayName("Test if we can fail to get album list with bad param value")
    fun failToGetAlbumListWithBadParam() {
        webTestClient.get()
            .uri { it.path("/api/store/searchAlbums").queryParam("artistId", 0).build() }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.errorMessage").isNotEmpty
    }

    @Test
    @Tag("Album")
    @DisplayName("Test if we can fail to get album list with no param value")
    fun failToGetAlbumListWithNoParam() {
        webTestClient.get()
            .uri { it.path("/api/store/searchAlbums").build() }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.errorMessage").isNotEmpty
    }
}