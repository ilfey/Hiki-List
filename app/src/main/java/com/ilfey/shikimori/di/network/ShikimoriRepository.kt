package com.ilfey.shikimori.di.network

import com.ilfey.shikimori.di.network.enums.*
import com.ilfey.shikimori.di.network.models.*
import retrofit2.Call
import retrofit2.http.*

interface ShikimoriRepository {

    /**
     * List user achievements
     * See: https://shikimori.one/api/doc/1.0/achievements/index
     * */
    @GET("/api/achievements")
    fun achievements(
        @Query("user_id") user_id: Long,
    ): Call<List<Achievements>>

    /**
     * List animes
     * See: https://shikimori.one/api/doc/1.0/animes
     * */
    @GET("/api/animes")
    fun animes(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int = 50,
        @Query("order") order: Order? = null, // Must be grouped
        @Query("kind") kind: Kind? = null,
        @Query("status") status: ListTypes? = null,
        @Query("season") season: String? = null,
        @Query("score") score: Int? = null,
        @Query("duration") duration: Duration? = null,
        @Query("rating") rating: String? = null, // TODO: Move to enum
        @Query("genre") genre: String? = null,
        @Query("studio") studio: String? = null,
        @Query("franchise") franchise: String? = null,
        @Query("censored") censored: Boolean? = null,
        @Query("mylist") mylist: String? = null, // TODO: Move to enum
        @Query("ids") ids: String? = null, // TODO: Move to enum
        @Query("exclude_ids") exclude_ids: String? = null,
        @Query("search") search: String? = null,
    ): Call<List<Animes>>

    /**
     * Show an anime
     * See: https://shikimori.one/api/doc/1.0/animes/show
     * */
    @GET("/api/animes/{id}")
    fun anime(
        @Path("id") id: Long,
    ): Call<Anime>

    /**
     * Show user history
     * See: https://shikimori.one/api/doc/1.0/users/history
     * */
    @GET("/api/users/{id}/history")
    fun history(
        @Path("id") id: Long,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int = 100,
        @Query("target_id") target_id: Long? = null,
        @Query("target_type") target_type: TargetType? = null,
    ): Call<List<HistoryItem>>

    /**
     * Show an user rate
     * See: https://shikimori.one/api/doc/2.0/user_rates/show
     * */
    @GET("/api/v2/user_rates/{id}")
    fun user_rate(
        @Path("id") id: Long,
    ): Call<UserRate>

    /**
     * List user rates
     * See: https://shikimori.one/api/doc/2.0/user_rates/index
     * */
    @GET("/api/v2/user_rates")
    fun user_rates(
        @Query("user_id") user_id: Long,
        @Query("target_id") target_id: Long? = null,
        @Query("target_type") target_type: TargetType? = null, // enum
        @Query("status") status: String? = null, // TODO: Move to enum
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int = 1000,
    ): Call<List<UserRate>>

    /**
     * Create an user rate
     * See: https://shikimori.one/api/doc/2.0/user_rates/create
     * */
    @POST("/api/v2/user_rates")
    fun create_user_rate(
        // TODO: add body
    ): Call<UserRate>

    /**
     * Update an user rate
     * See: https://shikimori.one/api/doc/2.0/user_rates/update
     * */
    @PATCH("/api/v2/user_rates/{id}")
    fun update_user_rate(
        // TODO: add body
    ): Call<UserRate>

    /**
     * Update an user rate
     * See: https://shikimori.one/api/doc/2.0/user_rates/update
     * */
    @PATCH("/api/v2/user_rates/{id}")
    fun replace_user_rate(
        // TODO: add body
    ): Call<UserRate>

    /**
     * Show current user's brief info.
     * See: https://shikimori.one/api/doc/1.0/users/whoami
     * */
    @GET("/api/users/whoami")
    fun whoami(): Call<User>

    /**
     * Show user's favorites
     * See: https://shikimori.one/api/doc/1.0/users/favourites
     * */
    @GET("/api/users/{id}/favourites")
    fun favorites(
        @Path("id") id: Long,
    ): Call<Favourites>

    /**
     * Show user's anime list
     * See: https://shikimori.one/api/doc/1.0/users/anime_rates
     * */
    @GET("/api/users/{id}/anime_rates")
    fun anime_rates(
        @Path("id") id: Long,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int = 5000,
        @Query("status") status: ListTypes? = null,
        @Query("censored") censored: Boolean? = null,
    ): Call<List<AnimeRate>>
}