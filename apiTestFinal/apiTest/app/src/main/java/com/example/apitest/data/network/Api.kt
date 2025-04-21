package com.example.apitest.data.network
import com.example.apitest.data.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE

import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{id}")
    suspend fun getUserByID(
        @Path("id") id: Int
    ): User

    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: Int,
        @Body user: User
    ): User

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Unit

    @GET("photos/random")
    suspend fun getRandomNaturePhoto(
        @Query("query") query: String = "nature",
        @Query("client_id") clientId: String = "tv_eDR-NN0IvLRCX5tb51wDott8F9WaeUCGV3X0tAjc"
    ): UnsplashPhotoResponse
}

data class UnsplashPhotoResponse(
    val urls: UnsplashPhotoUrls
)

data class UnsplashPhotoUrls(
    val small: String  // We'll use the small size for efficiency
)