package com.ilfey.shikimori.di.network.apis

import com.ilfey.shikimori.di.network.bodies.PatchUserRate
import com.ilfey.shikimori.di.network.bodies.PostUserRate
import com.ilfey.shikimori.di.network.entities.UserRate
import com.ilfey.shikimori.di.network.enums.TargetType
import retrofit2.Call
import retrofit2.http.*

interface UserRateApi {
    /**
     * Show an user rate
     * See: https://shikimori.me/api/doc/2.0/user_rates/show
     * */
    @GET("/api/v2/user_rates/{id}")
    fun userRate(
        @Path("id") id: Long,
    ): Call<UserRate>

    /**
     * List user rates
     * See: https://shikimori.me/api/doc/2.0/user_rates/index
     * */
    @GET("/api/v2/user_rates")
    fun userRates(
        @Query("user_id") user_id: Long,
        @Query("target_id") target_id: Long? = null,
        @Query("target_type") target_type: String? = null,
        @Query("status") status: String? = null,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int = 1000,
    ): Call<List<UserRate>>

    /**
     * Create an user rate
     * See: https://shikimori.me/api/doc/2.0/user_rates/create
     * */
    @POST("/api/v2/user_rates")
    fun createUserRate(
        @Body body: PostUserRate,
    ): Call<UserRate>

    /**
     * Update an user rate
     * See: https://shikimori.me/api/doc/2.0/user_rates/update
     * */
    @PATCH("/api/v2/user_rates/{id}")
    fun updateUserRate(
        @Path("id") id: Long,
        @Body body: PatchUserRate,
    ): Call<UserRate>
}