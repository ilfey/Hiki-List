package com.ilfey.shikimori.di.network.apis

import com.ilfey.shikimori.di.network.enums.Duration
import com.ilfey.shikimori.di.network.enums.Kind
import com.ilfey.shikimori.di.network.enums.ListType
import com.ilfey.shikimori.di.network.entities.Anime
import com.ilfey.shikimori.di.network.entities.AnimeItem
import com.ilfey.shikimori.di.network.entities.Role
import retrofit2.Call
import retrofit2.http.*

interface AnimeApi {
    /**
     * List animes
     * See: https://shikimori.me/api/doc/1.0/animes
     * */
    @GET("/api/animes")
    fun animes(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int = 50,
        /**
         * Must be grouped
         * enum: Order
         * */
        @Query("order") order: String? = null,
        @Query("kind") kind: Kind? = null,
        @Query("status") status: ListType? = null,
        @Query("season") season: String? = null,
        @Query("score") score: Int? = null,
        @Query("duration") duration: Duration? = null,
        @Query("rating") rating: String? = null,
        @Query("genre") genre: String? = null,
        @Query("studio") studio: String? = null,
        @Query("franchise") franchise: String? = null,
        @Query("censored") censored: Boolean? = null,
        @Query("mylist") list: String? = null,
        @Query("ids") ids: String? = null,
        @Query("exclude_ids") exclude_ids: String? = null,
        @Query("search") search: String? = null,
    ): Call<List<AnimeItem>>

    /**
     * Show an anime
     * See: https://shikimori.me/api/doc/1.0/animes/show
     * */
    @GET("/api/animes/{id}")
    fun anime(
        @Path("id") id: Long,
    ): Call<Anime>

    /**
     * Show a roles of anime
     * See: https://shikimori.me/api/doc/1.0/animes/show
     * */
    @GET("/api/animes/{id}/roles")
    fun animeRoles(
        @Path("id") id: Long,
    ): Call<List<Role>>

    /**
     * See: https://shikimori.one/api/doc/1.0/animes/similar
     * */
//    @GET("/api/animes/{id}/similar")
//    fun similar(
//        @Path("id") id: Long,
//    ): Call<*>

    /**
     * See: https://shikimori.one/api/doc/1.0/animes/related
     * */
//    @GET("/api/animes/{id}/related")
//    fun similar(
//        @Path("id") id: Long,
//    ): Call<*>

    /**
     * See: https://shikimori.one/api/doc/1.0/animes/screenshots
     * */
//    @GET("/api/animes/{id}/screenshots")
//    fun similar(
//        @Path("id") id: Long,
//    ): Call<*>

    /**
     * See: https://shikimori.one/api/doc/1.0/animes/franchise
     * */
//    @GET("/api/animes/{id}/franchise")
//    fun similar(
//        @Path("id") id: Long,
//    ): Call<*>

    /**
     * See: https://shikimori.one/api/doc/1.0/animes/external_links
     * */
//    @GET("/api/animes/{id}/external_links")
//    fun similar(
//        @Path("id") id: Long,
//    ): Call<*>

    /**
     * See: https://shikimori.one/api/doc/1.0/animes/topics
     * */
//    @GET("/api/animes/{id}/topics")
//    fun similar(
//        @Path("id") id: Long,
        /**
         * anons, ongoing, released, episode
         * */
//        @Query("kind") kind: Kind? = null,
//        @Query("page") page: Int? = null,
//        @Query("limit") limit: Int = 50,
//    ): Call<*>

}